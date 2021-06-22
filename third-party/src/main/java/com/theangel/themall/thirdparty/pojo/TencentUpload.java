package com.theangel.themall.thirdparty.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.tencent")
@Data
public class TencentUpload {
    //腾讯云的SecretId
    private String secretId;
    //腾讯云的SecretKey
    private String secretKey;
    //腾讯云的bucket (存储桶)
    private String bucket;
    //腾讯云的region(bucket所在地区)
    private String region;
    //腾讯云的allowPrefix(允许上传的路径)
    private String allowPrefix;
    //腾讯云的临时密钥时长(单位秒)
    private Integer durationSeconds;
    //腾讯云的访问基础链接:
    private String baseUrl;
}
