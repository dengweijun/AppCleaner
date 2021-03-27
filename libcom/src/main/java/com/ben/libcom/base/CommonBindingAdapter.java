package com.ben.libcom.base;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * BindingAdapter：可以自定添加更多的属性
 *
 * @author: BD
 */
public class CommonBindingAdapter {

    @BindingAdapter(value = "icon", requireAll = false)
    public static void showIcon(ImageView view, String url) {
        Glide.with(view)
                .load(url)
                .into(view);
    }

    @BindingAdapter(value = "visible", requireAll = false)
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

}
