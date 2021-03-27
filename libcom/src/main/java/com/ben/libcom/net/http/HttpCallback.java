package com.ben.libcom.net.http;


import com.ben.libcom.utils.GsonUtils;
import com.ben.libcom.utils.ReflectUtils;

/**
 * 请求接口返回json版本的实现类
 * 用于把网络返回的json字符串转让换成对象(Result就是用户接收数据的类型)
 *
 * @author: BD
 */
public abstract class HttpCallback<Result> implements ICallback {

    public abstract void onSuccess(Result result);

    @Override
    public void onSuccess(String result) {
        // result就是网络回来的数据，我们规定是json格式
        // 使用Gson进行转换之前，需要知道指定接收数据的类型
        Class<?> clz = ReflectUtils.analysisClassInfo(this);
        Result resultObj = (Result) GsonUtils.get().fromJson(result, clz);
        onSuccess(resultObj);
    }

    // 通过反射获取当前类的泛型
//    private Class<?> analysisClassInfo() {
//        // 在java中T.getClass() 或 T.class都是不合法的，因为T是泛型变量。
//        // 由于一个类的类型在编译期已确定，故不能在运行期得到T的实际类型。
//        // getGenericSuperclass：获取当前运行类泛型父类类型，即为参数化类型，有所有类型公用的高级接口Type接收。
//        // Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
//        Type genType = this.getClass().getGenericSuperclass();
//        Log.i("BEN_DENG", "analysisClassInfo: genType=" + genType.toString());
//        // ParameterizedType参数化类型，即泛型
//        ParameterizedType pType = (ParameterizedType) genType;
//        // getActualTypeArguments获取参数化类型的数组，泛型可能有多个
//        Type[] params = pType.getActualTypeArguments();
//        Type type0 = params[0];
//        Log.i("BEN_DENG", "analysisClassInfo: params=" + type0.toString());
//        return (Class<?>) type0;
//    }

}
