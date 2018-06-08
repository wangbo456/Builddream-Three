package com.jk.demo.dao;

import com.jk.demo.bean.Power;
import com.jk.demo.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestDao {

    @Select("select uid,uname,upass from user")
    List<User> queryuser();

    @Select("select * from `1711d4`.`power`")
    List<Power> getTree();

    @Select("select uid,uname,upass from user")
    List<User> queryUser();

    @Insert("INSERT INTO `1711d4`.`user`\n" +
            "            (\n" +
            "             `uname`,\n" +
            "             `upass`)\n" +
            "VALUES (\n" +
            "        #{uname},\n" +
            "        #{upass});")
    void adduser(User user);

    @Select("select uid,uname,upass from user where uname = #{aa}")
    User getuser(String aa);
}
