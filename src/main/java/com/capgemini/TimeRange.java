package com.capgemini;

public class TimeRange implements Comparable<TimeRange> {

    private static final int SPACE_ASCII_VALUE = 32;

    private final int from;
    private final int to;
    private final int duration;

    public TimeRange(int from, int to) {
        this.from = from;
        this.to = to;
        this.duration = to - from;
    }

    static public TimeRange parseTimeRange(String line) {

        int firstValue = getIntValue(line);
        int secondValue = getIntValue(skipToNextValue(line));

        return new TimeRange(firstValue, secondValue);
    }

    private static int getIntValue(String line) {
        int divider = line.indexOf(SPACE_ASCII_VALUE);
        if (divider < 0)
            divider = line.length();
        return Integer.parseInt(line.substring(0, divider));
    }

    private static String skipToNextValue(String line) {
        return line.substring(line.indexOf(SPACE_ASCII_VALUE) + 4);
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "TimeRange{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public int compareTo(TimeRange that) {
        return duration - that.duration;
    }
}
