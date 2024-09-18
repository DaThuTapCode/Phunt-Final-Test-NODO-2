package com.trongphu.finalintern2.config.i18n;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by Trong Phu on 17/09/2024 23:05
 * Đối tượng dịch message i18n
 * @author Trong Phu
 */
@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;

    //Constructor
    public Translator (ResourceBundleMessageSource messageSource){
        this.messageSource = messageSource;
    }

    /**
     * Method thực hiện dịch các message code truyền vào sang các ngôn ngữ i18n
     * @param message cấu hình code message trong file messages_lang.properties
     * @return {@link String} đã được dịch theo vị trí;
     * */
    public static String toLocale(String message) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }

    public static String toLocale(String message, Object[] objects) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, objects, locale);
    }

}
