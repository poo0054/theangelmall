package com.theangel.themall.auth.openfeign;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

//@FeignClient("third-party")
public interface ThemallPartyFeign {

//    @PostMapping(value = "/third-party/ten/cosGoodsLogo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> cosTenGoodsLogo(MultipartFile files);


  /*  @Configuration
    class FeignConfig {
        @Bean
        public Encoder multipartFormEncoder() {
            return new SpringFormEncoder();
        }

        @Bean
        public feign.Logger.Level multipartLoggerLevel() {
            return feign.Logger.Level.FULL;
        }
    }*/

}
