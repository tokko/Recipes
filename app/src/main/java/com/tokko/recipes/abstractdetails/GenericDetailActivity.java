package com.tokko.recipes.abstractdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.tokko.recipes.genericlists.GenericListActivity;
import com.tokko.recipes.utils.AbstractWrapper;

import roboguice.activity.RoboActivity;


public class GenericDetailActivity extends RoboActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        if (b != null) {
            Class<?> clz = (Class<?>) getIntent().getSerializableExtra("class");
            String json = getIntent().getStringExtra("entity");
            AbstractWrapper<?> entity = (AbstractWrapper<?>) new Gson().fromJson(json, clz);
            int resource = getIntent().getIntExtra(ResourceResolver.RESOURCE_EXTRA, ResourceResolver.RESOURCE_RECIPES);
            AbstractDetailFragment fragment = ResourceResolver.getDetailFragment(entity, resource, getIntent().getBooleanExtra("edit", false));
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, fragment)
                    .commit();
        }
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
}
