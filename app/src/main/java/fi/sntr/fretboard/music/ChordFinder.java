package fi.sntr.fretboard.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Class for comparing a set of notes with chords saved in {@link Music#CHORDS}
 */
public class ChordFinder {
    /**
     * Finds chords that are a close match to the given notes.
     *
     * returned chords always include the root note of the chord. If includeRoot and includeChord
     * are both >= 0, a chord with the includeChord id and includeRoot root note is always included
     * and highlighted in the result as the first item.
     *
     * Comparing is done within a single octave, all notes an octave apart from each other will
     * match each other.
     *
     * Gets the compared chords from {@link Music#CHORDS}
     *
     * @param notes the set of notes used for comparing
     * @param includeRoot always include this root note for the chord matching includeChord
     * @param includeChord always include this chord, even if root note is missing
     * @return a list of results ordered by missing note count
     */
    public static List<CompareResult> findChords(Set<Integer> notes, int includeRoot, int includeChord){
        List<CompareResult> returnChords = new ArrayList<>();
        CompareResult highlighted = null;
        for(Integer baseNote: notes) {
            int bits = getBits(notes, baseNote);
            for (int j = 0; j < Music.CHORDS.length; j++) {
                CompareResult cR = compareChord(bits, j, baseNote);
                if (cR.missingCount <= 12 && !cR.hasExtraNotes()) {
                    returnChords.add(cR);
                    if(cR.equalsChord(includeRoot,includeChord)) {
                        highlighted = cR;
                    }
                }
            }
        }

        // If included chord need to be added to results and it wasn't found during
        // comparison, highlight and add it separately
        if(includeChord >= 0 && includeRoot >= 0) {
            if(highlighted == null) {
                highlighted = findChord(includeRoot, includeChord, notes);
                returnChords.add(highlighted);
            }
            highlighted.setHighlighted(true);
        }

        Collections.sort(returnChords);

        return returnChords;
    }

    /**
     * Compare a chord with the given root to the given chord id using the set of selected notes.
     *
     * @param root root note of chord
     * @param chordId chord to compare to
     * @param selected set of selected notes
     * @return result for the comparison
     */
    private static CompareResult findChord(int root, int chordId, Set<Integer> selected) {
        int bits = getBits(selected, root);
        return compareChord(bits, chordId, root);
    }

    /**
     * Gets compare result by comparing the given bits to the given chords bits.
     */
    private static CompareResult compareChord(int bits, int chordId, int baseNote){
        int chordBits = Music.CHORDS[chordId].getBits();
        int found = bits & chordBits;
        int missing = chordBits ^ found;
        int extra = bits ^ found;
        return new CompareResult(baseNote, chordId, found, missing, extra);
    }

    /**
     * Returns integer with bits at note positions relative to the root set to one.
     *
     * If notes contains the root note, the first bit of the returned integer will be one.
     * For each item in the notes Set a bit will be set to one.
     *
     * @return number with bits on at notes positions relative to the root note
     */
    private static int getBits(Set<Integer> notes, int rootNote) {
        int bits = 0;
        for(Integer note: notes) {
            if(note >= 0) {
                bits = bits | 1 << Music.getNoteNumber(note - rootNote);
            }
        }
        return bits;
    }
}
