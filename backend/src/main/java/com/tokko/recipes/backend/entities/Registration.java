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

    @Id
    Long id;
    @Parent
    Key<RecipeUser> parent;
    // you can add more fields...
    @Index
    private String regId;

    public Registration() {
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
}