package com.ben.libcom.net.retrofit.response;


import com.ben.libcom.net.retrofit.Exception.ApiException;
import com.ben.libcom.utils.ReflectUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 获取IResponse后判断请求结果，并获取data返回
 * Observable<IResponse<T>> --> Observable<T>
 * 可以自己实现，这里只是提供统一处理业务封装的思路
 *
 * @param <T>
 * @author: BD
 */
public class ResponseTransformer<T> implements ObservableTransformer<IResponse<T>, T> {

    private final CompositeDisposable compositeDisposable;

    public ResponseTransformer(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public ObservableSource<T> apply(Observable<IResponse<T>> upstream) {
        return upstream
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        compositeDisposable.add(disposable);
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends IResponse<T>>>() {
                    @Override
                    public ObservableSource<? extends IResponse<T>> apply(Throwable throwable) throws Exception {
                        // 出现异常后统一处理
                        return Observable.error(ApiException.handleException(throwable));
                    }
                })
                .flatMap(new Function<IResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(IResponse<T> response) throws Exception {
                        // 这段逻辑是业务请求成功后，根据接口返回的数据做一些成功失败后的规范处理
                        // 这里已经高度封装，弊端也就是没那么灵活了
                        // 你也可以自己去实现具体的BaseResponse，不一定要使用IResponse这种写法
                        if (response.isSuccess()) {
                            if (response.getData() != null) {
                                return Observable.just(response.getData());
                            } else {
                                try {
                                    // 有可能存在返回的数据结果为NULL
                                    // 用过反射创建一个没有内容的数据实例，这个实例不能拿去用，只是为了防止传NULL而异常
                                    Class<?> clz = ReflectUtils.analysisClassInfo(response);
                                    T obj = (T) clz.newInstance();
                                    return Observable.just(obj);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return Observable.error(new ApiException(response.getCode(), response.getMsg()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取ResponseTransformer
     *
     * @param compositeDisposable
     * @param <U>
     * @return
     */
    public static <U> ResponseTransformer<U> obtain(CompositeDisposable compositeDisposable) {
        return new ResponseTransformer<>(compositeDisposable);
    }

}
