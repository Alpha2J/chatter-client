package cn.sourcecodes.chatterClient.controller;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class CommandChecker {

    private static Set<String> globalCommandSet;
    private static Set<String> moduleCommandSet;
    private static Set<String> unLoginCommandSet;

    static {
        globalCommandSet = new HashSet<>();
        globalCommandSet.add("help");

        moduleCommandSet = new HashSet<>();
        moduleCommandSet.add("chatter");
        moduleCommandSet.add("contact");
        moduleCommandSet.add("cgt");
        moduleCommandSet.add("message");

        unLoginCommandSet = new HashSet<>();
        unLoginCommandSet.add("help");
        unLoginCommandSet.add("validation");
    }

    /**
     * 1) 命令不区分大小写
     * 2) 命令中间可以有多个空格
     * @param commandStr
     * @return 0表示不存在,
     */
    public static Enum<CheckResult> commandCheck(String commandStr, boolean isLogin) {
        String lowerCase = commandStr.toLowerCase();
        String command = lowerCase.split("\\s+")[0];
        //如果已经登录, 则可以做
        if(!isLogin) {
            if(unLoginCommandSet.contains(command)) {
                return CheckResult.UN_LOGIN;
            } else {
                return CheckResult.ERROR;
            }
        } else {
            if(!globalCommandSet.contains(command) && !moduleCommandSet.contains(command)) {
                return CheckResult.ERROR;
            }

            if(globalCommandSet.contains(command)) {
                return CheckResult.GLOBAL;
            } else {
                return CheckResult.MODULE;
            }
        }
    }
}
