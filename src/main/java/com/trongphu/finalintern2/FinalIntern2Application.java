package com.trongphu.finalintern2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
//@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class FinalIntern2Application {

    public static void main(String[] args) {
        SpringApplication.run(FinalIntern2Application.class, args);
    }

}
