package com.theangel.themall.product.openfeign;

import com.theangel.common.utils.R;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("third-party")
public interface ThirdPartyService {

    @PostMapping(value = "/third-party/ten/cosGoodsLogo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R cosTenGoodsLogo(MultipartFile files);


    @Configuration
    class FeignConfig {
        @Bean
        public Encoder multipartFormEncoder() {
            return new SpringFormEncoder();
        }

        @Bean
        public feign.Logger.Level multipartLoggerLevel() {
            return feign.Logger.Level.FULL;
        }
    }

}
