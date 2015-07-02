package com.tokko.recipes.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.tokko.recipes.R;

import java.util.UUID;

public abstract class Editable<W1 extends View, W2 extends View, D> extends LinearLayout {
    protected W1 label;
    protected W2 edit;
    //  private boolean isEditing;

    public Editable(Context context, AttributeSet attrs, W1 w1, W2 w2) {
        super(context, attrs);
        label = w1;
        edit = w2;
        /*
        label.setSaveEnabled(true);
        label.setSaveFromParentEnabled(true);
        edit.setSaveFromParentEnabled(true);
        edit.setSaveEnabled(true);
        */
        w1.setId(UUID.randomUUID().hashCode());
        w2.setId(UUID.randomUUID().hashCode());
        setOrientation(VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.editable, this, true);
        replaceView(R.id.editable_label, w1);
        replaceView(R.id.editable_edit, w2);
    }

    /*
        @Override
        protected Parcelable onSaveInstanceState() {
            Parcelable superState = super.onSaveInstanceState();
            return new SavedState<>(superState, getData(), isEditing);
        }

        @Override
        protected void onRestoreInstanceState(Parcelable state) {
            if (!(state instanceof SavedState)) {
                super.onRestoreInstanceState(state);
                return;
            }
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            isEditing = ss.isEditing;
            edit();
            //noinspection unchecked
            setData((D) ss.getData());
        }
    */
    private void replaceView(int id, View n) {
        View v = findViewById(id);
        ViewGroup parent = (ViewGroup) v.getParent();
        int index = parent.indexOfChild(v);
        parent.removeViewAt(index);
        parent.addView(n, index);
    }

    public void edit() {
        label.setVisibility(View.GONE);
        edit.setVisibility(View.VISIBLE);
        //    isEditing = true;
    }

    protected abstract void onDiscard();

    public final void discard() {
        label.setVisibility(View.VISIBLE);
        edit.setVisibility(View.GONE);
        onDiscard();
        //     isEditing = false;
    }

    public final void accept() {
        label.setVisibility(View.VISIBLE);
        edit.setVisibility(View.GONE);
        onAccept();
        // isEditing = false;
    }

    protected abstract void onAccept();

    public abstract D getData();

    public abstract void setData(D data);

    /*
    private static class SavedState<D> extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[0];
            }
        };
        private D data;
        private boolean isEditing;

        public SavedState(Parcel source) {
            super(source);
            //noinspection unchecked
            data = (D) source.readValue(data.getClass().getClassLoader());
            isEditing = source.readInt() != 0;
        }

        public SavedState(Parcelable superState, D data, boolean isEditing) {
            this(superState);
            this.data = data;
            this.isEditing = isEditing;
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public D getData() {
            return data;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeValue(data);
            dest.writeInt(isEditing ? 1 : 0);
        }
    }
    */
}
