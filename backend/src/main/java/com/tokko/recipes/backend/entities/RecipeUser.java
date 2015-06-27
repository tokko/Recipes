package com.tokko.recipes.backend.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class RecipeUser {
    @Id
    private Long id;
    @Index
    private String email;

    public RecipeUser() {
    }//mandatory no-args constructor
    public RecipeUser(String email) {
        this.email = email;
    }

    public RecipeUser(String email, long id) {
        this(email);
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return ((RecipeUser) obj).getEmail().equals(email);
        } catch (Exception ignored) {
            return false;
        }
    }

    public RecipeUser setId(int id) {
        this.id = (long) id;
        return this;
    }
}
