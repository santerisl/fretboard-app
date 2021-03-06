package fi.sntr.fretboard;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom button for fretboard with three different extra states
 */
public class FretButton extends AppCompatButton {

    private final Set<Integer> states = new HashSet<>();

    public FretButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void updateState(int attr, boolean value) {
        boolean contains = states.contains(attr);
        if(value != contains) {
            if(contains) {
                states.remove(attr);
            } else {
                states.add(attr);
            }
            invalidate();
            requestLayout();
        }
    }

    /**
     * @param value value to set fret selected state to
     */
    public void setFretSelected(boolean value) {
        updateState(R.attr.state_fret_selected, value);
    }

    /**
     * @param value value to set fret highlighted chord state to
     */
    public void setFretHighlightedChord(boolean value) {
        updateState(R.attr.state_fret_highlighted_chord, value);
    }

    /**
     * @param value value to set fret highlighted scale state to
     */
    public void setFretHighlightedScale(boolean value) {
        updateState(R.attr.state_fret_highlighted_scale, value);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] state;
        if(states != null && states.size() > 0) {
            state = new int[states.size()];
            int i = 0;
            for (Integer s: states) {
                state[i] = s;
                i++;
            }
        } else {
            state = new int[0];
        }

        final int[] drawableState = super.onCreateDrawableState(extraSpace + state.length);

        mergeDrawableStates(drawableState, state);

        return drawableState;
    }
}
