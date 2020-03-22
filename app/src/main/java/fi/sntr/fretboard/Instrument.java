package fi.sntr.fretboard;

import java.util.ArrayList;
import java.util.List;

public class Instrument {

    public static String[] noteNamesSharp = {
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };

    private List<InstrumentChangeListener> listeners = new ArrayList<>();

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

    public void setSelected(int string, int fret) {
        int oldFret = selectedFrets[string];
        selectedFrets[string] = selectedFrets[string] == fret ? 0 : fret;

        for(InstrumentChangeListener listener: listeners) {
            listener.onSelectedChange(string, oldFret, selectedFrets[string]);
        }
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

    public void setChangeListener(InstrumentChangeListener listener) {
        listeners.add(listener);
    }

    public void setFretCount(int fretCount) {
        this.fretCount = fretCount;

        for(int i = 0; i < selectedFrets.length; i++) {
            if(selectedFrets[i] >= fretCount) {
                setSelected(i, fretCount - 1);
            }
        }

        for(InstrumentChangeListener listener: listeners) {
            listener.onFretCountChange(this.fretCount);
        }
    }

    interface InstrumentChangeListener {
        void onSelectedChange(int string, int oldFret, int newFret);
        void onFretCountChange(int fretCount);
    }
}
