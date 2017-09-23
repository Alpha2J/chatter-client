package cn.sourcecodes.chatterClient.common.utils;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class DataSendUtils {

    public static InputStream get(URL url) throws IOException {
        //加这行做代理只是为了能在本地抓到包, 不然没法查看请求包
        Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8888));
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(proxy);

        InputStream inputStream = httpURLConnection.getInputStream();

        return inputStream;
    }

    public static InputStream post(URL url, Map<String, String> nameValueMap) throws IOException {
        //加这行做代理只是为了能在本地抓到包, 不然没法查看请求包
        Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8888));
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

    public static InputStream post(URL url, Map<String, String> nameValueMap, OutputStream outputStream) throws IOException {
        //加这行做代理只是为了能在本地抓到包, 不然没法查看请求包
        Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8888));
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(proxy);

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);

        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("Charset", "UTF-8");

        String BOUNDARY = "--" + System.currentTimeMillis();
        httpURLConnection.setRequestProperty("Content-type", "multipart/form-data; boundary="+BOUNDARY);

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : nameValueMap.entrySet()) {
            stringBuilder.append("----");
            stringBuilder.append(BOUNDARY);
            stringBuilder.append("\r\n");
            stringBuilder.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\";\r\n");
            stringBuilder.append("Content-Type: text/plain\r\n\r\n");
            stringBuilder.append(entry.getValue() + "\r\n");
        }

        byte[] head = stringBuilder.toString().getBytes("utf-8");
        OutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
        out.write(head);

        byte[] foot = (BOUNDARY + "--\r\n").getBytes("utf-8");
        out.write(foot);

        out.flush();
        out.close();

        return httpURLConnection.getInputStream();
    }


}
