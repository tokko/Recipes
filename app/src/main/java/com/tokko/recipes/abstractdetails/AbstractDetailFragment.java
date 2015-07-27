package com.tokko.recipes.abstractdetails;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.tokko.recipes.R;
import com.tokko.recipes.views.Editable;

import java.lang.reflect.InvocationTargetException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

import static com.tokko.Util.cloneTo;

public abstract class AbstractDetailFragment<T> extends RoboFragment {
    private static final String EXTRA_ENTITY_KEY = "entity";
    private static final String EXTRA_ENTITY_CLASS_KEY = "clazz";
    private static final String EXTRA_ENTITY_EDITING_KEY = "editing_entity";
    protected T entity;
    private Class<T> clz;
    @InjectView(R.id.edit_buttonBar)
    private LinearLayout buttonBar;
    @InjectView(R.id.edit_delete)
    private Button deleteButton;
    private AbstractDetailFragmentCallbacks host;
    private boolean isEditing;

    public AbstractDetailFragment() {
    }

    public static <T> AbstractDetailFragment newInstance(T entity, Class<? extends AbstractDetailFragment<?>> cls, boolean edit) {
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
        Bundle b = savedInstanceState != null ? savedInstanceState : getArguments();
        if (b != null && b.containsKey(EXTRA_ENTITY_KEY)) {
            //noinspection unchecked
            clz = (Class<T>) b.getSerializable(EXTRA_ENTITY_CLASS_KEY);
            entity = new Gson().fromJson(b.getString(EXTRA_ENTITY_KEY), clz);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            host = (AbstractDetailFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Parent activity must implement callbacks");
        }
    }

    public void swapEntity(T entity) {
        this.entity = entity;
        populateForm(entity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_ENTITY_CLASS_KEY, entity.getClass());
        outState.putString(EXTRA_ENTITY_KEY, new Gson().toJson(entity));

        if (isEditing) {
            T editingEntity = cloneTo(entity, clz);
            editingEntity = populateEntity(editingEntity);
            outState.putString(EXTRA_ENTITY_EDITING_KEY, new Gson().toJson(editingEntity));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        populateForm(entity);
        if (savedInstanceState != null) {
            //populateForm(entity);
            if (savedInstanceState.containsKey(EXTRA_ENTITY_EDITING_KEY)) {
                T editingEntity = new Gson().fromJson(savedInstanceState.getString(EXTRA_ENTITY_EDITING_KEY), clz);
                populateForm(editingEntity);
                edit();
            }
        } else if (getArguments().getBoolean("edit") || getEntityId() == null) {
            edit();
        }
    }

    protected abstract Long getEntityId();
    protected abstract void populateForm(T entity);

    protected abstract T populateEntity(T editingEntity);

    protected abstract void onOk();

    protected abstract void onDelete();

    @OnClick(R.id.edit_ok)
    public final void onOk_Private() {
        entity = populateEntity(entity);
        accept();
        onOk();
    }

    @OnClick(R.id.edit_delete)
    public final void onDelete_Private() {
        //TODO: callback to list to remove fragment from parent container
        onDelete();
        host.onDetailFragmentFinished();
    }

    @OnClick(R.id.edit_cancel)
    public final void onCancel() {
        discard();
        if (getEntityId() == null)
            host.onDetailFragmentFinished();
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
                edit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void discard() {
        buttonBar.setVisibility(View.GONE);
        traverseHierarchy((ViewGroup) getView(), Editable::discard);
        isEditing = false;
    }

    private void edit() {
        isEditing = true;
        deleteButton.setEnabled(getEntityId() != null);
        buttonBar.setVisibility(View.VISIBLE);
        traverseHierarchy((ViewGroup) getView(), Editable::edit);
    }

    private void accept() {
        isEditing = false;
        buttonBar.setVisibility(View.GONE);
        traverseHierarchy((ViewGroup) getView(), Editable::accept);
    }

    private void traverseHierarchy(View root, EditableAction action) {
        traverseHierarchy((ViewGroup) root, action);
    }

    private void traverseHierarchy(ViewGroup root, EditableAction action) {
        if (root == null) return;
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof Editable) {
                action.action((Editable) v);
            }
            else if(v instanceof ViewGroup)
                traverseHierarchy(v, action);
        }
    }

    public interface AbstractDetailFragmentCallbacks {
        void onDetailFragmentFinished();
    }

    private interface EditableAction {
        void action(Editable editable);
    }
}
