package fi.sntr.fretboard;

import java.util.ArrayList;
import java.util.List;

public class Instrument {

    public static String[] noteNamesSharp = {
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };

    private List<SelectedChangeListener> listeners = new ArrayList<>();

    private int fretCount;
    private int[] rootNotes;
    private int[] selectedFrets;

    public Instrument(int fretCount, int ...rootNotes) {
        this.rootNotes = rootNotes;
        this.selectedFrets = new int[rootNotes.length];
        this.fretCount = fretCount;
    }

    public int getNoteCount() {
        return fretCount * rootNotes.length;
    }

    public String getNote(int string, int fret) {
        int note = (rootNotes[string] + fret) % noteNamesSharp.length;
        return noteNamesSharp[note];
    }

    public int setSelected(int string, int fret) {
        selectedFrets[string] = selectedFrets[string] == fret ? 0 : fret;

        for(SelectedChangeListener listener: listeners) {
            listener.onSelectedChange(string, fret, selectedFrets[string]);
        }

        return selectedFrets[string];
    }

    public int getSelected(int string) {
        return selectedFrets[string];
    }

    public boolean isSelected(int string, int fret) {
        return selectedFrets[string] == fret;
    }

    public int getFretCount() {
        return fretCount;
    }

    public int getStringCount() {
        return rootNotes.length;
    }

    public void setSelectedChangeListener(SelectedChangeListener listener) {
        listeners.add(listener);
    }

    interface SelectedChangeListener {
        void onSelectedChange(int string, int oldFret, int newFret);
    }
}
