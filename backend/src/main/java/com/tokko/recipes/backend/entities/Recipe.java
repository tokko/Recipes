package com.tokko.recipes.backend.entities;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
public class Recipe implements Iterable<Ingredient> {
    @Parent
    @JsonIgnore
    @Load
    Ref<RecipeUser> user;
    private
    @Id
    Long id;
    private String title;
    private String description;
    @Load
    @JsonIgnore
    private List<Ref<Ingredient>> ingredients = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(String title) {
        this.title = title;
    }

    public RecipeUser getUser() {
        return user.get();
    }

    public void setUser(RecipeUser user) {
        this.user = Ref.create(user);
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(Ref.create(ingredient));
    }

    @Override
    public Iterator<Ingredient> iterator() {
        return new Iterator<Ingredient>() {
            Iterator<Ref<Ingredient>> it = ingredients.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Ingredient next() {
                return it.next().safe();
            }

            @Override
            public void remove() {
                it.remove();
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        try{
            return ((Recipe)obj).getId().equals(id);
        }
        catch (ClassCastException ignored){
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public Recipe setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void populate(Recipe recipe) {
        setTitle(recipe.getTitle());
        setDescription(recipe.getDescription());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
