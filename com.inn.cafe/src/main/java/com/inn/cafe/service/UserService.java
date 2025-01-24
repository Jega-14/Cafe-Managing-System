package com.inn.cafe.service;

import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {


    ResponseEntity<String> signUp(Map<String, String> resquestMap);

    ResponseEntity<String> login(Map<String, String> resquestMap);

    ResponseEntity<List<UserWrapper>> getAllUser();

    ResponseEntity<String> update(Map<String,String> requestmap);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(Map<String,String> requesMap);

    ResponseEntity<String> forgotPassword(Map<String,String> requestMap);

}
