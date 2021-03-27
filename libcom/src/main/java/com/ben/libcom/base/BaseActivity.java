package com.ben.libcom.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Activity基类：初始化ViewDataBinding和ViewModel，并实现数据的绑定
 *
 * @param <B>
 * @param <VM>
 * @author: BD
 */
public abstract class BaseActivity<B extends ViewDataBinding, VM extends ViewModel> extends BaseNormalActivity {

    protected B binding;

    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createBindingView();
        initView();
        initData();
    }

    private void createBindingView() {
        Type genType = this.getClass().getGenericSuperclass();
        ParameterizedType pType = (ParameterizedType) genType;
        Type[] params = pType.getActualTypeArguments();
        if (params.length < 2) {
            return;
        }

        Class<?> bindingClz = (Class<?>) params[0];
        if (bindingClz != ViewDataBinding.class && ViewDataBinding.class.isAssignableFrom(bindingClz)) {
            try {
                Method method = bindingClz.getDeclaredMethod("inflate", LayoutInflater.class);
                method.setAccessible(true);
                binding = (B) method.invoke(null, getLayoutInflater());
                if (binding != null) {
                    setContentView(binding.getRoot());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Class<?> vmClz = (Class<?>) params[1];
        if (vmClz != ViewModel.class && ViewModel.class.isAssignableFrom(vmClz)) {
            // 创建viewModel实例
            viewModel = (VM) new ViewModelProvider(this).get((Class<? extends ViewModel>) vmClz);
        }

        if (binding != null && viewModel != null) {
            try {
                // binding.setViewModel(viewModel)
                Method method = binding.getClass().getDeclaredMethod("setViewModel", viewModel.getClass());
                method.setAccessible(true);
                method.invoke(binding, viewModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected abstract void initView();

    protected abstract void initData();


}
