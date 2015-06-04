package com.tokko.recipes.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;


public abstract class AbstractLoader<T> extends AsyncTaskLoader<T> {

    private T data;

    public AbstractLoader(Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    public void deliverResult(T result) {
        if (isReset()) {
            releaseResources(result);
            return;
        }

        T oldResult = data;
        data = result;

        if (isStarted()) {
            super.deliverResult(result);
        }

        if (oldResult != result && oldResult != null) {
            releaseResources(oldResult);
        }
    }

    @Override
    public void onCanceled(T result) {
        super.onCanceled(result);
        releaseResources(result);
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

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
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    protected void releaseResources(T result) {
    }
}
