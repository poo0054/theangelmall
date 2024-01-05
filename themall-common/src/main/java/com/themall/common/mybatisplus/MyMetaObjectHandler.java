package com.themall.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.themall.common.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author poo0054
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        if (metaObject.hasSetter("createDate")) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        }
        if (metaObject.hasSetter("createBy")) {
            this.strictInsertFill(metaObject, "createBy", Long.class, UserUtils.getUserId());
        }
        if (metaObject.hasSetter("updateDate")) {
            this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        }
        if (metaObject.hasSetter("updateBy")) {
            this.strictInsertFill(metaObject, "updateBy", Long.class, UserUtils.getUserId()); // 起始版本 3.3.0(推荐使用)
        }
        // 或者
//        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class); // 起始版本 3.3.3(推荐)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        if (metaObject.hasSetter("updateDate")) {
            this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        }
        if (metaObject.hasSetter("updateBy")) {
            this.strictInsertFill(metaObject, "updateBy", Long.class, UserUtils.getUserId()); // 起始版本 3.3.0(推荐使用)
        }
        // 或者
//        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class); // 起始版本 3.3.3(推荐)
    }
}
