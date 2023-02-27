package com.project.food.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FoodUtils {
    private FoodUtils(){


    }

    public static ResponseEntity<String> gResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message}\":\""+responseMessage+"\"}", httpStatus); 
    }
}
