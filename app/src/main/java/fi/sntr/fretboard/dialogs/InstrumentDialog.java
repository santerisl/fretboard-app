package fi.sntr.fretboard.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import fi.sntr.fretboard.R;
import fi.sntr.fretboard.data.InstrumentData;
import fi.sntr.fretboard.music.Music;

/**
 * Dialog for adding new instrument
 *
 * Not pretty.
 */
public class InstrumentDialog extends DialogFragment {

    private StringsAdapter stringsAdapter;

    public interface InstrumentDialogListener {
        void onSaveClick(InstrumentData instrumentData);
    }

    private Dialog dialog;
    private Button saveButton;
    private InstrumentDialogListener listener;

    private final List<Integer> instrumentStrings = new ArrayList<>();

    public static InstrumentDialog newInstance() {
        return new InstrumentDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_instrument, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputEditText nameEdit = view.findViewById(R.id.instrument_name_edit);

        saveButton = view.findViewById(R.id.save_instrument_button);
        saveButton.setEnabled(false);
        saveButton.setOnClickListener((l) -> {
            int[] strings = new int[instrumentStrings.size()];
            for(int i = 0; i < instrumentStrings.size(); i++) {
                strings[i] = instrumentStrings.get(i);
            }
            String name = nameEdit.getText().toString();

            InstrumentData data = new InstrumentData(name, strings);
            listener.onSaveClick(data);
            dismiss();
        });

        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener((l) -> dismiss());

        RecyclerView stringsRecycler = view.findViewById(R.id.instrument_strings_recycler);
        stringsAdapter = new StringsAdapter();
        stringsRecycler.setAdapter(stringsAdapter);
        stringsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView notesRecycler = view.findViewById(R.id.add_string_recycler);
        notesRecycler.setAdapter(new NotesAdapter());
        notesRecycler.setLayoutManager(new GridLayoutManager(
                getContext(),
                6,
                GridLayoutManager.VERTICAL,
                false));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof InstrumentDialogListener) {
            listener = (InstrumentDialogListener) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    private class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

        @NonNull
        @Override
        public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.instrument_add_string_button, parent, false);
            return new NoteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
            holder.button.setText(Music.NAMES_SHARP[position]);
            holder.button.setOnClickListener((l) -> {
                if(instrumentStrings.size() < 6) {
                    instrumentStrings.add(position);
                    saveButton.setEnabled(instrumentStrings.size() > 0);
                    stringsAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return Music.NAMES_SHARP.length;
        }
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        final Button button;
        NoteViewHolder(@NonNull View view) {
            super(view);
            button = view.findViewById(R.id.add_string_button);
        }
    }

    private class StringsAdapter extends RecyclerView.Adapter<StringViewHolder> {

        @NonNull
        @Override
        public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.instrument_string_item, parent, false);
            return new StringViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
            holder.noteText.setText(Music.getNoteName(instrumentStrings.get(position)));
            holder.removeButton.setOnClickListener((l) -> {
                instrumentStrings.remove(position);
                saveButton.setEnabled(instrumentStrings.size() > 0);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return instrumentStrings.size();
        }
    }

    /**
     * View holder for instrument string with remove button.
     */
    static class StringViewHolder extends RecyclerView.ViewHolder {
        final Button removeButton;
        final TextView noteText;
        StringViewHolder(@NonNull View view) {
            super(view);
            noteText = view.findViewById(R.id.string_note_text);
            removeButton = view.findViewById(R.id.remove_string_button);
        }
    }
}
