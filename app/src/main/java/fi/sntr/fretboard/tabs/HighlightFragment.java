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
import fi.sntr.fretboard.adapters.TextListAdapter;
import fi.sntr.fretboard.music.Instrument;
import fi.sntr.fretboard.music.Music;
import fi.sntr.fretboard.music.NoteGroup;

public class HighlightFragment extends Fragment {

    private Instrument mInstrument;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_highlight, container, false);

        RecyclerView noteRecycler = view.findViewById(R.id.notes_view);
        TextListAdapter<String> mNotesAdapter = new TextListAdapter<>(Music.NAMES_SHARP,
                (position) -> mInstrument.setHighlightRoot(position));

        noteRecycler.setAdapter(mNotesAdapter);
        noteRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView chordsRecycler = view.findViewById(R.id.chords_view);
        TextListAdapter<NoteGroup> mChordsAdapter = new TextListAdapter<>(Music.CHORDS,
                (position) -> mInstrument.setHighlight(Music.CHORDS[position]));
        chordsRecycler.setAdapter(mChordsAdapter);
        chordsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView scalesRecycler = view.findViewById(R.id.scales_view);
        TextListAdapter<NoteGroup> mScalesAdapter = new TextListAdapter<>(Music.SCALES,
                (position) -> mInstrument.setHighlight(Music.SCALES[position]));
        scalesRecycler.setAdapter(mScalesAdapter);
        scalesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mInstrument = ((MainActivity) context).instrument;
        }
    }
}
