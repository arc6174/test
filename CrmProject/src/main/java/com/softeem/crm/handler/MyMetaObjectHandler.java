package com.softeem.crm.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("时间插入填充.....");
        this.strictInsertFill(metaObject, "createDate", java.util.Date.class, new Date());
        this.strictInsertFill(metaObject, "updateDate", java.util.Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("时间更新填充.....");
        this.strictUpdateFill(metaObject, "updateDate", java.util.Date.class, new Date());
    }

}
