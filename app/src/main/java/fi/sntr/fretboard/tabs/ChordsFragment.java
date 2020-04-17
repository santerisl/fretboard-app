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

        TextView selectedText = view.findViewById(R.id.selected_text);
        TextView highlightText = view.findViewById(R.id.highlight_text);

        RecyclerView chordsRecycler = view.findViewById(R.id.compare_view);
        chordsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ChordsAdapter chordsAdapter = new ChordsAdapter(compareResults);
        chordsRecycler.setAdapter(chordsAdapter);


        mInstrument.setChangeListener(new Instrument.InstrumentChangeListener() {
            @Override
            public void onInstrumentChange() { }

            @Override
            public void onSelectedChange(int string, int oldFret, int newFret) {
                selected.clear();
                String text = "";
                for(int i = 0; i < mInstrument.getStringCount(); i++) {
                    if(mInstrument.getSelected(i) >= 0) {
                        text += mInstrument.getNote(i, mInstrument.getSelected(i)) + " | ";
                        selected.add(mInstrument.getNoteNumber(i, mInstrument.getSelected(i)));
                    }
                }
                if(text.length() >= 3) {
                    text = text.substring(0, text.length() - 2);
                }
                selectedText.setText(text + selected.toString());

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
                if(mInstrument.getHighlightChord() != null && mInstrument.getHighlightRoot() >= 0) {
                    String root = (Music.NAMES_SHARP[mInstrument.getHighlightRoot()]);
                    String name = (mInstrument.getHighlightChord().getName());
                    String intervals = (mInstrument.getHighlightChord().getIntervalString());

                    highlightText.setText(root + name + intervals);

                } else {
                    highlightText.setText("");
                }
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
        selectedNotes.clear();
        for(int i = 0; i < mInstrument.getStringCount(); i++) {
            int note = mInstrument.getNoteNumber(i, mInstrument.getSelected(i));
            if(note > -1 && !selectedNotes.contains(note)) {
                selectedNotes.add(note);
            }
        }

        compareResults.clear();
        compareResults.addAll(ChordFinder.findChords(selectedNotes));
    }

    class ChordsAdapter extends RecyclerView.Adapter<CompareViewHolder> {

        private List<CompareResult> items;

        public ChordsAdapter(List<CompareResult> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public CompareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.compare_result_item, parent, false);
            return new CompareViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CompareViewHolder holder, int position) {
            CompareResult r = items.get(position);
            String name = Music.NAMES_SHARP[r.root] + Music.CHORDS[r.chordId].getName();
            String missing = "";
            if(r.missing.size() > 0) {
                missing = "(Missing " + r.missing.size() + " notes)";
            }
            holder.textView.setText(name + ", " + missing + r.toString());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    static class CompareViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CompareViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);

        }
    }
}
