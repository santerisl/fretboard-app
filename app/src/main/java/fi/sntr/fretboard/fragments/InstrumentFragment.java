package fi.sntr.fretboard.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.Locale;

import fi.sntr.fretboard.MainActivity;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.dialogs.InstrumentDialog;
import fi.sntr.fretboard.adapters.InstrumentAdapter;
import fi.sntr.fretboard.data.Instruments;
import fi.sntr.fretboard.music.Instrument;

/**
 * Fragment for updating instrument fret count, sharp/flat display and selected instrument
 */
public class InstrumentFragment extends Fragment implements InstrumentAdapter.OnItemClickListener {

    private Instruments instruments;
    private Instrument mInstrument;

    private Button addFretButton;
    private Button removeFretButton;
    private Button fretCountButton;

    private InstrumentAdapter instrumentAdapter;

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

        RecyclerView recycler = view.findViewById(R.id.instrument_recycler);

        instrumentAdapter = new InstrumentAdapter(instruments, this);

        recycler.setAdapter(instrumentAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        instrumentAdapter.setSelected(instruments.selectedId);
    }


    private void updateFretViews() {
        addFretButton.setEnabled(mInstrument.getFretCount() + 1 <= Instrument.MAX_FRETS);
        removeFretButton.setEnabled(mInstrument.getFretCount() - 1 >= Instrument.MIN_FRETS);
        fretCountButton.setText(String.format(
                Locale.getDefault(), "%d", (mInstrument.getFretCount() - 1)));
    }

    private void addFrets(View v) {
        mInstrument.setFretCount(mInstrument.getFretCount() + 1);
        updateFretViews();
    }

    private void removeFrets(View v) {
        mInstrument.setFretCount(mInstrument.getFretCount() - 1);
        updateFretViews();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mInstrument = ((MainActivity) context).instrument;
            instruments = ((MainActivity) context).instruments;
        }
    }

    @Override
    public void onSelectedChange(int position) {
        mInstrument.setRootNotes(instruments.get(position).getStrings());
        instruments.selectedId = position;
    }

    @Override
    public void onNewClick() {
        InstrumentDialog iD = InstrumentDialog.newInstance();
        iD.show(getChildFragmentManager(), "instrumentDialog");
    }
}
