package com.tokko.recipes.editortests;

import com.tokko.recipes.abstractdetails.ResourceResolver;

public class RecipeEditorTests extends EditorTester {
    @Override
    protected int getResource() {
        return ResourceResolver.RESOURCE_RECIPES;
    }
}
