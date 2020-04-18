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
    private NoteGroup highlightChord;
    private NoteGroup highlightScale;

    public Instrument() {}

    public void setRootNotes(int ...rootNotes) {
        this.rootNotes = rootNotes;
        this.selectedFrets = new int[rootNotes.length];

        for(InstrumentChangeListener listener: listeners) {
            listener.onInstrumentChange();
        }
    }

    public int getNoteCount() {
        return fretCount * rootNotes.length;
    }

    public int getNoteNumber(int string, int fret) {
        return Music.getNoteNumber(rootNotes[string] + fret);
    }

    public int getSelectedNoteNumber(int string) {
        if(selectedFrets[string] >= 0) {
            return Music.getNoteNumber(rootNotes[string] + selectedFrets[string]);
        } else {
            return -1;
        }
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

    public void setHighlightChord(NoteGroup noteGroup) {
        this.highlightChord = noteGroup;
        for(InstrumentChangeListener listener: listeners) {
            listener.onHighlightChange();
        }
    }

    public void setHighlightScale(NoteGroup noteGroup) {
        this.highlightScale = noteGroup;

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

    public boolean isHighlightedChord(int string, int fret) {
        return isHighlighted(highlightChord, string, fret);
    }

    public boolean isHighlightedScale(int string, int fret) {
        return isHighlighted(highlightScale, string, fret);
    }

    private boolean isHighlighted(NoteGroup group, int string, int fret) {
        boolean isHighlighted = highlightRoot < 0 || group == null;
        for(int i = 0; !isHighlighted && i < group.getIntervalCount(); i++) {
            int note = (group.get(i) + highlightRoot) % Music.NOTE_COUNT;
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

    public int getHighlightRoot() {
        return highlightRoot;
    }

    public NoteGroup getHighlightChord() {
        return highlightChord;
    }

    public NoteGroup getHighlightScale() {
        return highlightScale;
    }

    public interface InstrumentChangeListener {
        void onInstrumentChange();
        void onSelectedChange(int string, int oldFret, int newFret);
        void onFretCountChange(int oldFretCount, int newFretCount);
        void onIsSharpChange(boolean isSharp);
        void onHighlightChange();
    }
}
