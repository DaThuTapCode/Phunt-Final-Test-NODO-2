package com.trongphu.finalintern2.config.cross;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Trong Phu on 17/09/2024 23:04
 *
 * @author Trong Phu
 */
public class CrossConfig implements WebMvcConfigurer {

    @Value("${server_fe}")
    private String SERVER_FE;
    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }
}
