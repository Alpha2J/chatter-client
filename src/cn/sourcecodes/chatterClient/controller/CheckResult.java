package cn.sourcecodes.chatterClient.controller;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public enum CheckResult {
    ERROR(0), GLOBAL(1), MODULE(2), UN_LOGIN(3);

    int value;

    CheckResult(int value) {
        this.value = value;
    }

    private int toValue() {
        return value;
    }
}
