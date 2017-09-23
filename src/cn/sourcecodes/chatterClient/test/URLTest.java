package cn.sourcecodes.chatterClient.test;

import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 * Created by cn.sourcecodes on 2017/6/7.
 */
public class URLTest {
    public static void main(String[] args) throws IOException, URISyntaxException {
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        URL url = new URL("http://localhost:8080/chatter/");
//        URL url = new URL("http://dc.csdn.net/re?8409=&uid=-&ref=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DHY0dFWnGRUzb-gce-eULYAyyi3ddml3IxSsHTUPFPT724pTTvatjFJvhUDAPwYYUI88tTeEHwH8zyQkMoM3Lc_%26wd%3D%26eqid%3Dc205a41b0001a5ee0000000659380845&pid=blog&mod=popu_3&dsm=get&mtp=2&con=%E3%80%90CSDN+%E6%8A%80%E6%9C%AF%E4%B8%BB%E9%A2%98%E6%9C%88%E3%80%91%E7%89%A9%E8%81%94%E7%BD%91%E5%85%A8%E6%A0%88%E5%BC%80%E5%8F%91%2Chttp%3A%2F%2Fbss.csdn.net%2Fm%2Ftopic%2Fcommunity_IoT%2Findex%3B%E3%80%90%E8%AF%84%E8%AE%BA%E9%80%81%E4%B9%A6%E3%80%91%E6%AF%8F%E5%91%A8%E8%8D%90%E4%B9%A6%EF%BC%9AMySQL%E3%80%81Kafka%E3%80%81%E5%BE%AE%E4%BF%A1%E5%B0%8F%E7%A8%8B%E5%BA%8F%2Chttp%3A%2F%2Fblog.csdn.net%2Fbroadview2006%2Farticle%2Fdetails%2F72819924%3BCSDN%E6%97%A5%E6%8A%A520170606+%E2%80%94%E2%80%94%E3%80%8A%E7%A8%8B%E5%BA%8F%E5%91%98%EF%BC%8C%E4%BD%A0%E5%BE%97%E5%A4%9A%E8%AF%BB%E4%B9%A6%E4%BA%86%EF%BC%81%E3%80%8B%2Chttp%3A%2F%2Fblog.csdn.net%2Fblogdevteam%2Farticle%2Fdetails%2F72877371&ck=-&curl=http%3A%2F%2Fblog.csdn.net%2FSugar_Z_%2Farticle%2Fdetails%2F47059103&x-acl-token=status_js_dkuyqthzbajmncbsb_token");
        Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8888));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Connection", "keep-alive");
        //conn.setRequestProperty("Cookie", "JSESSIONID=320C57C083E7F678ED14B8974732225E");
        conn.setRequestMethod("GET");

        System.out.println(conn.getContentType());
        CookieStore cookieStore = cookieManager.getCookieStore();
        List<HttpCookie> cookies = cookieStore.get(url.toURI());
        System.out.println("cookies length" + cookies.size());
        for (HttpCookie cookie : cookies) {
            System.out.println(cookie.toString());
        }
        System.out.println(cookieManager.getCookieStore());
    }
}
