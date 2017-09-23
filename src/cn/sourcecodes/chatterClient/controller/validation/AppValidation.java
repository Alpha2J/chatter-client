package cn.sourcecodes.chatterClient.controller.validation;

import cn.sourcecodes.chatterClient.app.ApplicationContext;
import cn.sourcecodes.chatterClient.common.constant.ApplicationConstant;
import cn.sourcecodes.chatterClient.common.entity.ServerResponse;
import cn.sourcecodes.chatterClient.common.utils.JsonUtils;
import cn.sourcecodes.chatterClient.common.utils.QueryStringUtils;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by cn.sourcecodes on 2017/6/7.
 */
public class AppValidation {
    private ApplicationContext applicationContext;
    private URL url;
    private Scanner input;

    //加这行做代理只是为了能在本地抓到包, 不然没法查看请求包
    Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8888));

    public AppValidation(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        String urlStr = ApplicationConstant.URL__BASE + ApplicationConstant.URL__VALIDATION;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
//            throw new RuntimeException("AppValidation 类中初始化URL失败");
            e.printStackTrace();
        }

        input = new Scanner(System.in);
    }

    //validate返回true的条件是登录成功
    public boolean validate() {
        if(applicationContext.isLogin()) {
            return true;
        }

        System.out.println("验证程序开启中...");
        boolean isDone = false;
        while(!isDone) {
            System.out.println("请输入指令, 1注册, 2登录, exit退出验证程序");
            switch (input.nextLine().trim()) {
                case "1" :
                    register();
                    break;
                case "2" :
                    boolean isLogin = login();
                    applicationContext.setLogin(isLogin);
                    break;
                case "3" :
                    logOut();
                case "exit" :
                    isDone = true;
                    break;
                default:
                    continue;
            }
        }

        return applicationContext.isLogin();
    }

    public boolean register() {
        boolean isSuccess = false;

        System.out.println("注册面板...");
        boolean isDone = false;
        String account;
        String password;

        while(!isDone) {
            System.out.println("账号: (字母开头, 6到20个字符)");
            account = input.nextLine().trim();
            System.out.println("密码: (大小写字母, 符号@#$%^* 6到40个字符)");
            password = input.nextLine().trim();
            if(executeRegister(account, password)) {
                System.out.println("注册成功, 即将退出注册面板..");
                isSuccess = true;
                isDone = true;
            } else {
                System.out.println("注册失败, exit 退出, 任意键继续");
                if(!input.nextLine().trim().equals("exit")) {
                    continue;
                } else {
                    isDone = true;
                }
            }
        }

        System.out.println("注册结束\r\n");
        return isSuccess;
    }

    public boolean login() {
        boolean isSuccess = false;

        System.out.println("登录面板...");
        boolean isDone = false;
        String account;
        String password;

        while(!isDone) {
            System.out.println("账号: (字母开头, 6到20个字符)");
            account = input.nextLine().trim();
            System.out.println("密码: (大小写字母, 符号@#$%^* 6到40个字符)");
            password = input.nextLine().trim();
            if(executeLogin(account, password)) {
                System.out.println("登录成功, 即将退出登录面板..");
                applicationContext.setLogin(true);
                isSuccess = true;
                isDone = true;
            } else {
                System.out.println("登录失败, exit 退出, 任意键继续");
                if(!input.nextLine().trim().equals("exit")) {
                    continue;
                } else {
                    isDone = true;
                }
            }
        }

        System.out.println("退出登录面板...\r\n");
        return isSuccess;
    }

    public boolean logOut() {
        if(!applicationContext.isLogin()) {
            System.out.println("还没登录, 无法下线");
        } else {
            boolean isLogOut = executeLogOut();
            if(isLogOut) {
                System.out.println("下线成功");
            }
        }

        return false;
    }

    private boolean executeRegister(String account, String password) {
        Map<String, String> nameValueMap = new HashMap<>();
        nameValueMap.put("account", account);
        nameValueMap.put("password", password);
        nameValueMap.put("method", "register");

        try {
            InputStream inputStream = post(nameValueMap);
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
            InputStream inputStream = post(nameValueMap);
            byte[] jsonByte = new byte[inputStream.available()];

            inputStream.read(jsonByte);

            String jsonStr = new String(jsonByte, "UTF-8");
            ServerResponse serverResponse = (ServerResponse) JsonUtils.resolveJson(jsonStr, ServerResponse.class);

            if (serverResponse.getAction() == ApplicationConstant.VALIDATION__LOGIN_SUCCESS) {
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

    private boolean executeLogOut() {
        Map<String, String> nameValueMap = new HashMap<>();
        nameValueMap.put("method", "logOut");

        try {
            InputStream inputStream = post(nameValueMap);
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

    //以post方式发送数据
    public InputStream post(Map<String, String> nameValueMap) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(proxy);

//        if(applicationContext.getCookie() != null) {
            //每次访问都将cookie带上, 这样服务端session才不会过期
//            System.out.println(applicationContext.getCookie());
//            前面设置了CookieManager只要程序还启动着, 就会自动带上cookie, 不用再自己带上了
            //httpURLConnection.setRequestProperty("Cookie", applicationContext.getCookie());
//        }

        //将请求参数进行x-www-form-urlencoded 编码
        String queryString = QueryStringUtils.generateCreateString(nameValueMap);

        if(httpURLConnection.getDoOutput() == false) {
            httpURLConnection.setDoOutput(true);
        }

        OutputStream outputStream = httpURLConnection.getOutputStream();
        OutputStream buffered = new BufferedOutputStream(outputStream);
        OutputStreamWriter writer = new OutputStreamWriter(buffered, "UTF-8");

        writer.write(queryString);
        writer.flush();
        writer.close();//不关闭流无法发送任何数据

        return httpURLConnection.getInputStream();
    }
}
