package cn.sourcecodes.chatterClient.resolver.impl;

import cn.sourcecodes.chatterClient.app.ApplicationContext;
import cn.sourcecodes.chatterClient.controller.Controller;
import cn.sourcecodes.chatterClient.controller.help.HelpController;
import cn.sourcecodes.chatterClient.controller.validation.ValidationController;
import cn.sourcecodes.chatterClient.resolver.AbstractCommandResolver;
import cn.sourcecodes.chatterClient.resolver.CommandResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class UnLoginCommandResolver extends AbstractCommandResolver implements CommandResolver {
    private Map<String, Controller> controllerMap;//保存所有命令解析需要用到的Controller

    public UnLoginCommandResolver(ApplicationContext applicationContext) {
        super(applicationContext);

        controllerMap = new HashMap<>();

        HelpController helpController = new HelpController();
        controllerMap.put("help", helpController);

        ValidationController validationController = new ValidationController();
        validationController.setApplicationContext(applicationContext);
        controllerMap.put("validation", validationController);
    }

    /*
    模块命令已经做在application中作检测, 这里只需要检测action 和 可选参数就可以
     */
    @Override
    public boolean resolveAndExecute(String command) {
        return resolveAndExecute(command, controllerMap);
    }
}
