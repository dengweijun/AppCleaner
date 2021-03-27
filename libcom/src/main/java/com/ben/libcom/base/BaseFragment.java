package com.ben.libcom.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Fragment基类
 *
 * @author: BD
 */
public abstract class BaseFragment<B extends ViewDataBinding, VM extends ViewModel> extends Fragment {

    protected B binding;

    protected VM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createBindingView();
        initView();
        initData();
        if (binding != null) {
            return binding.getRoot();
        }
        Log.e("BEN_DENG", "binding is null");
        return super.onCreateView(inflater, container, savedInstanceState);
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
//                if (binding != null) {
//                    setContentView(binding.getRoot());
//                }
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
