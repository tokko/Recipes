package com.tokko.recipes.views;

import android.view.View;

public interface Editable<D> {
    void edit();

    void discard();

    void accept();

    D getData();

    void setData(D data);
}
