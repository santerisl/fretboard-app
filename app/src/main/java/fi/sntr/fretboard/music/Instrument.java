package fi.sntr.fretboard.music;

import java.util.ArrayList;
import java.util.List;

public class Instrument {
    public static final int MIN_FRETS = 6;
    public static final int MAX_FRETS = 19;

    private List<InstrumentChangeListener> listeners = new ArrayList<>();

    private boolean isSharp = true;
    private int fretCount = 1;
    private int[] rootNotes = new int[0];
    private int[] selectedFrets = new int[0];

    private int highlightRoot = -1;
    private NoteGroup highlight;

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

    public void setHighlight(NoteGroup noteGroup) {
        this.highlight = noteGroup;
        if(this.highlightRoot < 0) {
            this.highlightRoot = 0;
        }

        for(InstrumentChangeListener listener: listeners) {
            listener.onHighlightChange();
        }
    }

    public void setHighlightRoot(int root) {
        if(this.highlightRoot != root) {
            this.highlightRoot = root;
            for(InstrumentChangeListener listener: listeners) {
                listener.onHighlightChange();
            }
        }
    }

    public int getSelected(int string) {
        return selectedFrets[string];
    }

    public boolean isSelected(int string, int fret) {
        return selectedFrets[string] == fret;
    }

    public boolean isHighlighted(int string, int fret) {
        boolean isHighlighted = highlightRoot < 0 || highlight == null;
        for(int i = 0; !isHighlighted && i < highlight.getIntervalCount(); i++) {
            int note = (highlight.get(i) + highlightRoot) % Music.NOTE_COUNT;
            if(note == getNoteNumber(string, fret)) {
                isHighlighted = true;
            }
        }
        return isHighlighted;
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
        if(fretCount > MAX_FRETS) {
            fretCount = MAX_FRETS;
        }
        if(fretCount < MIN_FRETS) {
            fretCount = MIN_FRETS;
        }

        if(this.fretCount != fretCount) {
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
    }

    public void setNoteNamesSharp() {
        if(!isSharp) {
            toggleSharp();
        }
    }

    public void setNoteNamesFlat() {
        if(isSharp) {
            toggleSharp();
        }
    }

    private void toggleSharp() {
        isSharp = !isSharp;

        for(InstrumentChangeListener listener: listeners) {
            listener.onIsSharpChange(this.isSharp);
        }
    }

    public interface InstrumentChangeListener {
        void onSelectedChange(int string, int oldFret, int newFret);
        void onFretCountChange(int oldFretCount, int newFretCount);
        void onIsSharpChange(boolean isSharp);
        void onHighlightChange();
    }
}
