根据模块划分命令, 如果没有登录, 只能使用validation命令, 如果登录了, 可以使用:
1) 模块命令
chatter
message
contact
contactGroup
2) 全局命令
help


命令统一格式: 模块名 -方法名(action) [可选参数]

所有输入命令都交给ControllerResolver 类来进行处理.
所有控制器都实现了Controller接口, 该接口有一个index() 方法, 表示这个模块主面板
约定:
1. 直接输入模块名直接进入模块主面板, 比如输入 message 直接进入消息模块.
2. 进入特定模块后不用再输入模块名, 直接输入方法名就可以进行相应操作.
3. 在模块中输入exit命令退出模块, 在主面板中输入exit则直接退出客户端.
4. 退出客户端前执行下线操作

各个模块命令列表:
个人
命令                                说明
chatter


好友             contact


好友列表类型      cgt


消息
message                            说明
命令







        globalCommandSet = new HashSet<>();
        globalCommandSet.add("help");

        moduleCommandSet = new HashSet<>();
        moduleCommandSet.add("chatter");
        moduleCommandSet.add("contact");
        moduleCommandSet.add("cgt");
        moduleCommandSet.add("message");

        unLoginCommandSet = new HashSet<>();
        unLoginCommandSet.add("help");
        unLoginCommandSet.add("validation");






初始化资源什么时候做?
资源包括什么呢?
检查线程.  每隔2秒发送一次数据包进行检查
消息读取线程  一旦读取到有新消息, 那么启动消息读取线程, 重写自己的list, 做同步, 当一个线程在往list里面写数据时, 其他的需要等待
cookie过时了怎么办:
登录成功后保存账号和密码, 服务端filter里面做好判断, 如果是未登录的用户提示未登录, 且跳转404, 如果检测response为未登录, 那么自动进行登录






