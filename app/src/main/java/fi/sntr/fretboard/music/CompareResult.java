package fi.sntr.fretboard.music;

import java.util.List;

public class CompareResult implements Comparable<CompareResult> {
    public int root;
    public int chordId;
    public List<Integer> found;
    public List<Integer> missing;
    public List<Integer> extra;

    @Override
    public String toString() {
        return root + ": (" + found.toString() + ", " + missing.toString() + ", " + extra.toString() + ")";
    }

    @Override
    public int compareTo(CompareResult other) {
        return missing.size() - other.missing.size();
    }
}
