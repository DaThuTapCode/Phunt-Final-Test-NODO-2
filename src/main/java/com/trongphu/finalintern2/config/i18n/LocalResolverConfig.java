package com.trongphu.finalintern2.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Trong Phu on 18/09/2024 09:09
 *
 * @author Trong Phu
 */
@Configuration
public class LocalResolverConfig implements WebMvcConfigurer {
    @Bean
    public ResourceBundleMessageSource bundleMessageSource () {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
