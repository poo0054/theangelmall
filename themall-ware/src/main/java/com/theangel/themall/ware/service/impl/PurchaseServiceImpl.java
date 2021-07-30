package com.theangel.themall.ware.service.impl;

import com.theangel.common.constant.WareConstant;
import com.theangel.themall.ware.entity.PurchaseDetailEntity;
import com.theangel.themall.ware.service.PurchaseDetailService;
import com.theangel.themall.ware.service.WareSkuService;
import com.theangel.themall.ware.vo.MergeVo;
import com.theangel.themall.ware.vo.PurchaseDoneVo;
import com.theangel.themall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.ware.dao.PurchaseDao;
import com.theangel.themall.ware.entity.PurchaseEntity;
import com.theangel.themall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService purchaseDetailService;
    @Autowired
    WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceive(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> wrapper = new QueryWrapper<PurchaseEntity>().eq("status", 0).or().eq("status", 1);
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveMerge(MergeVo merge) {
        Long purchaseId = merge.getPurchaseId();
        if (ObjectUtils.isEmpty(purchaseId)) {
            //新建采购单
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            purchaseEntity.setStatus(WareConstant.PurchaseEnum.CREATED.getCode());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }

        //TODO 采购单状态为0或者1才可以采购

        //分配采购单
        List<Long> items = merge.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> collect = items.stream().map(item -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(item);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());

        //更新采购单时间 状态
        purchaseDetailService.updateBatchById(collect);
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setUpdateTime(new Date());
        purchaseEntity.setStatus(WareConstant.PurchaseEnum.ASSIGNED.getCode());
        this.updateById(purchaseEntity);
    }


    /**
     * @param id
     */
    @Transactional
    @Override
    public void saveReceived(List<Long> id) {
        List<PurchaseEntity> collect = id.stream().map(item ->
                this.getById(item)
        ).filter(item ->
                item.getStatus() == WareConstant.PurchaseEnum.CREATED.getCode()
                        || item.getStatus() == WareConstant.PurchaseEnum.ASSIGNED.getCode()
        ).map(item -> {
            item.setStatus(WareConstant.PurchaseEnum.RECEIVE.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());
        //TODO 是自己的采购单
        //改变采购单状态为
        this.updateBatchById(collect);
        //改变采购详情状态为

        collect.forEach(item -> {
            List<PurchaseDetailEntity> purchaseDetailEntityList = purchaseDetailService.list(new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", item.getId()));
            List<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailEntityList.stream().map(purchaseDetailEntity -> {
                PurchaseDetailEntity purchaseEntity = new PurchaseDetailEntity();
                purchaseEntity.setId(purchaseDetailEntity.getId());
                purchaseEntity.setStatus(WareConstant.PurchaseDetailEnum.BUYING.getCode());
                return purchaseEntity;
            }).collect(Collectors.toList());

            purchaseDetailService.updateBatchById(purchaseDetailEntities);
        });

    }

    @Transactional
    @Override
    public void saveDone(PurchaseDoneVo doneVo) {
        //改变采购项状态
        Boolean flag = true;
        List<PurchaseItemDoneVo> items = doneVo.getItems();
        List<PurchaseDetailEntity> updates = new ArrayList<>();
        for (PurchaseItemDoneVo item : items) {
            PurchaseDetailEntity purchaseEntity = new PurchaseDetailEntity();
            if (item.getStatus() == WareConstant.PurchaseDetailEnum.HASERROR.getCode()) {
                flag = false;
                purchaseEntity.setStatus(item.getStatus());
            } else {
                //采购成功 要入库
                purchaseEntity.setStatus(WareConstant.PurchaseDetailEnum.FINISH.getCode());
                PurchaseDetailEntity entity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStack(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
            }
            purchaseEntity.setId(item.getItemId());
            updates.add(purchaseEntity);
        }
        purchaseDetailService.updateBatchById(updates);

        //改变采购单状态
        Long id = doneVo.getId();
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(id);
        purchaseEntity.setStatus(flag ? WareConstant.PurchaseEnum.FINISH.getCode() : WareConstant.PurchaseEnum.HASERROR.getCode());
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);

        //成功的入库


    }
}