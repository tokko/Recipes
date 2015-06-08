package com.tokko.recipes.genericlists;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.inject.Key;
import com.tokko.recipes.abstractdetails.AbstractDetailFragment;
import com.tokko.recipes.utils.AbstractLoader;
import com.tokko.recipes.utils.AbstractWrapper;

import java.util.List;

import roboguice.RoboGuice;
import roboguice.fragment.provided.RoboListFragment;


public class GenericListFragment<T extends AbstractWrapper<?>> extends RoboListFragment implements LoaderManager.LoaderCallbacks<List<T>> {


    private static final String STATE_ACTIVATED_POSITION = "activated_position";


    private Callbacks mCallbacks;

    private int mActivatedPosition = ListView.INVALID_POSITION;
    private Class<? extends AbstractLoader<List<T>>> clz;
    private ArrayAdapter<T> adapter;


    public GenericListFragment() {
    }

    public static <T extends AbstractWrapper<?>> GenericListFragment<T> newInstance(Class<? extends AbstractLoader<List<T>>> clz) {
        GenericListFragment<T> f = new GenericListFragment<>();
        Bundle b = new Bundle();
        b.putSerializable("test", clz);
        f.setArguments(b);
        return f;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clz = (Class<? extends AbstractLoader<List<T>>>) getArguments().getSerializable("test");
        adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
        setListAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(adapter.getItem(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Loader<List<T>> onCreateLoader(int id, Bundle args) {
        return (Loader<List<T>>) RoboGuice.getInjector(getActivity()).getInstance(Key.<AbstractLoader<T>>get(clz));
    }

    @Override
    public void onLoadFinished(Loader<List<T>> loader, List<T> data) {
        adapter.clear();
        adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<T>> loader) {
        setListAdapter(null);
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks<T extends AbstractWrapper<?>> {
        /**
         * Callback for when an item has been selected.
         */
        void onItemSelected(T entity);
    }
}
