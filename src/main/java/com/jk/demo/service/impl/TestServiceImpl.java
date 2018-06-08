package com.jk.demo.service.impl;

import com.jk.demo.bean.Power;
import com.jk.demo.bean.User;
import com.jk.demo.dao.TestDao;
import com.jk.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private TestDao dao;


    @Override
    public List<User> queryuser() {
        List<User> list = dao.queryuser();
        return list;
    }

    @Override
    public List<Power> getTree() {
        return dao.getTree();
    }

    @Override
    public List<User> queryUser() {
        return dao.queryUser();
    }

    @Override
    public void adduser(User user) {
        dao.adduser(user);
    }

    @Override
    public User getuser(String aa) {
        return dao.getuser(aa);
    }
}
