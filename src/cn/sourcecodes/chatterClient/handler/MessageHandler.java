package cn.sourcecodes.chatterClient.handler;

import cn.sourcecodes.chatterClient.entity.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cn.sourcecodes on 2017/6/9.
 */
public class MessageHandler {
    private List<Message> allMessagesCache;
    private List<Message> newMessageList;

    public MessageHandler(List<Message> messageList) {
        this.newMessageList = messageList;
        allMessagesCache = new ArrayList<>();
    }

    //读取新的消息, 读取完后就将这些消息放到历史消息列表中
    public List<Message> getNewMessageList() {
        List<Message> returnList = newMessageList;

        //已经读取的消息存到历史中
        allMessagesCache.addAll(newMessageList);
        newMessageList = null;

        return returnList;
    }

    //获取未读消息的数目
    public int getNewMessageNum() {
        return newMessageList == null ? 0 : newMessageList.size();
    }

}
