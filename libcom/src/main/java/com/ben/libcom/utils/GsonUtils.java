package com.ben.libcom.utils;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

/**
 * Gson，大的对象尽量不要重复去创建
 *
 * @author: BD
 */

public class GsonUtils {

    private static WeakReference<Gson> instance;

    public static Gson get() {
        if (instance == null || instance.get() == null) {
            Gson gson = new Gson();
            instance = new WeakReference<>(gson);
        }
        return instance.get();
    }

}
