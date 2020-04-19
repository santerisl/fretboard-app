package fi.sntr.fretboard.tabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButtonToggleGroup;

import fi.sntr.fretboard.MainActivity;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.Instrument;

public class InstrumentFragment extends Fragment {

    private Instrument mInstrument;

    private Button addFretButton;
    private Button removeFretButton;
    private Button fretCountButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_instrument, container, false);

        addFretButton = view.findViewById(R.id.button_add_fret);
        removeFretButton = view.findViewById(R.id.button_remove_fret);
        fretCountButton = view.findViewById(R.id.button_fret_count);

        addFretButton.setOnClickListener(this::addFrets);
        removeFretButton.setOnClickListener(this::removeFrets);

        updateFretViews();

        MaterialButtonToggleGroup accidentalGroup = view.findViewById(R.id.group_accidental_display);
        accidentalGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if(isChecked) {
                switch (checkedId) {
                    case R.id.button_sharp:
                        mInstrument.setNoteNamesSharp();
                        break;
                    case R.id.button_flat:
                        mInstrument.setNoteNamesFlat();
                        break;
                }
            }
        });

        MaterialButtonToggleGroup instrumentGroup = view.findViewById(R.id.group_instrument);
        instrumentGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if(isChecked) {
                switch (checkedId) {
                    case R.id.instrument_mandolin:
                        mInstrument.setRootNotes(4, 9, 2, 7);
                        break;
                    case R.id.instrument_guitar:
                        mInstrument.setRootNotes(4, 11, 7, 2, 9, 4);
                        break;
                }
            }
        });

        return view;
    }

    private void updateFretViews() {
        addFretButton.setEnabled(mInstrument.getFretCount() + 1 <= Instrument.MAX_FRETS);
        removeFretButton.setEnabled(mInstrument.getFretCount() - 1 >= Instrument.MIN_FRETS);
        fretCountButton.setText(Integer.toString(mInstrument.getFretCount() - 1));
    }

    public void addFrets(View v) {
        mInstrument.setFretCount(mInstrument.getFretCount() + 1);
        updateFretViews();
    }

    public void removeFrets(View v) {
        mInstrument.setFretCount(mInstrument.getFretCount() - 1);
        updateFretViews();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mInstrument = ((MainActivity) context).instrument;
        }
    }
}
