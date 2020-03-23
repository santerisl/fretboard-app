package fi.sntr.fretboard;

import java.util.ArrayList;
import java.util.List;

public class Instrument {

    public static final int NOTE_COUNT = 12;

    public static final String[] noteNamesSharp = {
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };

    public static final String[] noteNamesFlat = {
            "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"
    };

    private List<InstrumentChangeListener> listeners = new ArrayList<>();

    private boolean isSharp = true;
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

    public int getNoteNumber(int string, int fret) {
        return (rootNotes[string] + fret) % NOTE_COUNT;
    }

    public String getNote(int string, int fret) {
        int note = getNoteNumber(string, fret);
        return isSharp ? noteNamesSharp[note] : noteNamesFlat[note];
    }

    public void setSelected(int string, int fret) {
        int oldFret = selectedFrets[string];
        selectedFrets[string] = selectedFrets[string] == fret ? -1 : fret;

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

    public void toggleSharp() {
        isSharp = !isSharp;

        for(InstrumentChangeListener listener: listeners) {
            listener.onIsSharpChange(this.isSharp);
        }
    }

    public void setFretCount(int fretCount) {
        int oldFretCount = this.fretCount;
        this.fretCount = fretCount;

        for(int i = 0; i < selectedFrets.length; i++) {
            if(selectedFrets[i] >= fretCount) {
                setSelected(i, -1);
            }
        }

        for(InstrumentChangeListener listener: listeners) {
            listener.onFretCountChange(oldFretCount, this.fretCount);
        }
    }

    interface InstrumentChangeListener {
        void onSelectedChange(int string, int oldFret, int newFret);
        void onFretCountChange(int oldFretCount, int newFretCount);
        void onIsSharpChange(boolean isSharp);
    }
}
