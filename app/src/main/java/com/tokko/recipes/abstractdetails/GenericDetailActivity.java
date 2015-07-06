package com.tokko.recipes.abstractdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.tokko.recipes.genericlists.GenericListActivity;
import com.tokko.recipes.utils.AbstractWrapper;

import roboguice.activity.RoboActivity;


public class GenericDetailActivity extends RoboActivity implements AbstractDetailFragment.AbstractDetailFragmentCallbacks {
    private AbstractDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState != null) {
            fragment = (AbstractDetailFragment) getFragmentManager().getFragment(savedInstanceState, "frag");
        } else if (getIntent().getExtras() != null) {
            Class<?> clz = (Class<?>) getIntent().getSerializableExtra("class");
            String json = getIntent().getStringExtra("entity");
            AbstractWrapper<?> entity = (AbstractWrapper<?>) new Gson().fromJson(json, clz);
            int resource = getIntent().getIntExtra(ResourceResolver.RESOURCE_EXTRA, ResourceResolver.RESOURCE_RECIPES);
            fragment = ResourceResolver.getDetailFragment(entity, resource, getIntent().getBooleanExtra("edit", false));
        }
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "frag", fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            navigateUpTo(new Intent(this, GenericListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetailFragmentFinished() {
        finish();
    }
}
