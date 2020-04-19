package fi.sntr.fretboard.music;

import android.util.Log;

public class NoteGroup {
    private final String name;
    public final int[] intervals;
    private int bits;
    public NoteGroup(String name, int ...intervals) {
        this.name = name;
        this.intervals = intervals;

        for(int i = 0; i < intervals.length; i++) {
            bits = bits | 1 << Music.getNoteNumber(intervals[i]);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.name.equals("") ? "Major" : this.name;
    }

    public String getIntervalString() {
        String result = "";
        for(int i = 0; i < intervals.length; i++) {
            result += "(" + Music.INTERVALS[intervals[i]] + ")";
        }
        return result;
    }

    public int getIntervalCount() {
        return intervals.length;
    }

    public int get(int interval) {
        return intervals[interval];
    }

    public int getBits() {
        return bits;
    }

    @Override
    public String toString() {
        return this.getFullName();
    }
}
