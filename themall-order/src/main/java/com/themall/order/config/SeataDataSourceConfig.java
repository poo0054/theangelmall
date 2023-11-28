/*
package com.theangel.themall.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

*/
/**
 * 因为 Seata 通过代理数据源实现分支事务，如果没有注入，事务无法成功回滚
 * 低版本springboot的可以  高版本的会出错
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.config
 * @ClassName: DataSourceConfig
 * @Author: theangel
 * @Date: 2021/9/20 21:00
 *//*

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    */
/**
     * 需要将 DataSourceProxy 设置为主数据源，否则事务无法回滚
     *
     * @param druidDataSource The DruidDataSource
     * @return The default datasource
     *//*

    @Primary
    @Bean("dataSource")
    public DataSource dataSource(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }
}
*/
