package com.tokko.recipes.genericlists;

import android.content.Intent;
import android.os.Bundle;

import com.google.api.client.json.GenericJson;
import com.google.gson.Gson;
import com.tokko.recipes.R;
import com.tokko.recipes.abstractdetails.AbstractDetailFragment;
import com.tokko.recipes.abstractdetails.GenericDetailActivity;
import com.tokko.recipes.abstractdetails.ResourceResolver;
import com.tokko.recipes.backend.entities.groceryApi.model.Grocery;

import roboguice.activity.RoboActivity;

public class GenericListActivity extends RoboActivity
        implements GenericListFragment.Callbacks<GenericJson>, AbstractDetailFragment.AbstractDetailFragmentCallbacks {

    public static final String EXTRA_CLASS = "extra_class";

    private boolean mTwoPane;
    private GenericListFragment listFragment;
    private int resource = ResourceResolver.RESOURCE_RECIPES;
    private Class<?> clz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }
        if (savedInstanceState != null) {
            resource = savedInstanceState.getInt(ResourceResolver.RESOURCE_EXTRA);
            clz = (Class<?>) savedInstanceState.getSerializable(EXTRA_CLASS);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            resource = getIntent().getIntExtra(ResourceResolver.RESOURCE_EXTRA, ResourceResolver.RESOURCE_RECIPES);
            clz = (Class<?>) getIntent().getSerializableExtra(EXTRA_CLASS);
            if(clz == null)
                clz = Grocery.class;
        }
        listFragment = ResourceResolver.getListFragment(resource);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getFragmentManager().beginTransaction().replace(R.id.recipe_list, listFragment).commit();
    }

    @Override
    public void onItemSelected(GenericJson entity, boolean edit) {
        AbstractDetailFragment fragment = ResourceResolver.getDetailFragment(entity, edit); // AbstractDetailFragment.newInstance(id, getAbstractDetailFragmentClass());
        if (mTwoPane) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment).addToBackStack("name")
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, GenericDetailActivity.class).putExtra(ResourceResolver.RESOURCE_EXTRA, resource);
            detailIntent.putExtra("entity", new Gson().toJson(entity));
            detailIntent.putExtra("class", entity.getClass());
            startActivity(detailIntent);
        }
    }

    @Override
    public void onAddClicked() {
        try {
            GenericJson entity = (GenericJson) clz.newInstance();
            onItemSelected(entity, true);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() <= 1) finish();
        else getFragmentManager().popBackStack();
    }

    @Override
    public void onDetailFragmentFinished() {
        if (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
    }

    // public abstract <T> Class<GenericListFragment<T>> getAbstractListFragmentClass();

    // public abstract Class<AbstractDetailFragment> getAbstractDetailFragmentClass();
}
