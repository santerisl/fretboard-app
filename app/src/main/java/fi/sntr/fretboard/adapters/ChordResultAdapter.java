package fi.sntr.fretboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.CompareResult;
import fi.sntr.fretboard.music.Instrument;

public class ChordResultAdapter extends RecyclerView.Adapter<ChordResultAdapter.ResultViewHolder> {

    private List<CompareResult> items;
    private Instrument mInstrument;

    public ChordResultAdapter(List<CompareResult> items, Instrument instrument) {
        this.items = items;
        this.mInstrument = instrument;
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
        holder.notesRecycler.setAdapter(new ChordNotesAdapter(r));

        holder.highlightButton.setActivated(r.isHighlighted());

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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView chordName;
        RecyclerView notesRecycler;
        Button highlightButton;

        public ResultViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            chordName = view.findViewById(R.id.chord_name_text);
            notesRecycler = view.findViewById(R.id.notes_recycler);
            highlightButton = view.findViewById(R.id.change_highlight_button);


            notesRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
    }
}
