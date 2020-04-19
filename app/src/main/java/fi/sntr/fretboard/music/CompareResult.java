package fi.sntr.fretboard.music;

public class CompareResult implements Comparable<CompareResult> {
    public int root;
    public int chordId;
    private int found;
    private int missing;
    private int extra;

    public int missingCount;

    public CompareResult(int root, int chordId, int found, int missing, int extra) {
        this.root = root;
        this.chordId = chordId;
        this.found = found;
        this.missing = missing;
        this.extra = extra;

        while (missing > 0) {
            missingCount += missing & 1;
            missing = missing >> 1;
        }
    }

    @Override
    public String toString() {
        return "(" + root + ": "
                + Integer.toBinaryString(found) + ", "
                + Integer.toBinaryString(missing) + ", "
                + Integer.toBinaryString(extra) + ")";
    }

    public String getChordName() {
        return String.format("%s%s", Music.NAMES_SHARP[root], Music.CHORDS[chordId].getName());
    }

    public boolean hasNote(int note) {
        return (found & (1 << Music.getNoteNumber(note))) > 0;
    }

    @Override
    public int compareTo(CompareResult c) {
        return this.missingCount - c.missingCount;
    }

    public boolean hasExtraNotes() {
        return extra > 0;
    }
}
