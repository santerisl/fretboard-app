package fi.sntr.fretboard.music;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for holding information about fretted instrument
 */
public class Instrument {
    public static final int MIN_FRETS = 6;
    public static final int MAX_FRETS = 19;

    private final List<InstrumentChangeListener> listeners = new ArrayList<>();

    private boolean isSharp = true;
    private int fretCount = 1;
    private int[] rootNotes = new int[0];

    private int[] selectedFrets = new int[0];

    private int highlightRoot = -1;
    private int highlightChord = -1;
    private int highlightScale = -1;

    /**
     * Empty constructor
     */
    public Instrument() {}

    /**
     * Sets all the root notes for this instruments and notifies listeners
     * {@link InstrumentChangeListener#onInstrumentChange()}
     * @param rootNotes the root notes to set
     */
    public void setRootNotes(int ...rootNotes) {
        this.rootNotes = rootNotes;
        this.selectedFrets = new int[rootNotes.length];

        for(InstrumentChangeListener listener: listeners) {
            listener.onInstrumentChange();
        }
    }

    /**
     * Gets the note number of the given fret on the given string
     *
     * @param string string to get fret on
     * @param fret fret to get
     * @return note name of selected note
     */
    public int getNoteNumber(int string, int fret) {
        return Music.getNoteNumber(rootNotes[string] + fret);
    }

    /**
     * @param string string to get note number on
     * @return selected fret number on given string, or -1 if no fret selected on the string
     */
    public int getSelectedNoteNumber(int string) {
        if(selectedFrets[string] >= 0) {
            return Music.getNoteNumber(rootNotes[string] + selectedFrets[string]);
        } else {
            return -1;
        }
    }

    /**
     * Gets the note name at the given fret on the give string
     * @param string string to get fret on
     * @param fret fret to get
     * @return name of note
     */
    public String getNote(int string, int fret) {
        int note = getNoteNumber(string, fret);
        return isSharp ? Music.NAMES_SHARP[note] : Music.NAMES_FLAT[note];
    }

    /**
     * Sets the given fret to selected on the give string and notifies listeners
     * {@link InstrumentChangeListener#onSelectedChange(int, int, int)}
     * @param string string to set selected fret on
     * @param fret fret to select on the given string
     */
    public void setSelected(int string, int fret) {
        int oldFret = selectedFrets[string];
        selectedFrets[string] = selectedFrets[string] == fret ? -1 : fret;

        for(InstrumentChangeListener listener: listeners) {
            listener.onSelectedChange(string, oldFret, selectedFrets[string]);
        }
    }

    /**
     * Sets the currently highlighted chord
     * @param chordId chord id to highlight
     */
    public void setHighlightChord(int chordId) {
        this.highlightChord = chordId;
        for(InstrumentChangeListener listener: listeners) {
            listener.onHighlightChange();
        }
    }

    /**
     * Sets the currently highlighted scale
     * @param scaleId scale id to highlight
     */
    public void setHighlightScale(int scaleId) {
        this.highlightScale = scaleId;

        for(InstrumentChangeListener listener: listeners) {
            listener.onHighlightChange();
        }
    }

    /**
     * Sets the root note of the highlighted scale and chord and notifies listeners
     * @param root highlight root note to set
     */
    public void setHighlightRoot(int root) {
        if(this.highlightRoot != root) {
            this.highlightRoot = root;
            for(InstrumentChangeListener listener: listeners) {
                listener.onHighlightChange();
            }
        }
    }

    /**
     * @param string string to check fret on
     * @param fret fret to check
     * @return true if the given fret is currently selected, false otherwise
     */
    public boolean isSelected(int string, int fret) {
        return selectedFrets[string] == fret;
    }

    /**
     * Checks if the given fret on the given string is contained in the currently highlighted chord
     * @param string string to check fret on
     * @param fret fret to check
     * @return true if given fret is included in the highlighted chord, false otherwise
     */
    public boolean isHighlightedChord(int string, int fret) {
        if(highlightChord >= 0) {
            return isHighlighted(Music.CHORDS[highlightChord], string, fret);
        } else {
            return true;
        }
    }

    /**
     * Checks if the given fret on the given string is contained in the currently highlighted scale
     * @param string string to check fret on
     * @param fret fret to check
     * @return true if given fret is included in the highlighted scale, false otherwise
     */
    public boolean isHighlightedScale(int string, int fret) {
        if(highlightScale >= 0) {
            return isHighlighted(Music.SCALES[highlightScale], string, fret);
        } else {
            return true;
        }
    }

    private boolean isHighlighted(NoteGroup group, int string, int fret) {
        boolean isHighlighted = highlightRoot < 0;
        for(int i = 0; !isHighlighted && i < group.getIntervalCount(); i++) {
            int note = (group.get(i) + highlightRoot) % Music.NOTE_COUNT;
            if(note == getNoteNumber(string, fret)) {
                isHighlighted = true;
            }
        }
        return isHighlighted;
    }

    /**
     * @return current fret count on the instrument
     */
    public int getFretCount() {
        return fretCount;
    }

    /**
     * @return current string count on the instrument
     */
    public int getStringCount() {
        return rootNotes.length;
    }

    /**
     * Adds a new listener for instrument change events
     * @param listener instrument listener to add
     */
    public void addChangeListener(InstrumentChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Sets the current fret count on the instrument and notifies listeners
     *
     * Fret count value is clamped to be between
     * {@link Instrument#MIN_FRETS} and {@link Instrument#MAX_FRETS}
     *
     * @param fretCount fret count to set
     */
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

    /**
     * Sets the current note display type to sharp (#)
     */
    public void setNoteNamesSharp() {
        if(!isSharp) {
            toggleSharp();
        }
    }

    /**
     * Sets the current note display type to flat (b)
     */
    public void setNoteNamesFlat() {
        if(isSharp) {
            toggleSharp();
        }
    }

    private void toggleSharp() {
        isSharp = !isSharp;

        for(InstrumentChangeListener listener: listeners) {
            listener.onIsSharpChange();
        }
    }

    /**
     * @return current highlight root note
     */
    public int getHighlightRoot() {
        return highlightRoot;
    }

    /**
     * @return current highlight chord id
     */
    public int getHighlightChord() {
        return highlightChord;
    }

    /**
     * @return current highlight scale id
     */
    public int getHighlightScale() {
        return highlightScale;
    }

    /**
     * Listener for instrument change events
     */
    public interface InstrumentChangeListener {
        /**
         * Called when the root notes of the instrument change
         */
        void onInstrumentChange();

        /**
         * Called when a selected fret on a string changes
         * @param string the string the change happened on
         * @param oldFret the previously selected fret
         * @param newFret the newly selected fret
         */
        void onSelectedChange(int string, int oldFret, int newFret);

        /**
         * Called when the instrument fret count changes
         * @param oldFretCount previous fret count
         * @param newFretCount new fret count
         */
        void onFretCountChange(int oldFretCount, int newFretCount);

        /**
         * Called when the instrument is swapped between sharp and flat notes
         */
        void onIsSharpChange();

        /**
         * Called when the highlighted scale, chord or highlight root note is changed
         */
        void onHighlightChange();
    }
}
