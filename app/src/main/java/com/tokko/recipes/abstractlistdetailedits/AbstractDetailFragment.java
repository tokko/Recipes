package com.tokko.recipes.abstractlistdetailedits;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;

import java.lang.reflect.InvocationTargetException;


/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link AbstractListActivity}
 * in two-pane mode (on tablets) or a {@link AbstractDetailActivity}
 * on handsets.
 */
public class AbstractDetailFragment<T> extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private T entity;

    /**
     * The dummy content this fragment is presenting.
     */

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AbstractDetailFragment() {
    }

    public static <T> AbstractDetailFragment newInstance(T entity, Class<AbstractDetailFragment> cls) {
        try {
            return cls.getConstructor().newInstance();
        } catch (java.lang.InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Parcelable parcel = getArguments().getParcelable(ARG_ITEM_ID);
        }
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // Show the dummy content as text in a TextView.

        return rootView;
    }
    */
}
