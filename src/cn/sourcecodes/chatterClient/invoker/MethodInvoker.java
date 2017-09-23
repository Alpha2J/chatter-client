package cn.sourcecodes.chatterClient.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by cn.sourcecodes on 2017/6/8.
 */
public class MethodInvoker {
    public static void invoke(Class<?> clazz, Object object, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Method method = clazz.getDeclaredMethod(methodName);
        method.invoke(object);
    }

    public static void invoke(Class<?> clazz, Object object, String methodName, Object[] params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<String>[] stringClazz = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            stringClazz[i] = String.class;
        }

        Method method = clazz.getDeclaredMethod(methodName, stringClazz);

        method.invoke(object, params);
    }
}
