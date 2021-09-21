package com.theangel.themall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.Channel;
import com.theangel.common.exception.NoStockException;
import com.theangel.common.to.mq.StockDetailTo;
import com.theangel.common.to.mq.StockLockedTo;
import com.theangel.common.utils.R;
import com.theangel.themall.ware.entity.WareOrderTaskDetailEntity;
import com.theangel.themall.ware.entity.WareOrderTaskEntity;
import com.theangel.themall.ware.openfeign.OrderFeignService;
import com.theangel.themall.ware.openfeign.ProductFeignService;
import com.theangel.common.to.SkuHasStockVo;
import com.theangel.themall.ware.service.WareOrderTaskDetailService;
import com.theangel.themall.ware.service.WareOrderTaskService;
import com.theangel.themall.ware.to.OrderItemTo;
import com.theangel.themall.ware.to.WareSkuLockTo;
import com.theangel.themall.ware.vo.OrderVo;
import lombok.Data;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.ware.dao.WareSkuDao;
import com.theangel.themall.ware.entity.WareSkuEntity;
import com.theangel.themall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {
    @Autowired
    ProductFeignService productFeignService;
    @Autowired
    WareSkuDao wareSkuDao;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    WareOrderTaskDetailService wareOrderTaskDetailService;
    @Autowired
    WareOrderTaskService wareOrderTaskService;
    @Autowired
    OrderFeignService orderFeignService;


    @Override
    public void unLockStock(StockLockedTo to) {
        //库存工作单的id    wms_ware_order_task的id
        Long id = to.getId();
        StockDetailTo detailTo = to.getDetailTo();
        //查询数据库有没有这条数据，可能回滚了就会数据库没有这条信息，没有就不用解锁
        WareOrderTaskDetailEntity byId = wareOrderTaskDetailService.getById(detailTo.getId());
        //不等于空就要解锁
        if (!ObjectUtils.isEmpty(byId)) {
            //库存锁定成功，需不需要解锁，还要判断订单
            //没有订单  必须解锁
            //订单有   没有支付才需要解锁   已取消才能解锁
            WareOrderTaskEntity byId1 = wareOrderTaskService.getById(id);
            //订单id
            String orderSn = byId1.getOrderSn();
            //根据订单号，查询订单状态
            R orderStock = orderFeignService.getOrderStock(orderSn);
            //查询成功
            if (0 == orderStock.getCode()) {
                OrderVo orderVo = orderStock.getData(new TypeReference<OrderVo>() {
                });
                //订单不存在或者订单已经被取消，都应该取消库存
                if (ObjectUtils.isEmpty(orderVo) || 4 == orderVo.getStatus()) {
                    unLockWare(detailTo.getSkuId(), detailTo.getWareId(), detailTo.getSkuNum(), detailTo.getId());
                }
            } else {
                throw new RuntimeException("远程服务调用失败");
            }
        }
    }


    /**
     * 解锁订单
     *
     * @param skuId        商品id
     * @param wareId       那个仓库
     * @param num          多少库存
     * @param taskDetailId 工作单id
     */
    public void unLockWare(Long skuId, Long wareId, Integer num, Long taskDetailId) {
        this.baseMapper.unLockStock(skuId, wareId, num);
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        /**
         * skuId ->
         * wareId ->
         */
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if (!ObjectUtils.isEmpty(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }
        String wareId = (String) params.get("wareId");
        if (!ObjectUtils.isEmpty(wareId)) {
            queryWrapper.like("ware_id", wareId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    /**
     * 判断是否有这个库存
     *
     * @param skuId
     * @param wareId
     * @param skuNum
     */
    @Override
    public void addStack(Long skuId, Long wareId, Integer skuNum) {
        //判断是否有这个库存
        WareSkuEntity list = this.getOne(new QueryWrapper<WareSkuEntity>()
                .eq("sku_id", skuId)
                .eq("ware_id", wareId));
        if (ObjectUtils.isEmpty(list)) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStockLocked(0);
            //  查询远程sku名称  事务不需要回滚
            try {
                R r = productFeignService.info(skuId);
                if (r.getCode() == 0) {
                    Map<String, Object> data = (Map) r.get("skuInfo");
                    wareSkuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            wareSkuEntity.setStock(skuNum);
            this.baseMapper.insert(wareSkuEntity);
        } else {
            Integer stock = list.getStock();
            skuNum += stock;
            this.baseMapper.addStack(skuId, wareId, skuNum);
        }
    }

    /**
     * 是否有库存
     *
     * @param skuIds
     * @return
     */
    @Override
    public List<SkuHasStockVo> getHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect = skuIds.stream().map(skuId -> {
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
            Long aLong = baseMapper.getHasStock(skuId);
            skuHasStockVo.setSkuId(skuId);
            if (!ObjectUtils.isEmpty(aLong)) {
                skuHasStockVo.setHasStock(aLong > 0);
            } else {
                skuHasStockVo.setHasStock(false);
            }
            return skuHasStockVo;
        }).filter(item -> !ObjectUtils.isEmpty(item)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 锁定库存
     * 解锁库存
     * 订单创建成功，没有支付
     * 库存锁定成功，订单创建失败
     *
     * @param skuLockTo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean OrderLock(WareSkuLockTo skuLockTo) {
        //TODO 按照下单的收货地址  找到就近库存  锁定库存
        List<OrderItemTo> locks = skuLockTo.getLocks();
        //所有需要锁的商品，封装成对象
        List<SkuWareHasStock> collect = locks.stream().map(item -> {
            SkuWareHasStock skuWareHasStock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            skuWareHasStock.setSkuId(skuId);
            //查询有库存的  仓库id
            List<Long> list = wareSkuDao.listWareIdHakSkuStock(skuId);
            skuWareHasStock.setWareId(list);
            skuWareHasStock.setNum(item.getCount());
            return skuWareHasStock;
        }).collect(Collectors.toList());
        //保存库存单
        WareOrderTaskEntity wareOrderTaskEntity = new WareOrderTaskEntity();
        wareOrderTaskEntity.setOrderSn(skuLockTo.getOrderSn());
        wareOrderTaskService.save(wareOrderTaskEntity);
        //锁定库存
        for (SkuWareHasStock stock : collect) {
            //商品是否锁成功
            boolean skuIdLock = false;
            Long skuId = stock.getSkuId();
            List<Long> longList = stock.getWareId();
            if (ObjectUtils.isEmpty(longList)) {
                //没有库存就直接抛异常   库存不足 无法下单
                throw new NoStockException(skuId);
            }
            //放入mq的数据
            StockLockedTo stockLockedTo = new StockLockedTo();
            //循环所有仓库
            for (Long wareId : longList) {
                //锁定库存的核心，如果锁定成功，返回1，至少有一条锁定成功
                Integer integer = wareSkuDao.lockSkuStock(skuId, wareId, stock.getNum());
                if (1 == integer) {
                    skuIdLock = true;
                    stockLockedTo.setId(wareOrderTaskEntity.getId());
                    //保存工作单详情
                    WareOrderTaskDetailEntity wareOrderTaskDetailEntity = new WareOrderTaskDetailEntity();
                    wareOrderTaskDetailEntity.setSkuId(stock.getSkuId());
                    wareOrderTaskDetailEntity.setSkuNum(stock.getNum());
                    wareOrderTaskDetailEntity.setLockStatus(1);
                    wareOrderTaskDetailEntity.setWareId(wareId);
                    wareOrderTaskDetailEntity.setTaskId(wareOrderTaskEntity.getId());
                    wareOrderTaskDetailService.save(wareOrderTaskDetailEntity);
                    //只发id，防止回滚找不到数据
                    StockDetailTo stockDetailTo = new StockDetailTo();
                    BeanUtils.copyProperties(wareOrderTaskDetailEntity, stockDetailTo);
                    stockLockedTo.setDetailTo(stockDetailTo);
                    /**
                     * 锁定成功 发送mq
                     * 如果锁定一个，就失败，发送了也查不到id
                     */
                    rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked", stockLockedTo);
                    //锁定成功跳出循环
                    break;
                }
            }
            //其中一个商品每个仓库都没有库存
            if (!skuIdLock) {
                //当前商品所有仓库没有锁住，就直接抛出
                throw new NoStockException(skuId);
            }
        }
        //所有锁定成功
        return true;

    }


    @Data
    class SkuWareHasStock {
        private Long skuId;
        /**
         * 仓库id
         * 当前商品在哪些仓库有库存
         */
        private List<Long> wareId;

        /**
         * 多少件
         */
        private Integer num;

    }


}