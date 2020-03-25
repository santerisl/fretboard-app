package fi.sntr.fretboard.tabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.sntr.fretboard.MainActivity;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.Instrument;

public class HighlightFragment extends Fragment {

    private Instrument mInstrument;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_highlight, container, false);

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
