package com.tokko.recipes.utils;

import android.content.AsyncTaskLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.gson.Gson;

import java.util.List;


public abstract class AbstractLoader<T extends AbstractWrapper<?>> extends AsyncTaskLoader<List<T>> {
    public final static String GCM_ACTION = "com.google.android.c2dm.intent.RECEIVE";

    private final IntentFilter intentFilter;
    protected List<T> data;
    private BroadcastReceiver onChangeReceiver;
    private Context context;
    private Class<? extends AbstractWrapper<?>> clz;

    public AbstractLoader(Context context, Class<? extends AbstractWrapper<?>> clz) {
        super(context);
        this.context = context;
        this.clz = clz;
        onContentChanged();
        intentFilter = new IntentFilter(GCM_ACTION);
        onChangeReceiver = new OnChangeReceiver();
    }

    @Override
    public void deliverResult(List<T> result) {
        if (isReset()) {
            releaseResources(result);
            return;
        }

        List<T> oldResult = data;
        data = result;

        if (isStarted()) {
            super.deliverResult(result);
        }

        if (oldResult != result && oldResult != null) {
            releaseResources(oldResult);
        }
    }

    @Override
    public void onCanceled(List<T> result) {
        super.onCanceled(result);
        releaseResources(result);
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();
        if (onChangeReceiver != null)
            context.unregisterReceiver(onChangeReceiver);

        releaseResources(data);
        data = null;
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            deliverResult(data);
        }
        if (takeContentChanged() || data == null) {
            forceLoad();
        }
        if (onChangeReceiver != null)
            context.registerReceiver(onChangeReceiver, intentFilter);

    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    protected void releaseResources(List<T> result) {
    }

    protected void onNewData(T t) {
        for (T t1 : data) {
            if (t.equals(t1)) {
                t1.populateWith(t);
                return;
            }
        }
        data.add(t);
        onContentChanged();
    }

    private class OnChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            T t = null;
            try {
                //noinspection unchecked
                t = (T) new Gson().fromJson(message, clz);
            } catch (ClassCastException ignored) {
            }
            if (t != null) {
                onNewData(t);
            }
        }
    }
}
