package fi.sntr.fretboard.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ChordFinder {

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

    public static CompareResult findChord(int root, int chordId, Set<Integer> selected) {
        int bits = getBits(selected, root);
        return compareChord(bits, chordId, root);
    }

    private static CompareResult compareChord(int bits, int chordIndex, int baseNote){
        int chordBits = Music.CHORDS[chordIndex].getBits();
        int found = bits & chordBits;
        int missing = chordBits ^ found;
        int extra = bits ^ found;
        return new CompareResult(baseNote, chordIndex, found, missing, extra);
    }

    private static int getBits(Set<Integer> notes, int baseNote) {
        int bits = 0;
        for(Integer note: notes) {
            if(note >= 0) {
                bits = bits | 1 << Music.getNoteNumber(note - baseNote);
            }
        }
        return bits;
    }
}
