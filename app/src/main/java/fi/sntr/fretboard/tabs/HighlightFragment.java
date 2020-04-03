package fi.sntr.fretboard.tabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import fi.sntr.fretboard.MainActivity;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.Instrument;
import fi.sntr.fretboard.music.Music;

public class HighlightFragment extends Fragment {

    private Instrument mInstrument;
    private MaterialButtonToggleGroup scaleGroup;
    private MaterialButtonToggleGroup chordGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_highlight, container, false);

        MaterialButtonToggleGroup noteGroup = view.findViewById(R.id.notes_group);
        for(int i = 0; i < Music.NAMES_SHARP.length; i++) {
            MaterialButton button = (MaterialButton) inflater.inflate(R.layout.highlight_group_button,
                    noteGroup, false);
            button.setText(Music.NAMES_SHARP[i]);
            button.setTag(i);
            noteGroup.addView(button);
        }

        chordGroup = view.findViewById(R.id.chords_group);
        for(int i = 0; i < Music.CHORDS.length; i++) {
            MaterialButton button = (MaterialButton) inflater.inflate(R.layout.highlight_group_button,
                    chordGroup, false);
            button.setText(Music.CHORDS[i].getName());
            button.setTag(i);
            button.setOnClickListener(this::setHighlightChord);
            chordGroup.addView(button);
        }

        scaleGroup = view.findViewById(R.id.scales_group);
        for(int i = 0; i < Music.SCALES.length; i++) {
            MaterialButton button = (MaterialButton) inflater.inflate(R.layout.highlight_group_button,
                    scaleGroup, false);
            button.setText(Music.SCALES[i].getName());
            button.setTag(i);
            button.setOnClickListener(this::setHighlightScale);
            scaleGroup.addView(button);
        }

        noteGroup.addOnButtonCheckedListener(this::setInstrumentHighlightRoot);

        return view;
    }

    private void setInstrumentHighlightRoot(MaterialButtonToggleGroup group, int checkedId,  boolean isChecked) {
        MaterialButton button;
        if(group.getId() == R.id.notes_group) {
            if (group.getCheckedButtonId() == View.NO_ID) {
                mInstrument.setHighlightRoot(-1);
            }
            button = group.findViewById(group.getCheckedButtonId());

            if (button != null && button.getTag() != null) {
                int position = (int) button.getTag();
                mInstrument.setHighlightRoot(position);
            }
        }
    }

    private void setHighlightChord(View v) {
        int chordId = chordGroup.getCheckedButtonId();
        if (chordId != View.NO_ID) {
            MaterialButton button = chordGroup.findViewById(chordId);
            if (button != null && button.getTag() != null) {
                int position = (int) button.getTag();
                mInstrument.setHighlight(Music.CHORDS[position]);
                scaleGroup.clearChecked();
            }
        } else {
            mInstrument.setHighlight(null);
        }
    }

    private void setHighlightScale(View v) {
        int scaleId = scaleGroup.getCheckedButtonId();
        if (scaleId != View.NO_ID) {
            MaterialButton button = scaleGroup.findViewById(scaleId);
            if (button != null && button.getTag() != null) {
                int position = (int) button.getTag();
                mInstrument.setHighlight(Music.SCALES[position]);
                chordGroup.clearChecked();
            }
        } else {
            mInstrument.setHighlight(null);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mInstrument = ((MainActivity) context).instrument;
        }
    }
}
