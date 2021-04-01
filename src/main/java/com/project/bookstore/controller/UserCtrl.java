package com.project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.miscellaneous.Util;
import com.project.bookstore.miscellaneous.*;
import com.project.bookstore.model.*;
import com.project.bookstore.service.ServiceUser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserCtrl {

  Logger log = LoggerFactory.getLogger(UserCtrl.class);

  @Autowired
  ServiceUser userService;

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public String signup(@RequestBody String data){
    log.debug(String.format("Entered user signup for data: %s", data));

    try {
      ObjectMapper mapper = new ObjectMapper();
      InputDataUserSignup inputData = mapper.readValue(data, UserSignupInputData.class);
      EntityUser user = new EntityUser(null, inputData.getFirstName(), inputData.getLastName(),
              inputData.getEmail(), inputData.getPassword(), 0, null);
      return userService.singupUser(user);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(ErrorCodes.RESULT_UNKNOWN_ERROR, null);
    }

  }


  /*
    ---        Admin credentials
    ---        email:   admin@admin.com
    ---        pwd:     admin123
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(@RequestBody String data){
    log.debug(String.format("Entered user /login for: %s", data));

    try{
      ObjectMapper mapper = new ObjectMapper();
      InputDataUserLogin inputData = mapper.readValue(data, InputDataUserLogin.class);
      return userService.loginUser(inputData);
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(ErrorCodes.RESULT_UNKNOWN_ERROR, null);
    }
  }

  @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
  public String addAddress(@RequestBody String data){
    log.debug(String.format("Entered /addAddress for: %s", data));

    try{
      ObjectMapper mapper = new ObjectMapper();
      InputDataAddress inputData = mapper.readValue(data, InputDataAddress.class);
      return userService.addUserAddress(inputData);
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(ErrorCodes.RESULT_UNKNOWN_ERROR, null);
    }
  }

  @RequestMapping(value = "/getAddress", method = RequestMethod.GET)
  public String getAddress(@RequestParam String userId){
    log.debug(String.format("Entered /getAddress for: %s", userId));

    try{
      return userService.getAddress(userId);
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(ErrorCodes.RESULT_UNKNOWN_ERROR, null);
    }
  }
}
