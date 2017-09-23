package cn.sourcecodes.chatterClient.resolver.impl;

import cn.sourcecodes.chatterClient.app.ApplicationContext;
import cn.sourcecodes.chatterClient.controller.Controller;
import cn.sourcecodes.chatterClient.controller.chatter.ChatterController;
import cn.sourcecodes.chatterClient.controller.contact.ContactController;
import cn.sourcecodes.chatterClient.controller.contactGroupType.ContactGroupTypeController;
import cn.sourcecodes.chatterClient.controller.message.MessageController;
import cn.sourcecodes.chatterClient.invoker.MethodInvoker;
import cn.sourcecodes.chatterClient.resolver.AbstractCommandResolver;
import cn.sourcecodes.chatterClient.resolver.CommandResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器解析器, 传入响应的String,
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class ModuleCommandResolver extends AbstractCommandResolver implements CommandResolver {
    private Map<String, Controller> controllerMap;//保存所有命令解析需要用到的Controller

    public ModuleCommandResolver(ApplicationContext applicationContext) {
        super(applicationContext);

        controllerMap = new HashMap<>();

        ChatterController chatterController = new ChatterController();
        chatterController.setApplicationContext(applicationContext);
        controllerMap.put("chatter", chatterController);

        ContactController contactController = new ContactController();
        contactController.setApplicationContext(applicationContext);
        controllerMap.put("contact", contactController);

        ContactGroupTypeController contactGroupTypeController = new ContactGroupTypeController();
        contactGroupTypeController.setApplicationContext(applicationContext);
        controllerMap.put("cgt", contactGroupTypeController);

        MessageController messageController = new MessageController();
        messageController.setApplicationContext(applicationContext);
        controllerMap.put("message", messageController);
    }

    @Override
    public boolean resolveAndExecute(String command) {
        return resolveAndExecute(command, controllerMap);
    }
}
