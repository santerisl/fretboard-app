package fi.sntr.fretboard.music;

import androidx.annotation.NonNull;

/**
 * Holder class for groups of intervals
 */
public class NoteGroup {
    private final String name;
    /** Intervals included in this note group */
    private final int[] intervals;
    private int bits;

    public NoteGroup(String name, int ...intervals) {
        this.name = name;
        this.intervals = intervals;

        for (int interval : intervals) {
            bits = bits | 1 << Music.getNoteNumber(interval);
        }
    }

    /**
     * @return the name of this group
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the name of this group, or "Major" if name is empty
     */
    private String getFullName() {
        return this.name.equals("") ? "Major" : this.name;
    }

    /**
     * @return the count of intervals in this group
     */
    public int getIntervalCount() {
        return intervals.length;
    }

    /**
     * @param interval the interval to get
     * @return the interval at the given position
     */
    public int get(int interval) {
        return intervals[interval];
    }

    /**
     * @return integer with bits on at the positions of this groups intervals
     */
    public int getBits() {
        return bits;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getFullName();
    }
}
