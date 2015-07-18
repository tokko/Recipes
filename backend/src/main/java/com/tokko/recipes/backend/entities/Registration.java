package com.tokko.recipes.backend.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class Registration {

    @Parent
    Ref<RecipeUser> parent;
    @Id
    private String regId;

    public Registration() {
    }

    public Registration(String regId) {
        this.regId = regId;
    }

    public void setParent(RecipeUser parent) {
        this.parent = Ref.create(parent);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return ((Registration) obj).getRegId().equals(regId);
        } catch (Exception ignored) {
            return false;
        }
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}