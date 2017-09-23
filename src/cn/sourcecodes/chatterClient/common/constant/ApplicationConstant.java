package cn.sourcecodes.chatterClient.common.constant;

/**
 * Created by cn.sourcecodes on 2017/6/5.
 */
public class ApplicationConstant {
    /*
    各种url
     */
    public static final String URL__BASE = "http://localhost:8080/chatter";
    //获取session的cookie
    public static final String URL__INDEX = "/index.jsp";
    //验证模块
    public static final String URL__VALIDATION = "/validation";
    //初始化
    public static final String URL__INIT = "/appInit";
    //消息模块
    //获取消息
    public static final String URL__MESSAGE_GET = "/message/getMessage";
    public static final String URL_SEND_PRIVATE = "/message/sendMessage/privateMessage";
    public static final String URL_SEND_GROUP = "/message/sendMessage/groupMessage";

    /*
    服务器返回类型
     */
    //注册
    public static final int VALIDATION__REGISTER_SUCCESS = 1;//注册成功
    //注册失败
    public static final int VALIDATION__REGISTER_FAIL_ACCOUNT_ALREADY_EXIST = 2;//账号已经存在
    public static final int VALIDATION__REGISTER_FAIL_PHONE_ALREADY_EXIST = 3;//手机号已经存在
    //未知原因(数据正确性在客户端进行验证, 如果不经过客户端验证, 发了不合法的字符, 那么同一返回未知原因
    public static final int VALIDATION__REGISTER_FAIL_UNKNOWN_REASON = 4;

    //登录
    public static final int VALIDATION__LOGIN_SUCCESS = 101;//登录成功
    public static final int VALIDATION__LOGIN_FAIL = 102;//登录失败

    //下线
    public static final int VALIDATION__LOG_OUT_SUCCESS = 201;//下线成功
    public static final int VALIDATION__LOG_OUT_FAIL = 202;//下线失败

    //账号检测
    public static final int VALIDATION__CHECK_ACCOUNT_AVAILABLE = 301;//账号可用

    //参数解析错误, 比如int类型的chatterId传了文本进来, 或者找不到参数
    public static final int VALIDATION__PARAMETER_RESOLVE_ERROR = 1001;

    //多地登录限制
    public static final int VALIDATION__LOGIN_AREA_LIMIT = 2002;












/*
消息模块
 */

    //消息类型
    public static final int MESSAGE__TYPE_PRIVATE = 1;//私聊
    public static final int MESSAGE__TYPE_GROUP = 2;//群聊

    //消息内容类型
    //这些类型都是即时消息
    public static final int MESSAGE__CONTENT_TYPE_PLAIN_TEXT = 101;//纯文本
    public static final int MESSAGE__CONTENT_TYPE_PICTURE = 102;//图片
    public static final int MESSAGE__CONTENT_TYPE_VOICE = 103;//语音
    public static final int MESSAGE__CONTENT_TYPE_VEDIO = 104;//视频
    public static final int MESSAGE__CONTENT_TYPE_FILE = 105;//文件
    public static final int MESSAGE__CONTENT_TYPE_TEXT_PICTURE = 106;//文本和图片
    public static final int MESSAGE__CONTENT_TYPE_RED_PACKET = 107;//红包
    //这些类型的消息是通告性消息, 客户端收到后获取content里面的byte, 解析获得通信方的地址
    public static final int MESSAGE__CONTENT_TYPE_VOICE_CHAT = 1001;//要进行语音聊天
    public static final int MESSAGE__CONTENT_TYPE_VEDIO_CHAT = 1002;//要进行视频聊天

    //消息响应类型
    public static final int MESSAGE__HANDLE_SUCCESS = 10001;//处理成功
    public static final int MESSAGE__HANDLE_FAIL = 10002;//处理失败
    public static final int MESSAGE__NEW_TRUE = 10003;//表示有新的消息
    public static final int MESSAGE__NEW_FALSE = 10004;//表示没有新的消息
    public static final int MESSAGE__PARAMETER_RESOLVE_ERROR = 10005;//表示参数解析出错
    public static final int MESSAGE__GET_SUCCESS_NOT_NULL = 10006;//获取消息成功, 且有新消息
    public static final int MESSAGE__GET_SUCCESS_NULL = 10007;//获取消息成功, 没有新消息

    //内容改变通知消息
    public static final int MESSAGE__GROUP_INFO_CHANGE = 80000;//表示群资料更改, 比如群公告

    //系统消息
    public static final int MESSAGE__CONTENT_TYPE_SYSTEM = 90000;//表示内容来自系统, 而不是某个用户


    //好友模块的消息
    public static final int MESSAGE__CONTACT_ADD_VALIDATION = 90001; //表示有人想添加我为好友
    public static final int MESSAGE__CONTACT_ADD_RESPONSE = 90002;//表示回应别人的添加请求的消息

    //消息模块程序代码验证常量
    public static final int MESSAGE__ADD_MESSAGE_FAIL_UNKNOWN_REASON = 2001;//添加消息失败, 未知原因
    public static final int MESSAGE__ADD_MESSAGE_FAIL_SYSTEM_ERROR = -1;

}
