package fi.sntr.fretboard.music;

public class NoteGroup {
    private final String name;
    private final int[] intervals;

    public NoteGroup(String name, int ...intervals) {
        this.name = name;
        this.intervals = intervals;
    }

    public String getName() {
        return this.name.equals("") ? "Major" : this.name;
    }

    public String getIntervalString() {
        String result = "";
        for(int i = 0; i < intervals.length; i++) {
            result += "(" + Music.INTERVALS[intervals[i]] + ")";
        }
        return result;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}