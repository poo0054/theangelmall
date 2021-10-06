package com.theangel.themall.coupon.service.impl;

import com.theangel.themall.coupon.entity.SeckillSkuRelationEntity;
import com.theangel.themall.coupon.service.SeckillSkuRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.coupon.dao.SeckillSessionDao;
import com.theangel.themall.coupon.entity.SeckillSessionEntity;
import com.theangel.themall.coupon.service.SeckillSessionService;
import org.springframework.util.ObjectUtils;


@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {


    @Autowired
    SeckillSkuRelationService seckillSkuRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SeckillSessionEntity> getLates3DaySession() {
        List<SeckillSessionEntity> startTime = this.list(new QueryWrapper<SeckillSessionEntity>().between("start_time", getStartTime(), getEndTime()));
        if (!ObjectUtils.isEmpty(startTime)) {
            startTime.stream().map(item -> {
                Long id = item.getId();
                List<SeckillSkuRelationEntity> promotionSessionId = seckillSkuRelationService.list(new QueryWrapper<SeckillSkuRelationEntity>().eq("promotion_session_id", id));
                if (!ObjectUtils.isEmpty(promotionSessionId)) {
                    item.setRelationEntities(promotionSessionId);
                }
                return item;
            }).collect(Collectors.toList());
        }
        return startTime;
    }

    private String getStartTime() {
        LocalDate now = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(now, LocalTime.MIN);
        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String getEndTime() {
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.plusDays(2);
        LocalDateTime end = LocalDateTime.of(localDate, LocalTime.MAX);
        return end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}