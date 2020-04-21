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
import fi.sntr.fretboard.adapters.ChordResultAdapter;
import fi.sntr.fretboard.music.Instrument;

/**
 * Fragment for chord comparison results
 */
public class ChordsFragment extends Fragment {

    private Instrument mInstrument;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_chords, container, false);

        RecyclerView chordsRecycler = view.findViewById(R.id.compare_view);
        chordsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ChordResultAdapter chordsAdapter = new ChordResultAdapter(mInstrument);
        chordsRecycler.setAdapter(chordsAdapter);

        chordsRecycler.setItemAnimator(null);

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
