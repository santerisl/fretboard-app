package fi.sntr.fretboard.music;

import androidx.annotation.NonNull;

import java.util.Set;

/**
 * Container class for a result of comparing chords
 * {@see ChordFinder}
 */
public class CompareResult implements Comparable<CompareResult> {
    /** root note used in this comparison */
    public final int root;
    /** id of the chord used in this comparison */
    public final int chordId;

    private final int found;
    private final int extra;
    private boolean isHighlighted;
    /** count of notes missing from the full chord */
    public int missingCount;

    /**
     * @param root root note of the compared chord
     * @param chordId id of the compared chord
     * @param found found bits for the compared chord
     * @param missing missing bits for the compared chord
     * @param extra extra bits for the compared chord
     */
    public CompareResult(int root, int chordId, int found, int missing, int extra) {
        this.root = root;
        this.chordId = chordId;
        this.found = found;
        this.extra = extra;

        while (missing > 0) {
            missingCount += missing & 1;
            missing = missing >> 1;
        }
    }

    /**
     * @return name of this chord including the root note name and chord name
     */
    public String getChordName() {
        return String.format("%s%s", Music.NAMES_SHARP[root], Music.CHORDS[chordId].getName());
    }

    /**
     * Checks if the comparison included the given note
     * @param note the note to check
     * @return true if the comparison included the given note, false otherwise
     */
    public boolean hasNote(int note) {
        return (found & (1 << Music.getNoteNumber(note))) > 0;
    }

    /**
     * Compares the results according to the results highlighted status and missing note count
     */
    @Override
    public int compareTo(@NonNull CompareResult other) {
        if(isHighlighted || other.isHighlighted) {
            return (other.isHighlighted == isHighlighted ? 0 : (isHighlighted ? -1 : 1));
        }
        return this.missingCount - other.missingCount;
    }

    /**
     * @return true if this comparison had extra notes, false otherwise
     */
    public boolean hasExtraNotes() {
        return extra > 0;
    }

    /**
     * @return true if this result is highlighted
     * @see ChordFinder#findChords(Set, int, int)
     */
    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * @param highlighted the highlighted value to set
     */
    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    /**
     * @param root root of the chord to compare to
     * @param chordId id of the chord to compare to
     * @return true if both root and id are equal, false otherwise
     */
    public boolean equalsChord(int root, int chordId) {
        return this.chordId == chordId && this.root == root;
    }
}
