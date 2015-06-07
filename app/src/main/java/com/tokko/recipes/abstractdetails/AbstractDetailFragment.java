package com.tokko.recipes.abstractdetails;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.tokko.recipes.R;
import com.tokko.recipes.utils.AbstractWrapper;

import java.lang.reflect.InvocationTargetException;

import roboguice.fragment.provided.RoboFragment;

public abstract class AbstractDetailFragment<T extends AbstractWrapper<?>> extends RoboFragment {
    protected T entity;


    public AbstractDetailFragment() {
    }

    public static <T extends AbstractWrapper<?>> AbstractDetailFragment newInstance(T entity, Class<? extends AbstractDetailFragment<?>> cls) {
        try {
            AbstractDetailFragment<?> f = cls.getConstructor().newInstance();
            Bundle b = new Bundle();
            b.putString("entity", new Gson().toJson(entity));
            b.putSerializable("clazz", entity.getClass());
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
        if (getArguments().containsKey("entity")) {
            //noinspection unchecked
            Class<T> clz = (Class<T>) getArguments().getSerializable("clazz");
            entity = new Gson().fromJson(getArguments().getString("entity"), clz);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                ViewGroup view = (ViewGroup) getView();
                if (view == null) return true;
                for (int i = 0; i < view.getChildCount(); i++) {
                    View v = view.getChildAt(i);
                    if (v instanceof ViewSwitcher) {
                        ((ViewSwitcher) v).showNext();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
