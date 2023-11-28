package com.themall.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * sentinel:
 * 配置控制台的信息：参考官网
 * 默认是保存在当前项目的内存中 重启失效！！！
 * 启动实时监控  需要导入actuator
 * 熔断保护
 * 1.使用熔断保护  给调用方的熔断保护
 * 2.调用方手动指定远程访问策略   远程服务被降级，就会触发熔断处理
 * 3.在生产方  使用降级 为了控制大流量访问
 * <p>
 * 自定义降级
 * 1.代码自定义降级
 * try ( Entry entry =SphU.entry("currentSeckillSkus")){ } catch (Throwable t) { if (!BlockException.isBlockException(t)) {Tracer.trace(t);}}
 * 注解
 * 2.@SentinelResource(value = "currentSeckillSkus", blockHandler = "blockHandler"，fallback)
 * 如果降级了 就会调用方法名为blockHandler的值的方法
 * public List<SeckillSkuRedisTo> blockHandler(BlockException blockException) {
 * return null;
 * }
 * fallback同一个类中，如果在别的类 使用fallbackClass，必须为静态方法，和blockHandler就是可以用exception参数
 * <p>
 * <p>
 * 不管使用哪种一定要限制返回，不然会报错
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.theangel.themall.seckill.openfeign")
public class ThemallSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallSeckillApplication.class, args);
    }

}
