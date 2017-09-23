package cn.sourcecodes.chatterClient.controller.init;

import cn.sourcecodes.chatterClient.app.ApplicationContext;
import cn.sourcecodes.chatterClient.common.constant.ApplicationConstant;
import cn.sourcecodes.chatterClient.common.entity.AppInitData;
import cn.sourcecodes.chatterClient.common.utils.DataSendUtils;
import cn.sourcecodes.chatterClient.common.utils.JsonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.List;

/**
 * 数据初始化控制器, 不给用户操作, 不实现Controller接口
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class AppInitController {
    private ApplicationContext applicationContext;
    private URL url;

    public AppInitController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        String urlStr = ApplicationConstant.URL__BASE + ApplicationConstant.URL__INIT;

        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /*
    启动应用程序应该先访问路径index.jsp, 获得session
     */
    public void createCookie() {
        String urlStr = ApplicationConstant.URL__BASE + ApplicationConstant.URL__INDEX;
        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            URL url = new URL(urlStr);
            Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8888));
            URLConnection urlConnection = url.openConnection(proxy);

            urlConnection.getContent();
            CookieStore cookieStore = cookieManager.getCookieStore();
            List<HttpCookie> cookies = cookieStore.get(url.toURI());
            applicationContext.setCookie(cookies.get(0).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void initData() {
        System.out.println("SYSTEM: 客户端数据初始化中....");
        try {
            InputStream inputStream = DataSendUtils.get(url);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] bytes = new byte[1024];
            int length;
            while((length = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, length);
            }

            AppInitData appInitData = (AppInitData) JsonUtils.resolveJson(byteArrayOutputStream.toString(), AppInitData.class);
            applicationContext.resolveAppInitData(appInitData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("客户端初始化成功....");
    }


}
