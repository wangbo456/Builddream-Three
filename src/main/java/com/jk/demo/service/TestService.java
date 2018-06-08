package com.jk.demo.service;

import com.jk.demo.bean.Power;
import com.jk.demo.bean.User;

import java.util.List;

public interface TestService {

    List<User> queryuser();

    List<Power> getTree();

    List<User> queryUser();

    void adduser(User user);

    User getuser(String aa);
}
