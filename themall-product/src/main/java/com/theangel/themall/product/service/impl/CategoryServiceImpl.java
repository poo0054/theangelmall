package com.theangel.themall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.theangel.common.utils.fileutils.UUIDUtils;
import com.theangel.themall.product.service.CategoryBrandRelationService;
import com.theangel.themall.product.vo.Catelog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.product.dao.CategoryDao;
import com.theangel.themall.product.entity.CategoryEntity;
import com.theangel.themall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );
        return new PageUtils(page);
    }


    @Override
    public List<CategoryEntity> listCategoryTree() {
        CopyOnWriteArrayList<CategoryEntity> categoryEntities = new CopyOnWriteArrayList<>(baseMapper.selectList(null));
        List<CategoryEntity> collect = categoryEntities
                .parallelStream()
                .filter(categoryEntity ->
                        categoryEntity.getParentCid().equals(0L)
                )
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, categoryEntities));
                    return categoryEntity;
                })
                .sorted((v1, v2) -> (v1.getSort() == null ? 0 : v1.getSort()) - (v2.getSort() == null ? 0 : v2.getSort()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public Integer removeMenuById(List<Long> asList) {
        return baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Integer addCategory(CategoryEntity categoryEntity) {
        return baseMapper.insert(categoryEntity);
    }

    @Override
    public Long[] getCateLogPath(Long catelog) {
        List<Long> longs = new ArrayList<>();
        List<Long> parentCateLogId = getParentCateLogId(catelog, longs);
        Collections.reverse(parentCateLogId);
        return parentCateLogId.toArray(new Long[parentCateLogId.size()]);
    }

    /**
     * CacheEvict: 清除缓存
     * Caching:多个组合操作
     * CacheEvict->allEntries：为true，删除该分区所有缓存  ，批量删除缓存
     *
     * @param category
     */
//    @Caching(evict = {
//            @CacheEvict(value = "category", key = "'getLevel1Categorys'"),
//            @CacheEvict(value = "category", key = "'getCatalogJson'")
//    })
    @CacheEvict(value = "category", allEntries = true)
    @Override
    @Transactional
    public void updateDetail(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategoryIdAndName(category.getCatId(), category.getName());
        }
    }

    private List<Long> getParentCateLogId(Long catelogId, List<Long> longs) {
        longs.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (0 != byId.getParentCid()) {
            getParentCateLogId(byId.getParentCid(), longs);
        }
        return longs;
    }

    /**
     * 获取下级菜单
     *
     * @param categoryEntity
     * @param categoryEntities
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity categoryEntity, List<CategoryEntity> categoryEntities) {
        return categoryEntities
                .stream()
                .filter(v -> categoryEntity.getCatId().equals(v.getParentCid()))
                .map(categoryentity -> {
                    categoryentity.setChildren(getChildren(categoryentity, categoryEntities));
                    return categoryentity;
                })
                .sorted(Comparator.comparingInt(v -> (v.getSort() == null ? 0 : v.getSort())))
//                .sorted((v1, v2) -> (v1.getSort() == null ? 0 : v1.getSort()) - (v2.getSort() == null ? 0 : v2.getSort()))
                .collect(Collectors.toList());
    }


    /**
     * 查询所有一级分类
     *
     * @return
     * @Cacheable： 表示当前方法结果需要缓存 执行过程：缓存中有，方法不需要调用    如果没有，调用方法，最后将方法结果放入缓存
     * 每一个需要缓存的数据，指定缓存的名字（缓存的分区） 如：商品有关的 product
     * SpEL表达式：https://docs.spring.io/spring-framework/docs/5.3.10-SNAPSHOT/reference/html/integration.html#cache-annotations-cacheable-synchronized
     * 指定key ：需要''
     * 存活时间：time-to-live
     * 保存为json格式，默认为jdk序列化的格式
     * sync:本地锁
     */
    @Cacheable(value = {"category"}, key = "#root.method.name", sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        System.out.println("我是查询所有一级分类");
        List<CategoryEntity> parent_cid = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return parent_cid;
    }

    /**
     * 使用cache作为缓存
     *
     * @return
     */
    @Cacheable(value = "category", key = "#root.method.name")
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        System.out.println("去数据库查询了数据");
        //所有分类
        List<CategoryEntity> list = this.list();
        //查询所有1级分类实体类
        List<CategoryEntity> level1Categorys = this.getParentCid(list, 0L);
        Map<String, List<Catelog2Vo>> listMap = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v1 -> {
            //二级分类实体类
            List<CategoryEntity> categoryEntities = this.getParentCid(list, v1.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (!ObjectUtils.isEmpty(categoryEntities)) {
                catelog2Vos = categoryEntities.stream().map(v2 -> {
                    //查询所有一级分类的二级分类
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v2.getParentCid() + "", v2.getCatId() + "", null, v2.getName());
                    //查询二级分类下上级分类
                    List<CategoryEntity> parent_cid2 = this.getParentCid(list, v2.getCatId());
                    if (!ObjectUtils.isEmpty(parent_cid2)) {
                        List<Catelog2Vo.Catalog3Vo> catalog3Vos = parent_cid2.stream().map(categoryEntity -> {
                            Catelog2Vo.Catalog3Vo catalog3Vo = new Catelog2Vo.Catalog3Vo(categoryEntity.getParentCid() + "", categoryEntity.getCatId() + "", categoryEntity.getName());
                            return catalog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        return listMap;
    }

    /**
     * 加入redis缓存
     * <p>
     * TODO  产生堆外内存溢出:outOfDirectMemoryError 待线上运行时排查
     * lettuce堆外内存溢出bug
     * 当进行压力测试时后期后出现堆外内存溢出OutOfDirectMemoryError
     * 产生原因：
     * 1)、springboot2.0以后默认使用lettuce作为操作redis的客户端，它使用netty进行网络通信
     * 2)、lettuce的bug导致netty堆外内存溢出。netty如果没有指定堆外内存，默认使用Xms的值，可以使用-Dio.netty.maxDirectMemory进行设置
     * 解决方案：由于是lettuce的bug造成，不要直接使用-Dio.netty.maxDirectMemory去调大虚拟机堆外内存，治标不治本。
     * 1)、升级lettuce客户端。但是没有解决的
     * 2)、切换使用jedis
     *
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonRedis() {
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            System.out.println("方法getCatalogJson缓存不命中，准备查询数据库......");
            Map<String, List<Catelog2Vo>> catalogJsonDb = getCatalogJsonDb();
            /*
            //加入缓存同时设置过期时间
            redisTemplate.opsForValue().set("catalogJson", JSON.toJSONString(catalogJsonDb), 1, TimeUnit.DAYS);
            */
            return catalogJsonDb;
        }
        System.out.println("方法getCatalogJson缓存命中");
        Map<String, List<Catelog2Vo>> stringListMap = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
        });
        return stringListMap;
    }

    /**
     * 使用redisson分布式锁
     * 缓存数据一致性问题，别的地方修改后，这里拿到的就是旧数据，是错误数据
     * 1 双写模式：修改数据库时，同时修改缓存
     * 2 失效模式：修改数据库时，删除缓存中的数据
     *
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonDbRedissonLock() {
        /**
         * 锁的名字一样就是同一把锁 ，名字不能一样
         * 具体缓存的是某一个数据， 11商品 product-11-lock
         */
        RLock catalogJson = redissonClient.getLock("CatalogJson-lock");
        System.out.println("占用锁成功......");
        catalogJson.lock();
        Map<String, List<Catelog2Vo>> catalogDb;
        try {
            catalogDb = getCatalogDb();
        } finally {
            catalogJson.unlock();
        }
        return catalogDb;
    }

    /**
     * 从数据库查询的数据
     * 用synchronized 本地锁   在分布式下只能锁住当前实例  解决方案：分布式锁
     *
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonDb() {
        /**
         * TODO 缓存穿透 缓存雪崩 缓存击穿
         * 缓存穿透：加入null结果缓存 解决缓存穿透
         * 缓存雪崩： 过期时间（随机值）
         * 缓存击穿：加锁
         */
        //使用uuid作为lock锁的value 避免删除别的锁
        String uuid = UUIDUtils.getUUID();
        //用redis分布式锁 redis的setnx  必须设置时间 不然会产生死锁
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Boolean lock = ops.setIfAbsent("lock", uuid, 30, TimeUnit.SECONDS);
        //占用锁成功
        if (lock) {
            System.out.println("占用锁成功......");
//            redisTemplate.expire("lock", 30, TimeUnit.SECONDS);  lock设置30秒
            Map<String, List<Catelog2Vo>> catalogDb;
            try {
                catalogDb = getCatalogDb();
            } finally {
                //删除锁不是原子操作 ，
//            redisTemplate.delete("lock");
            /*
            String lockValue = ops.get("lock");
            if (lockValue.equals(uuid)) {
                redisTemplate.delete("lock");
            }
            */
                //必须是原子操作
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                //删除锁
                Long lock1 = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                        Arrays.asList("lock"), uuid);
                System.out.println("删除锁成功......");
            }
            return catalogDb;
        } else {
            return getCatalogJsonDb();
        }


    }

    /**
     * 真正查询数据库，在查数据库之前再查一次缓存
     *
     * @return
     */
    private Map<String, List<Catelog2Vo>> getCatalogDb() {
        //进来之后 需要再查一次缓存 没有才去数据库
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (!StringUtils.isEmpty(catalogJson)) {
            Map<String, List<Catelog2Vo>> stringListMap = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
            return stringListMap;
        }
        System.out.println("去数据库查询了数据");
        //所有分类
        List<CategoryEntity> list = this.list();
        //查询所有1级分类实体类
        List<CategoryEntity> level1Categorys = this.getParentCid(list, 0L);
        Map<String, List<Catelog2Vo>> collect1 = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v1 -> {
            //二级分类实体类
            List<CategoryEntity> categoryEntities = this.getParentCid(list, v1.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (!ObjectUtils.isEmpty(categoryEntities)) {
                catelog2Vos = categoryEntities.stream().map(v2 -> {
                    //查询所有一级分类的二级分类
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v2.getParentCid() + "", v2.getCatId() + "", null, v2.getName());
                    //查询二级分类下上级分类
                    List<CategoryEntity> parent_cid2 = this.getParentCid(list, v2.getCatId());
                    if (!ObjectUtils.isEmpty(parent_cid2)) {
                        List<Catelog2Vo.Catalog3Vo> catalog3Vos = parent_cid2.stream().map(categoryEntity -> {
                            Catelog2Vo.Catalog3Vo catalog3Vo = new Catelog2Vo.Catalog3Vo(categoryEntity.getParentCid() + "", categoryEntity.getCatId() + "", categoryEntity.getName());
                            return catalog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        //加入缓存同时设置过期时间 需要在锁中加入
        redisTemplate.opsForValue().set("catalogJson", JSON.toJSONString(collect1), 1, TimeUnit.DAYS);

        return collect1;
    }

    /**
     * 从数据库查询的数据
     * 用synchronized 本地锁   在分布式下只能锁住当前实例  解决方案：分布式锁
     *
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonDbLocalLock() {
        /**
         * TODO 缓存穿透 缓存雪崩 缓存击穿
         * 缓存穿透：加入null结果缓存 解决缓存穿透
         * 缓存雪崩： 过期时间（随机值）
         * 缓存击穿：加锁
         */
        //用synchronized 本地锁   在分布式下只能锁住当前实例  解决方案：分布式锁
        synchronized (this) {
            return getCatalogDb();
        }
    }


    /**
     * 查询出parentCid为指定值的实体
     *
     * @param list
     * @param parentCid
     * @return
     */
    private List<CategoryEntity> getParentCid(List<CategoryEntity> list, Long parentCid) {
//        return this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", v1.getCatId()));
        return list.stream().filter(categoryEntity -> categoryEntity.getParentCid().equals(parentCid)).collect(Collectors.toList());
    }
}

