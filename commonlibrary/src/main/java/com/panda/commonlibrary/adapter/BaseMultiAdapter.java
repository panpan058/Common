package com.panda.commonlibrary.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/07/07 12:09
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public abstract class BaseMultiAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> mData;
    private Map<Integer, Class<? extends ViewBinding>> bindings;

    public BaseMultiAdapter(List<T> mData) {
        this.mData = mData;
    }

    protected void addItemType(int type, Class<? extends ViewBinding> binding) {
        if (bindings == null) {
            bindings = new HashMap<>();
        }
        bindings.put(type, binding);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Class<? extends ViewBinding> root = bindings.get(viewType);
        try {
            Method method = root.getMethod("inflate", LayoutInflater.class);
            Object invoke = method.invoke(null, LayoutInflater.from(parent.getContext()));
            return new BaseViewHolder((ViewBinding) invoke);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        bindData(holder.vb, mData.get(position));
    }

    abstract void bindData(ViewBinding holder, T item);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}

