package com.xtuniversity.mall.model.user.controller;

import com.xtuniversity.mall.model.user.entity.User;
import com.xtuniversity.mall.model.user.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zlj
 * @introduce
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("allUser")
    public List<User> getAllUser(){
        return  userService.getAllUser();
    }

}
