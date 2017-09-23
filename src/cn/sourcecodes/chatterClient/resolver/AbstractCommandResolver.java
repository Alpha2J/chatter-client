package cn.sourcecodes.chatterClient.resolver;

import cn.sourcecodes.chatterClient.app.ApplicationContext;
import cn.sourcecodes.chatterClient.controller.Controller;
import cn.sourcecodes.chatterClient.invoker.MethodInvoker;

import java.util.Map;

/**
 * Created by cn.sourcecodes on 2017/6/9.
 */
public abstract class AbstractCommandResolver {

    protected ApplicationContext applicationContext;

    public AbstractCommandResolver() {}

    public AbstractCommandResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /*
    能够进入这个方法, 说明模块命令已经检查过了
     */
    protected boolean resolveAndExecute(String command, Map<String, Controller> controllerMap) {
        String[] commandParameter = command.split("\\s+");//用空格分开, 一个多多个空格

        //如果等1, 说明直接进入主页方法
        if (commandParameter.length == 1) {
            controllerMap.get(commandParameter[0]).index();
            return true;
        } else if (commandParameter.length > 1 && commandParameter[1].startsWith("-")) {
            //如果不等于1, 且第二个参数是 "-" 开头, 说明有方法和参数
            Controller controller = controllerMap.get(commandParameter[0]);

            int begin = command.indexOf("-");
            String actionAndParam = command.substring(begin + 1);//去掉前面的 "-"
            String[] actionAndParamArray = actionAndParam.split("\\s+");
            try {
                //如果只有方法, 没有参数, 则直接执行 比如 validation -hello
                if (actionAndParamArray.length == 1) {

                    MethodInvoker.invoke(controller.getClass(), controller, actionAndParamArray[0]);

                } else {
                    String[] paramArray = new String[actionAndParamArray.length - 1];
                    for (int i = 0; i < paramArray.length; i++) {
                        paramArray[i] = actionAndParamArray[i + 1];
                    }
                    MethodInvoker.invoke(controller.getClass(), controller, actionAndParamArray[0], paramArray);
                }

                return true;
            } catch (NoSuchMethodException e) {
                System.out.println("没有方法");
            } catch (Exception e) {
                System.out.println("其他方法执行异常");
            }

            return false;
        } else {
            return false;
        }
    }
}
