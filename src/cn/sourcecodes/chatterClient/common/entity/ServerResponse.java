package cn.sourcecodes.chatterClient.common.entity;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class ServerResponse {
    private int action;
    private String msg;

    public ServerResponse() {}

    public ServerResponse(int action, String msg) {
        this.action = action;
        this.msg = msg;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
