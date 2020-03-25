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

public class SettingsFragment extends Fragment {

    private Instrument mInstrument;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_settings, container, false);

        view.findViewById(R.id.button_add_frets).setOnClickListener(this::addFrets);
        view.findViewById(R.id.button_remove_frets).setOnClickListener(this::removeFrets);
        view.findViewById(R.id.button_toggle_sharp).setOnClickListener(this::toggleSharpFlat);
        return view;
    }

    public void addFrets(View view) {
        if(mInstrument.getFretCount() < 19) {
            mInstrument.setFretCount(mInstrument.getFretCount() + 1);
        }
    }

    public void removeFrets(View view) {
        if(mInstrument.getFretCount() > 6) {
            mInstrument.setFretCount(mInstrument.getFretCount() - 1);
        }
    }

    public void toggleSharpFlat(View view) {
        mInstrument.toggleSharp();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mInstrument = ((MainActivity) context).instrument;
        }
    }
}
