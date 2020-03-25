package fi.sntr.fretboard.music;

import java.util.ArrayList;
import java.util.List;

public class Instrument {
    private List<InstrumentChangeListener> listeners = new ArrayList<>();

    private boolean isSharp = true;
    private int fretCount = 1;
    private int[] rootNotes = new int[0];
    private int[] selectedFrets = new int[0];

    public Instrument() {}

    public void setRootNotes(int ...rootNotes) {
        this.rootNotes = rootNotes;
        this.selectedFrets = new int[rootNotes.length];
    }

    public int getNoteCount() {
        return fretCount * rootNotes.length;
    }

    public int getNoteNumber(int string, int fret) {
        return (rootNotes[string] + fret) % Music.NOTE_COUNT;
    }

    public String getNote(int string, int fret) {
        int note = getNoteNumber(string, fret);
        return isSharp ? Music.NAMES_SHARP[note] : Music.NAMES_FLAT[note];
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

    public interface InstrumentChangeListener {
        void onSelectedChange(int string, int oldFret, int newFret);
        void onFretCountChange(int oldFretCount, int newFretCount);
        void onIsSharpChange(boolean isSharp);
    }
}
