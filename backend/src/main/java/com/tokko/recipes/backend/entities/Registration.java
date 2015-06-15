package com.tokko.recipes.backend.entities;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import static com.tokko.recipes.backend.resourceaccess.OfyService.ofy;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class Registration {

    @Parent
    Key<RecipeUser> parent;
    @Id
    private String regId;

    public Registration() {
    }

    public Registration(String regId) {
        this.regId = regId;
    }

    public RecipeUser getParent() {
        return ofy().load().key(parent).now();
    }

    public void setParent(RecipeUser parent) {
        this.parent = Key.create(parent);
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return ((Registration) obj).getRegId().equals(regId);
        } catch (Exception ignored) {
            return false;
        }
    }
}