package cn.sourcecodes.chatterClient.controller.validation;

import cn.sourcecodes.chatterClient.app.ApplicationContext;
import cn.sourcecodes.chatterClient.common.constant.ApplicationConstant;
import cn.sourcecodes.chatterClient.common.entity.ServerResponse;
import cn.sourcecodes.chatterClient.common.utils.DataSendUtils;
import cn.sourcecodes.chatterClient.common.utils.JsonUtils;
import cn.sourcecodes.chatterClient.controller.Controller;
import cn.sourcecodes.chatterClient.controller.init.AppInitController;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class ValidationController implements Controller {
    private ApplicationContext applicationContext;
    private URL url;

    public ValidationController() {
        String urlStr = ApplicationConstant.URL__BASE + ApplicationConstant.URL__VALIDATION;
        try {
            this.url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        String urlStr = ApplicationConstant.URL__BASE + ApplicationConstant.URL__VALIDATION;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
//            throw new RuntimeException("AppValidation 类中初始化URL失败");
            e.printStackTrace();
        }
    }

    @Override
    public void index() {
        System.out.println("SYSTEM: validation 面板");
        System.out.println("client validation index(unLogin)-> ");
    }


    public void register(String account, String password) {
        if(executeRegister(account, password)) {
            System.out.println("PROMPT: 注册成功");
        } else {
            System.out.println("PROMPT: 注册失败");
        }
    }

    public void login(String account, String password) {

        if(executeLogin(account, password)) {
            System.out.println("PROMPT: 登录成功");
            applicationContext.setLogin(true);
            AppInitController appInitController = applicationContext.getAppInitController();
            System.out.println("SYSTEM: 初始化数据中...");
            if(appInitController != null) {
                appInitController.initData();
            } else {
                appInitController = new AppInitController(applicationContext);
                appInitController.initData();
            }
            System.out.println("SYSTEM: 数据初始化成功");
        } else {
            System.out.println("PROMPT: 登陆失败");
        }

    }

    public void logOut() {
        if(!applicationContext.isLogin()) {
            System.out.println("SYSTEM: 还没登录, 无法下线");
        } else {
            boolean isLogOut = executeLogOut();
            if(isLogOut) {
                System.out.println("SYSTEM: 下线成功");
                System.out.println("SYSTEM: 即将退出系统");
                System.exit(0);
            }
        }
    }

    private boolean executeRegister(String account, String password) {
        Map<String, String> nameValueMap = new HashMap<>();
        nameValueMap.put("account", account);
        nameValueMap.put("password", password);
        nameValueMap.put("method", "register");

        try {
            InputStream inputStream = DataSendUtils.post(url, nameValueMap);
            byte[] jsonByte = new byte[inputStream.available()];

            inputStream.read(jsonByte);

            String jsonStr = new String(jsonByte, "UTF-8");
            ServerResponse serverResponse = (ServerResponse) JsonUtils.resolveJson(jsonStr, ServerResponse.class);

            //这里有时会为空, 但是抓包看到确实是有数据返回的, 不清楚哪里出错, 直接返回false
            if(serverResponse == null) {
                return false;
            }

            if (serverResponse.getAction() == ApplicationConstant.VALIDATION__REGISTER_SUCCESS) {
                System.out.println(serverResponse.getMsg());
                return true;
            } else {
                System.out.println(serverResponse.getMsg());
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean executeLogin(String account, String password) {
        Map<String, String> nameValueMap = new HashMap<>();
        nameValueMap.put("account", account);
        nameValueMap.put("password", password);
        nameValueMap.put("method", "login");

        try {
            InputStream inputStream = DataSendUtils.post(url, nameValueMap);
            byte[] jsonByte = new byte[inputStream.available()];

            inputStream.read(jsonByte);

            String jsonStr = new String(jsonByte, "UTF-8");
            ServerResponse serverResponse = (ServerResponse) JsonUtils.resolveJson(jsonStr, ServerResponse.class);

            if (serverResponse.getAction() == ApplicationConstant.VALIDATION__LOGIN_SUCCESS) {
                System.out.println("SYSTEM: " + serverResponse.getMsg());
                return true;
            } else {
                System.out.println("SYSTEM: " + serverResponse.getMsg());
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean executeLogOut() {
        Map<String, String> nameValueMap = new HashMap<>();
        nameValueMap.put("method", "logOut");

        try {
            InputStream inputStream = DataSendUtils.post(url, nameValueMap);
            byte[] jsonByte = new byte[inputStream.available()];

            inputStream.read(jsonByte);

            String jsonStr = new String(jsonByte, "UTF-8");
            ServerResponse serverResponse = (ServerResponse) JsonUtils.resolveJson(jsonStr, ServerResponse.class);

            if (serverResponse.getAction() == ApplicationConstant.VALIDATION__LOG_OUT_SUCCESS) {
                System.out.println(serverResponse.getMsg());
                return true;
            } else {
                System.out.println(serverResponse.getMsg());
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
