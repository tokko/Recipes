package com.tokko.recipes.abstractdetails;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.api.client.json.GenericJson;
import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractDetailFragment<T extends GenericJson> extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private T entity;


    public AbstractDetailFragment() {
    }

    public static <T extends GenericJson> AbstractDetailFragment newInstance(T entity, Class<? extends AbstractDetailFragment<?>> cls) {
        try {
            AbstractDetailFragment<?> f = cls.getConstructor().newInstance();
            Bundle b = new Bundle();
            b.putString("entity", new Gson().toJson(entity));
            f.setArguments(b);
            return f;
        } catch (java.lang.InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Parcelable parcel = getArguments().getParcelable(ARG_ITEM_ID);
        }
    }
}
