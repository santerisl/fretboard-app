package fi.sntr.fretboard;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class FretButton extends AppCompatButton {

    private boolean mSelected = false;
    private boolean mFaded = false;

    public FretButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFretSelected(boolean selected) {
        if(mSelected != selected) {
            mSelected = selected;
            invalidate();
            requestLayout();
        }
    }

    public void setFretFaded(boolean faded) {
        if(mFaded != faded) {
            mFaded = faded;
            invalidate();
            requestLayout();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] state;
        if(mFaded && mSelected) {
            state = new int[]{R.attr.state_fret_selected, R.attr.state_fret_faded};
        } else if(mFaded) {
            state = new int[]{R.attr.state_fret_faded};
        } else if(mSelected) {
            state = new int[]{R.attr.state_fret_selected};
        } else {
            state = new int[0];
        }

        final int[] drawableState = super.onCreateDrawableState(extraSpace + state.length);

        mergeDrawableStates(drawableState, state);

        return drawableState;
    }
}
