package fi.sntr.fretboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.CompareResult;
import fi.sntr.fretboard.music.Music;
import fi.sntr.fretboard.music.NoteGroup;

public class ChordNotesAdapter extends RecyclerView.Adapter<ChordNotesAdapter.NoteViewHolder> {

    private CompareResult result;
    private NoteGroup chord;

    public ChordNotesAdapter(CompareResult result) {
        this.result = result;
        chord = Music.CHORDS[result.chordId];
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.compare_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        String notename = Music.getNoteName(chord.get(position) + result.root);
        String intervalname = Music.INTERVALS[chord.get(position)];
        holder.noteName.setText(notename);
        holder.intervalName.setText(intervalname);
        if(result.hasNote(chord.get(position))) {
            holder.noteName.setTextColor(ContextCompat.getColor(holder.view.getContext(), R.color.colorPrimary));
        } else {
            holder.noteName.setTextColor(ContextCompat.getColor(holder.view.getContext(), R.color.colorError));
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return chord.getIntervalCount();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteName;
        TextView intervalName;
        View view;

        public NoteViewHolder(@NonNull View view) {
            super(view);
            noteName = view.findViewById(R.id.note_name_text);
            intervalName = view.findViewById(R.id.note_interval_text);
            this.view = view;
        }
    }
}
