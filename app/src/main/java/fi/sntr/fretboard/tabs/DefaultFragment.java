package fi.sntr.fretboard.tabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fi.sntr.fretboard.MainActivity;
import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.Instrument;

public class DefaultFragment extends Fragment {

    private Instrument mInstrument;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_default, container, false);

        TextView selectedText = view.findViewById(R.id.selected_text);

        mInstrument.setChangeListener(new Instrument.InstrumentChangeListener() {
            @Override
            public void onSelectedChange(int string, int oldFret, int newFret) {
                String text = "";
                for(int i = 0; i < mInstrument.getStringCount(); i++) {
                    if(mInstrument.getSelected(i) >= 0) {
                        text += mInstrument.getNote(i, mInstrument.getSelected(i)) + " | ";
                    }
                }
                if(text.length() >= 3) {
                    text = text.substring(0, text.length() - 2);
                }
                selectedText.setText(text);
            }

            @Override
            public void onFretCountChange(int oldFretCount, int newFretCount) {
            }

            @Override
            public void onIsSharpChange(boolean isSharp) {

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
}
