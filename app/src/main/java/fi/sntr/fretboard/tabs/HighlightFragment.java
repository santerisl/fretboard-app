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


import fi.sntr.fretboard.MainActivity;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.adapters.ButtonToggleListAdapter;
import fi.sntr.fretboard.adapters.ButtonToggleListAdapter.OnItemClickListener;
import fi.sntr.fretboard.music.Instrument;
import fi.sntr.fretboard.music.Music;

public class HighlightFragment extends Fragment {

    private Instrument mInstrument;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_highlight, container, false);

        createAdapter(view.findViewById(R.id.notes_view),
                Music.NAMES_SHARP,
                mInstrument::setHighlightRoot);

        createAdapter(view.findViewById(R.id.chords_view),
                Music.CHORDS,
                mInstrument::setHighlightChord);

        createAdapter(view.findViewById(R.id.scales_view),
                Music.SCALES,
                mInstrument::setHighlightScale);

        return view;
    }

    private <T> void createAdapter(RecyclerView recycler, T[] items, OnItemClickListener listener) {
        recycler.setAdapter(new ButtonToggleListAdapter<>(items, listener));
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setHasFixedSize(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mInstrument = ((MainActivity) context).instrument;
        }
    }
}
