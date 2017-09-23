package cn.sourcecodes.chatterClient.test;

import java.awt.*;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class Stringtest {

    public static void main(String[] args) {
        String str = "method -add he he";

        String[] c = str.split("\\s+");
        if(c.length > 1 && c[1].startsWith("-")) {
            int begin = str.indexOf("-");
            System.out.println("index of -" + begin);
            String k = str.substring(begin);
            String[] ks = k.split("\\s+");
            System.out.println("begin" + ks.length);
            for (int i = 0; i < ks.length; i++) {
                System.out.println(ks[i]);
            }
        }

    }
}
