package cn.sourcecodes.chatterClient.app;

import cn.sourcecodes.chatterClient.controller.CheckResult;
import cn.sourcecodes.chatterClient.controller.CommandChecker;
import cn.sourcecodes.chatterClient.controller.init.AppInitController;
import cn.sourcecodes.chatterClient.resolver.CommandResolver;
import cn.sourcecodes.chatterClient.resolver.impl.GlobalCommandResolver;
import cn.sourcecodes.chatterClient.resolver.impl.ModuleCommandResolver;
import cn.sourcecodes.chatterClient.resolver.impl.UnLoginCommandResolver;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by cn.sourcecodes on 2017/6/7.
 */
public class Application extends Thread {
    private ApplicationContext applicationContext;
    private AppInitController appInitController;
    private Scanner input;
    private Toolkit toolkit;//系统工具类, 用于下面实现蜂鸣声音
    private CommandResolver globalCommandResolver;
    private CommandResolver moduleCommandResolver;
    private CommandResolver unLoginCommandResolver;

    public Application() {
        this.applicationContext = new ApplicationContext();
        this.appInitController = new AppInitController(applicationContext);
        this.applicationContext.setAppInitController(appInitController);

        this.input = new Scanner(System.in);
        this.toolkit = Toolkit.getDefaultToolkit();

        this.globalCommandResolver = new GlobalCommandResolver();
        this.moduleCommandResolver = new ModuleCommandResolver(this.applicationContext);
        this.unLoginCommandResolver = new UnLoginCommandResolver(this.applicationContext);
    }

    @Override
    public void run() {
        startUp();
    }

    private void startUp() {
        //先创建连接
        appInitController.createCookie();

        boolean isExit = false;
        while(!isExit) {
            //如果没有登录
            if(!applicationContext.isLogin()) {
                System.out.print("client index(unLogin)-> ");
                String command = input.nextLine().trim();

                if(command.equals("exit")) {
                    System.out.println("PROMPT: 即将退出系统!");
                    isExit = true;
                    continue;
                }

                Enum<CheckResult> result = CommandChecker.commandCheck(command, false);//false表示没有登录
                if(result.equals(CheckResult.ERROR)) {
                    System.out.println("ERROR: 无效的命令!");
                    toolkit.beep();
                } else {
                    //解析命令并执行
                    boolean executeResult = unLoginCommandResolver.resolveAndExecute(command);
                    if(!executeResult) {
                        System.out.println("ERROR: 命令输入错误!");
                        toolkit.beep();
                    }
                }
            } else {
                //如果登录了, 则可以使用登录命令
                System.out.print("client index(logged in)-> ");
                String command = input.nextLine().trim();

                if(command.equals("exit")) {
                    System.out.println("PROMPT: 即将退出系统!");
                    isExit = true;
                    continue;
                }

                Enum<CheckResult> result = CommandChecker.commandCheck(command, true);
                if(result.equals(CheckResult.ERROR)) {
                    System.out.println("ERROR: 无效的命令!");
                    toolkit.beep();
                } else if (result.equals(CheckResult.GLOBAL)){
                    //如果是全局命令
                    boolean executeResult = globalCommandResolver.resolveAndExecute(command);
                    if(!executeResult) {
                        System.out.println("ERROR: 命令输入错误!");
                        toolkit.beep();
                    }
                } else {
                    //如果是模块命令
                    boolean executeResult = moduleCommandResolver.resolveAndExecute(command);
                    if(!executeResult) {
                        System.out.println("ERROR: 命令输入错误!");
                        toolkit.beep();
                    }
                }
            }
        }



    }
}
