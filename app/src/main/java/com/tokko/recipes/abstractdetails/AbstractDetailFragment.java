package com.tokko.recipes.abstractdetails;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.tokko.recipes.R;
import com.tokko.recipes.utils.AbstractWrapper;

import java.lang.reflect.InvocationTargetException;

import butterknife.OnClick;
import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

public abstract class AbstractDetailFragment<T extends AbstractWrapper<?>> extends RoboFragment {
    protected T entity;

    @InjectView(R.id.edit_buttonBar)
    private LinearLayout buttonBar;
    @InjectView(R.id.edit_delete)
    private Button deleteButton;
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (entity.getId() == null)
            deleteButton.setEnabled(false);
    }

    @OnClick(R.id.edit_ok)
    public final void onOk_Private() {
        onOk();
        switchMode();
    }

    protected abstract void onOk();

    @OnClick(R.id.edit_delete)
    public final void onDelete_Private() {
        //TODO: callback to list to remove fragment from parent container
        onDelete();
    }

    protected abstract void onDelete();

    @OnClick(R.id.edit_cancel)
    public final void onCancel() {
        switchMode();
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
                switchMode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchMode(){
        switchMode((ViewGroup) getView());
    }

    private void switchMode(ViewGroup viewgroup) {
        if (viewgroup == null) return;
        for (int i = 0; i < viewgroup.getChildCount(); i++) {
            View v = viewgroup.getChildAt(i);
            if (v instanceof ViewSwitcher) {
                ((ViewSwitcher) v).showNext();
            }
            else if(v instanceof ViewGroup)
                switchMode((ViewGroup)v);
        }
        buttonBar.setVisibility(buttonBar.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        onSwitchMode();
    }

    protected abstract void onSwitchMode();
}
