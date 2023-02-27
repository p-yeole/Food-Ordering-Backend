package com.project.food.JWT;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.food.dao.UserDao;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService{

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loadbyusername {}",username);
        userDetail =userDao.findByEmailId(username);
        if(!Objects.isNull(userDetail)){
            return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
        }else{
                throw new UsernameNotFoundException("user not found.");
            }
    }
    public com.project.food.POJO.User getUserDetail(){

        // com.project.food.POJO.User user =userDetail;
        // user.setPassword(null);
        return userDetail;
    }
    

    private com.project.food.POJO.User userDetail; 

}
