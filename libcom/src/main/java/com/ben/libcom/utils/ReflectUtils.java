package com.ben.libcom.utils;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: BD
 */
public class ReflectUtils {

    // 通过反射获取泛型类型
    public static Class<?> analysisClassInfo(Object object) {
        return analysisClassInfoByIndex(object, 0);
    }

    // 通过反射获取泛型类型
    public static Class<?> analysisClassInfoByIndex(Object object, int index) {
        // 在java中T.getClass() 或 T.class都是不合法的，因为T是泛型变量。
        // 由于一个类的类型在编译期已确定，故不能在运行期得到T的实际类型。
        // getGenericSuperclass：获取当前运行类泛型父类类型，即为参数化类型，有所有类型公用的高级接口Type接收。
        // Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        Type genType = object.getClass().getGenericSuperclass();
        Log.i("BEN_DENG", "analysisClassInfo: genType=" + genType.toString());
        // ParameterizedType参数化类型，即泛型
        ParameterizedType pType = (ParameterizedType) genType;
        // getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        Type[] params = pType.getActualTypeArguments();
        Type typeIndex = params[index];
        Log.i("BEN_DENG", "analysisClassInfo: params=" + typeIndex.toString());
        return (Class<?>) typeIndex;
    }

}
