package cn.sourcecodes.chatterClient.resolver;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public interface CommandResolver {
    /**
     * 解析并执行命令
     * @param command
     * @return  如果解析失败, 没有命令格式错误, 没有合适方法, 方法参数不对, 都返回false, 如果成功执行, 返回true
     */
    boolean resolveAndExecute(String command);
}
