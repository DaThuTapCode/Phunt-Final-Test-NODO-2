package com.trongphu.finalintern2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Trong Phu on 19/09/2024 09:27
 *
 * @author Trong Phu
 */
@RestController
@RequestMapping("api/images")
public class ImageController {

    @GetMapping(value = "")
    public ResponseEntity<?> getImageByName(){
       return null;
    }
}
