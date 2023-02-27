package com.project.food.serviceImpl;

import java.util.Map;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.food.JWT.CustomerUserDetailsService;
import com.project.food.JWT.JwtFilter;
import com.project.food.JWT.JwtUtil;
import com.project.food.POJO.User;
import com.project.food.constents.FoodConstants;
import com.project.food.dao.UserDao;
import com.project.food.service.UserService;
import com.project.food.utils.FoodUtils;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
      log.info("Inside signup {}" , requestMap);
       
       try {
        if(validateSignUpMap(requestMap)){
            User user =userDao.findByEmailId(requestMap.get("email"));
            if(Objects.isNull(user)){
                 userDao.save(getUserFromMap(requestMap));
                 return FoodUtils.gResponseEntity("Successfully Register", HttpStatus.OK);
            }
            else{
                return FoodUtils.gResponseEntity("Email already exits", HttpStatus.BAD_REQUEST);
            }
           }
           else{
            return FoodUtils.gResponseEntity(FoodConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
           }
       } catch (Exception e) {
        e.printStackTrace();
       }
       return FoodUtils.gResponseEntity(FoodConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private boolean validateSignUpMap(Map<String, String> requestMap){
       if( requestMap.containsKey("name") &&  requestMap.containsKey("contactNumber") &&
        requestMap.containsKey("email") &&  requestMap.containsKey("password") ){
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user =new User();
        user.setName( requestMap.get("name"));
        user.setContactNumber( requestMap.get("contactnumber"));
        user.setEmail( requestMap.get("email"));
        user.setPassword( requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
      log.info("Inside LOgin");
      try {
        Authentication auth =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
        if(auth.isAuthenticated()){
            if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                return new ResponseEntity<String>("{\"token\":\""+ 
                // jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(), 
                jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                customerUserDetailsService.getUserDetail().getRole())+"\"}" ,
                 HttpStatus.OK);
            }else{
                return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",
                HttpStatus.BAD_REQUEST);
            }
        }
      } catch (Exception e) {
        log.error("{}", e);
      }
      return new ResponseEntity<String>("{\"message\":\""+"Bad Credentinls."+"\"}",
                HttpStatus.BAD_REQUEST);
    }


}
