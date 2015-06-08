package com.tokko.recipes.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Inject;
import com.tokko.recipes.R;
import com.tokko.recipes.abstractdetails.AbstractDetailFragment;
import com.tokko.recipes.backend.recipeApi.RecipeApi;
import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.utils.RecipeWrapper;

import java.io.IOException;

import roboguice.inject.InjectView;

public class RecipeDetailFragment extends AbstractDetailFragment<RecipeWrapper> {
    @InjectView(R.id.detail_title)
    TextView title;
    @InjectView(R.id.edit_title)
    EditText edit_title;
    @Inject
    private RecipeApi api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(entity.getTitle());
        edit_title.setText(entity.getTitle());
    }

    @Override
    protected void onOk() {
        entity.setTitle(edit_title.getText().toString());
        Recipe entity = this.entity.getEntity();
        if (entity == null) return;
        try {
            if (entity.getId() == null) api.insert(entity);
            else api.update(entity.getId(), entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDelete() {
        try {
            api.remove(entity.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSwitchMode() {
        edit_title.setText(entity.getTitle());
        title.setText(edit_title.getText().toString());
    }
}
