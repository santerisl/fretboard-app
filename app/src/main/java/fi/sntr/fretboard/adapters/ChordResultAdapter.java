package fi.sntr.fretboard.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.ChordFinder;
import fi.sntr.fretboard.music.CompareResult;
import fi.sntr.fretboard.music.Instrument;

public class ChordResultAdapter extends RecyclerView.Adapter<ChordResultAdapter.ResultViewHolder>
    implements Instrument.InstrumentChangeListener {

    private Instrument mInstrument;

    private List<CompareResult> items = new ArrayList<>();
    private Set<Integer> selected = new HashSet<>();
    private Set<Integer> previousSelected = new HashSet<>();

    public ChordResultAdapter(Instrument instrument) {
        mInstrument = instrument;
        mInstrument.setChangeListener(this);

        setHasStableIds(true);

        updateSelected();
        updateItems();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.compare_result_item, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        CompareResult r = items.get(position);
        holder.view.setActivated(r.isHighlighted());
        holder.chordName.setText(r.getChordName());

        holder.highlightButton.setActivated(r.isHighlighted());
        holder.adapter.setResult(r);

        holder.highlightButton.setOnClickListener((l) -> {
            if(!r.isHighlighted()) {
                mInstrument.setHighlightRoot(r.root);
                mInstrument.setHighlightChord(r.chordId);

            } else {
                mInstrument.setHighlightRoot(-1);
                mInstrument.setHighlightChord(-1);
            }
        });
    }

    private void updateItems() {
        items.clear();
        items.addAll(ChordFinder.findChords(selected,
                mInstrument.getHighlightRoot(),
                mInstrument.getHighlightChord()));
    }

    @Override
    public long getItemId(int position) {
        CompareResult r = items.get(position);
        return (r.chordId << 16) | r.root;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onInstrumentChange() {}

    @Override
    public void onSelectedChange(int string, int oldFret, int newFret) {
        if(updateSelected()) {
            updateItems();
            notifyDataSetChanged();
        }
    }

    private boolean updateSelected() {
        previousSelected.clear();
        previousSelected.addAll(selected);

        selected.clear();
        for (int i = 0; i < mInstrument.getStringCount(); i++) {
            int baseNote = mInstrument.getSelectedNoteNumber(i);
            if(baseNote >= 0) {
                selected.add(baseNote);
            }
        }
        return !previousSelected.equals(selected);
    }

    @Override
    public void onFretCountChange(int oldFretCount, int newFretCount) {}

    @Override
    public void onIsSharpChange(boolean isSharp) {}

    @Override
    public void onHighlightChange() {
        updateItems();
        notifyDataSetChanged();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView chordName;
        RecyclerView notesRecycler;
        ChordNotesAdapter adapter;
        Button highlightButton;

        public ResultViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            chordName = view.findViewById(R.id.chord_name_text);
            notesRecycler = view.findViewById(R.id.notes_recycler);
            highlightButton = view.findViewById(R.id.change_highlight_button);

            notesRecycler.setLayoutManager(new LinearLayoutManager(
                    view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            adapter = new ChordNotesAdapter();
            notesRecycler.setAdapter(adapter);
        }
    }
}
