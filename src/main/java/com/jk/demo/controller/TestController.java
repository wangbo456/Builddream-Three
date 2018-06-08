package com.jk.demo.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jk.demo.bean.Power;
import com.jk.demo.bean.User;
import com.jk.demo.service.TestService;
import com.jk.demo.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.rmi.ServerException;
import java.util.*;

@Controller
@RequestMapping("testcontroller")
public class TestController {

    @Autowired
    private TestService service;

    @RequestMapping("test")
    public ModelAndView test(){
        ModelAndView mav = new ModelAndView("home");
        List<User> list = service.queryuser();
        System.out.println(list);
        mav.addObject("list",list);
       return mav;
    }


    @RequestMapping("getTree")
    @ResponseBody
    public List<Map<String,Object>> getTree(){
        /**
         * 功能描述: 
         * @param: []
         * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
         * @auther: 王博
         * @date: 2018/6/8 16:47
         */
        List<Power> list = service.getTree();
        System.out.println(list);
        return treeStr(list, 0);
    }

    public List<Map<String,Object>> treeStr(List<Power> list,Integer pid){
        //定义返回变量
        List<Map<String,Object>> newlist = new ArrayList<Map<String,Object>>();
        //循环所有数据
        for (int i = 0; i < list.size(); i++) {
            //定义以个map集合  用来存放 单个节点数据
            Map<String,Object> map = null;
            //获取单个对象数据
            Power m = list.get(i);
            //判断当前节点是否存在父节点
            if(m!=null){
                if(m.getP_parentid().equals(pid)){
                    map = new HashMap<String, Object>();
                    map.put("id", m.getP_id());
                    map.put("url", m.getP_url());
                    map.put("icon", m.getP_icon());
                    map.put("name", m.getP_text());
                    map.put("children",treeStr(list,m.getP_id()));
                }
                if(map!=null){
                    //获取子节点数据
                    List<Map<String,Object>>  l = (List<Map<String,Object>>)map.get("children");
                    //如果没有子节点数据 移除 nodes
                    if(l.size() == 0){
                        map.remove("children");
                    }
                    newlist.add(map);
                }
            }
        }

        return newlist;
    }



    @RequestMapping("queryUser")
    @ResponseBody
    public Map<String,Object> queryUser(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("rows",service.queryUser());
        map.put("status",200);
        map.put("total",100);
        map.put("hint","");
        return map;
    }

    @RequestMapping("adduser")
    public String adduser(User user){
        user.setUname("王晓雄");
        user.setUpass("123456");
        String newString = Md5Util.getMd532(user.getUpass());
        user.setUpass(newString);
        System.out.println(user);
        service.adduser(user);

        return "";
    }


    @RequestMapping("getaa")
    public void getaa(String aa,String pp,User user){
        String newString = Md5Util.getMd532(pp);
        User use = service.getuser(aa);
       if(use.getUpass().equals(newString)){
           System.out.println("登陆成功");
        }else{
           System.out.println("密码错误");
       }

    }


    //短信接口
    private static Random dom = new Random();
    @RequestMapping("verifyPhone")
    @ResponseBody
    public String verifyPhone(HttpServletRequest req, String phone) throws ServerException, ClientException, ClientException {
        phone = "17611467551";
        String code = (dom.nextInt(9000)+1000)+"";
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAIqXkqUKEku8Zq";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "SW1lTcOO6evE5NxAbb07xkDpwBC84S" +
                "" +
                "" +
                "";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);

        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("SKYDOG");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_135260008");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\""+code+"\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            /*req.getSession().setAttribute(phone,code);*/
            req.getSession().setAttribute("asd",code);
            return sendSmsResponse.getCode();
        }
        return sendSmsResponse.getCode();
    }



}
