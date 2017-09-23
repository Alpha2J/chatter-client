package cn.sourcecodes.chatterClient.app;

import cn.sourcecodes.chatterClient.common.entity.AppInitData;
import cn.sourcecodes.chatterClient.controller.init.AppInitController;
import cn.sourcecodes.chatterClient.entity.Chatter;
import cn.sourcecodes.chatterClient.entity.Contact;
import cn.sourcecodes.chatterClient.entity.ContactGroupType;
import cn.sourcecodes.chatterClient.handler.MessageHandler;


import java.util.List;

/**
 * Created by cn.sourcecodes on 2017/6/5.
 */
public class ApplicationContext {
    private Chatter chatter;//当前用户信息
    private List<Contact> contactList;//好友列表
    private List<ContactGroupType> contactGroupTypeList;//好友分组列表
    //private List<Message> messageList;//消息列表
    private MessageHandler messageHandler;//消息的处理, 封装了消息

    private boolean isLogin;
    private String cookie;

    private AppInitController appInitController;

    public ApplicationContext() { }

    //将初始化数据解析
    public void resolveAppInitData(AppInitData appInitData) {
        this.chatter = appInitData.getChatter();
        this.contactList = appInitData.getContactList();
        this.contactGroupTypeList = appInitData.getContactGroupTypeList();
        messageHandler = new MessageHandler(appInitData.getMessageList());
    }

    public Chatter getChatter() {
        return chatter;
    }

    public void setChatter(Chatter chatter) {
        this.chatter = chatter;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public List<ContactGroupType> getContactGroupTypeList() {
        return contactGroupTypeList;
    }

    public void setContactGroupTypeList(List<ContactGroupType> contactGroupTypeList) {
        this.contactGroupTypeList = contactGroupTypeList;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public AppInitController getAppInitController() {
        return appInitController;
    }

    public void setAppInitController(AppInitController appInitController) {
        this.appInitController = appInitController;
    }
}
