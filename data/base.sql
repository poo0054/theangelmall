ALTER TABLE themall_admin.sys_dept
    ADD flag int DEFAULT 0 NULL COMMENT '逻辑删除状态 已删除值(默认为 1) 未删除值(默认为 0)';
ALTER TABLE themall_admin.sys_dept
    ADD create_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '创建者ID';
ALTER TABLE themall_admin.sys_dept
    ADD create_time datetime NULL COMMENT '创建时间';
ALTER TABLE themall_admin.sys_dept
    ADD update_by varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '修改人';
ALTER TABLE themall_admin.sys_dept
    ADD update_date datetime NULL COMMENT '修改时间';