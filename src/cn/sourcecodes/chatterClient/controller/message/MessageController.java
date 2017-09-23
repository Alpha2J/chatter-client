package cn.sourcecodes.chatterClient.controller.message;

import cn.sourcecodes.chatterClient.app.ApplicationContext;
import cn.sourcecodes.chatterClient.common.constant.ApplicationConstant;
import cn.sourcecodes.chatterClient.common.utils.DataSendUtils;
import cn.sourcecodes.chatterClient.controller.Controller;
import cn.sourcecodes.chatterClient.entity.Contact;
import cn.sourcecodes.chatterClient.entity.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class MessageController implements Controller {
    private ApplicationContext applicationContext;
    private URL privateSendUrl;
    private URL groupSendUrl;

    public MessageController() {
        String privateSendUrlStr = ApplicationConstant.URL__BASE + ApplicationConstant.URL_SEND_PRIVATE;
        String groupSendUrlStr = ApplicationConstant.URL__BASE + ApplicationConstant.URL_SEND_GROUP;

        try {
            privateSendUrl = new URL(privateSendUrlStr);
            groupSendUrl = new URL(groupSendUrlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void index() {
        System.out.println("MESSAGE INDEX: ");
        System.out.println("当前一共有 " + applicationContext.getMessageHandler().getNewMessageNum() + "条未读消息.");
    }

    //没有做群聊, 默认是发送私聊消息, 且是文本消息
    public void send(String receiveIdStr, String content) {
        if(!checkIsContact(receiveIdStr)) {
            System.out.println("ERROR: message send receiveId content , receiveId 出错");
            return;
        }

        int receiveId;
        try {
            receiveId = Integer.parseInt(receiveIdStr);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: 参数解析错误");
        }

        Map<String, String> nameValueMap = new HashMap<>();
        nameValueMap.put("messageType", String.valueOf(ApplicationConstant.MESSAGE__TYPE_PRIVATE));
        nameValueMap.put("contentType", String.valueOf(ApplicationConstant.MESSAGE__CONTENT_TYPE_PLAIN_TEXT));
        nameValueMap.put("sendId", String.valueOf(applicationContext.getChatter().getId()));
        nameValueMap.put("receiveId", receiveIdStr);
        nameValueMap.put("content", content);

        try {
            DataSendUtils.post(privateSendUrl, nameValueMap, new ByteArrayOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        List<Message> messageList = applicationContext.getMessageHandler().getNewMessageList();
        for (int i = 0; i < messageList.size(); i++) {
            System.out.println(messageList.get(i).getContent());
        }
    }

    private boolean checkIsContact(String idStr) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return false;
        }

        List<Contact> contactList = applicationContext.getContactList();
        for (int i = 0; i < contactList.size(); i++) {
            if(contactList.get(i).getId() == id) {
                return true;
            }
        }

        return false;
    }
}
