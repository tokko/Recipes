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
import com.tokko.recipes.views.EditTextViewSwitchable;

import java.io.IOException;

import roboguice.inject.InjectView;

public class RecipeDetailFragment extends AbstractDetailFragment<RecipeWrapper> {
    @InjectView(R.id.recipe_title)
    EditTextViewSwitchable title;
    @InjectView(R.id.recipe_description)
    EditTextViewSwitchable description;

    @Inject
    private RecipeApi api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(entity.getTitle()).setHint("Title");
        description.setText(entity.getDescription()).setHint("Description");
    }

    @Override
    protected void onOk() {
        entity.setTitle(title.getText());
        entity.setDescription(description.getText());
        final Recipe entity = this.entity.getEntity();
        if (entity == null) return;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (entity.getId() == null)
                        api.insert(entity).execute();
                    else api.update(entity.getId(), entity).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onDelete() {
        try {
            api.remove(entity.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onSwitchMode() {
        /*
        edit_title.setText(entity.getTitle());
        title.setText(edit_title.getText().toString());
        edit_description.setText(entity.getDescription());
        description.setText(edit_description.getText().toString());
        */
    }
}
