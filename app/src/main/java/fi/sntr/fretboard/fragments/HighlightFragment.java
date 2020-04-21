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

import fi.sntr.fretboard.MainActivity;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.adapters.ButtonToggleListAdapter;
import fi.sntr.fretboard.adapters.ButtonToggleListAdapter.OnItemChangeListener;
import fi.sntr.fretboard.music.Instrument;
import fi.sntr.fretboard.music.Music;

/**
 * Fragment for selecting fretboard highlights
 */
public class HighlightFragment extends Fragment {

    private Instrument mInstrument;
    private ButtonToggleListAdapter noteAdapter;
    private ButtonToggleListAdapter chordAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_highlight, container, false);

        noteAdapter = createAdapter(
                view.findViewById(R.id.notes_view),
                Music.NAMES_SHARP, mInstrument::setHighlightRoot);

        chordAdapter = createAdapter(
                view.findViewById(R.id.chords_view),
                Music.CHORDS, mInstrument::setHighlightChord);

        createAdapter(
                view.findViewById(R.id.scales_view),
                Music.SCALES, mInstrument::setHighlightScale);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        noteAdapter.setSelected(mInstrument.getHighlightRoot());
        chordAdapter.setSelected(mInstrument.getHighlightChord());
    }

    private <T> ButtonToggleListAdapter createAdapter(RecyclerView recycler, T[] items, OnItemChangeListener listener) {
        ButtonToggleListAdapter adapter = new ButtonToggleListAdapter<>(items, listener);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setHasFixedSize(true);
        return adapter;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mInstrument = ((MainActivity) context).instrument;
        }
    }
}
