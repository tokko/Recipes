package com.tokko.recipes.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.tokko.recipes.R;
import com.tokko.recipes.abstractdetails.AbstractDetailFragment;
import com.tokko.recipes.backend.ingredients.ingredientApi.model.Ingredient;
import com.tokko.recipes.backend.recipeApi.RecipeApi;
import com.tokko.recipes.backend.recipeApi.model.Recipe;
import com.tokko.recipes.views.EditTextViewSwitchable;
import com.tokko.recipes.views.EditableListView;

import java.io.IOException;

import roboguice.inject.InjectView;

public class RecipeDetailFragment extends AbstractDetailFragment<Recipe> {
    @InjectView(R.id.recipe_title)
    EditTextViewSwitchable title;
    @InjectView(R.id.recipe_description)
    EditTextViewSwitchable description;
   // @InjectView(R.id.recipe_ingredients)
    EditableListView<Ingredient> ingredients;

    @Inject
    private RecipeApi api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //title.setData(entity.getTitle());
        title.setHint("Title");
        //description.setData(entity.getDescription());
        description.setHint("Description");
        ingredients.setToStringer(i -> i.toString());
    }

    @Override
    protected Long getEntityId() {
        return entity.getId();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onOk() {
        entity.setTitle(title.getText());
        entity.setDescription(description.getText());
        final Recipe entity = this.entity;
        if (entity == null) return;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Recipe execute = api.insert(entity).execute();
                    if (execute != null)
                        RecipeDetailFragment.this.entity.setId(execute.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onDelete() {
        new Thread((() -> {
            try {
                api.remove(entity.getId()).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        })).start();
    }

    @Override
    protected void populateForm(Recipe entity) {
        title.setData(entity.getTitle());
        description.setData(entity.getDescription());
    }

    @Override
    protected Recipe populateEntity(Recipe editingEntity) {
        editingEntity.setTitle(title.getText());
        editingEntity.setDescription(description.getText());
        return editingEntity;
    }
}
