package fi.sntr.fretboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.CompareResult;

public class ChordResultAdapter extends RecyclerView.Adapter<ChordResultAdapter.ResultViewHolder> {

    private List<CompareResult> items;

    public ChordResultAdapter(List<CompareResult> items) {
        this.items = items;
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
        holder.chordName.setText(r.getChordName());
        holder.notesRecycler.setAdapter(new ChordNotesAdapter(r));
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
        TextView chordName;
        RecyclerView notesRecycler;

        public ResultViewHolder(@NonNull View view) {
            super(view);
            chordName = view.findViewById(R.id.chord_name_text);
            notesRecycler = view.findViewById(R.id.notes_recycler);


            notesRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
    }
}
