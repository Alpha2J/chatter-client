package cn.sourcecodes.chatterClient.resolver.impl;

import cn.sourcecodes.chatterClient.controller.Controller;
import cn.sourcecodes.chatterClient.controller.help.HelpController;
import cn.sourcecodes.chatterClient.resolver.AbstractCommandResolver;
import cn.sourcecodes.chatterClient.resolver.CommandResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class GlobalCommandResolver extends AbstractCommandResolver implements CommandResolver {
    private Map<String, Controller> controllerMap;

    public GlobalCommandResolver() {
        controllerMap = new HashMap<>();

        HelpController helpController = new HelpController();
        controllerMap.put("help", helpController);
    }

    @Override
    public boolean resolveAndExecute(String command) {
        return resolveAndExecute(command, controllerMap);
    }
}
