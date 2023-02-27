package com.project.food.restImpl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.food.constents.FoodConstants;
import com.project.food.rest.UserRest;
import com.project.food.service.UserService;
import com.project.food.utils.FoodUtils;

@RestController
public class UserRestImpl implements UserRest{

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
       try {
        return userService.signUp(requestMap);
       } catch (Exception e) {
         e.printStackTrace();
       }
       return FoodUtils.gResponseEntity(FoodConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requesmMap) {
      try {
        return userService.login(requesmMap);
       } catch (Exception e) {
         e.printStackTrace();
       }
       return FoodUtils.gResponseEntity(FoodConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
