package com.jk.demo.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jk.demo.util.Md5Util;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.rmi.ServerException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
@Configuration
@EnableScheduling
public class test {

    @Scheduled(cron = "5 30 11 9 * ?")
    public void aa(){
        System.out.println("定时器+++++++");
    }


        public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            String str = "apple";
            String newString = Md5Util.getMd532(str);
            System.out.println(newString);

            String  aa = Md5Util.JM("123");
            System.out.println(aa);
            System.out.println(Md5Util.getMd532("apple").equals("1f3870be274f6c49b3e31a0c6728957f"));

        }





}
