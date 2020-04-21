package fi.sntr.fretboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fi.sntr.fretboard.R;
import fi.sntr.fretboard.music.CompareResult;
import fi.sntr.fretboard.music.Music;
import fi.sntr.fretboard.music.NoteGroup;

/**
 * Adapter for displaying a list of notes found or not found in a ChordResult
 */
public class ChordNotesAdapter extends RecyclerView.Adapter<ChordNotesAdapter.NoteViewHolder> {

    private CompareResult result;
    private NoteGroup chord;

    public ChordNotesAdapter() {}

    public void setResult(CompareResult result) {
        this.result = result;
        chord = Music.CHORDS[result.chordId];
        notifyDataSetChanged();
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
        holder.noteName.setText(Music.getNoteName(chord.get(position) + result.root));
        holder.intervalName.setText(Music.INTERVALS[chord.get(position)]);
        holder.noteName.setSelected(result.hasNote(chord.get(position)));
    }

    @Override
    public long getItemId(int position) {
        return chord != null ? chord.get(position) : -1;
    }

    @Override
    public int getItemCount() {
        return result != null ? chord.getIntervalCount() : 0;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView noteName;
        final TextView intervalName;

        NoteViewHolder(@NonNull View view) {
            super(view);
            noteName = view.findViewById(R.id.note_name_text);
            intervalName = view.findViewById(R.id.note_interval_text);
        }
    }
}
