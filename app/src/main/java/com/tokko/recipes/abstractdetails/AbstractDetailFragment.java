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
import com.tokko.recipes.views.EditTextViewSwitchable;
import com.tokko.recipes.views.Editable;

import java.lang.reflect.InvocationTargetException;

import butterknife.ButterKnife;
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

    public static <T extends AbstractWrapper<?>> AbstractDetailFragment newInstance(T entity, Class<? extends AbstractDetailFragment<?>> cls, boolean edit) {
        try {
            AbstractDetailFragment<?> f = cls.getConstructor().newInstance();
            Bundle b = new Bundle();
            b.putString("entity", new Gson().toJson(entity));
            b.putSerializable("clazz", entity.getClass());
            b.putBoolean("edit", edit);
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
        ButterKnife.inject(this, view);
        if (entity.getId() == null)
            deleteButton.setEnabled(false);
        if (savedInstanceState != null) {
            //todo: restore save state
        } else if (getArguments().getBoolean("edit"))
            doIt(Editable::edit);
    }

    @OnClick(R.id.edit_ok)
    public final void onOk_Private() {
        onOk();
        doIt(Editable::accept);
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
        doIt(Editable::discard);
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
                doIt(Editable::edit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doIt(EditableAction action) {
        doIt((ViewGroup) getView(), action);
    }

    private void doIt(ViewGroup viewGroup, EditableAction action) {
        if (viewGroup == null) return;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof Editable) {
                action.Action((Editable) v);
            }
            else if(v instanceof ViewGroup)
                doIt((ViewGroup) v, action);
        }
        buttonBar.setVisibility(buttonBar.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    private interface EditableAction {
        void Action(Editable editable);
    }

}
