package fi.sntr.fretboard.tabs;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fi.sntr.fretboard.MainActivity;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.adapters.ChordResultAdapter;
import fi.sntr.fretboard.music.ChordFinder;
import fi.sntr.fretboard.music.CompareResult;
import fi.sntr.fretboard.music.Instrument;
import fi.sntr.fretboard.music.Music;

public class ChordsFragment extends Fragment {

    private Instrument mInstrument;
    private Set<Integer> selected = new HashSet<>();

    private List<CompareResult> compareResults = new ArrayList<>();
    private List<Integer> selectedNotes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_chords, container, false);

        RecyclerView chordsRecycler = view.findViewById(R.id.compare_view);
        chordsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ChordResultAdapter chordsAdapter = new ChordResultAdapter(compareResults);
        chordsRecycler.setAdapter(chordsAdapter);

        updateChords();
        chordsAdapter.notifyDataSetChanged();

        mInstrument.setChangeListener(new Instrument.InstrumentChangeListener() {
            @Override
            public void onInstrumentChange() { }

            @Override
            public void onSelectedChange(int string, int oldFret, int newFret) {
                updateChords();
                chordsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFretCountChange(int oldFretCount, int newFretCount) {
            }

            @Override
            public void onIsSharpChange(boolean isSharp) {

            }

            @Override
            public void onHighlightChange() {

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mInstrument = ((MainActivity) context).instrument;
        }
    }

    public void updateChords() {
        compareResults.clear();
        compareResults.addAll(ChordFinder.findChords(mInstrument));
    }
}
