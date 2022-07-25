package com.capgemini;

import java.sql.Time;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IdleTimeServerWindowFinder {

    private static final int BEGINNING_OF_THE_DAY = 0;
    private static final int END_OF_THE_DAY = 1440;

    /**
     * When no idle window found,
     * returns default start value = -1
     */
    private static int start = -1;

    public int getMaintenanceWindowStart(List<TimeRange> busyTimeRanges, short windowDuration) {
        busyTimeRanges.sort(Comparator.reverseOrder());
        reduceOverlaps(busyTimeRanges);
        busyTimeRanges.sort(TimeRange::compareTo);
        findFirsValidIdleWindow(new TimeRange(BEGINNING_OF_THE_DAY, END_OF_THE_DAY), busyTimeRanges, windowDuration);
        return start;
    }

    private static void findFirsValidIdleWindow(TimeRange timeRangeLeft, List<TimeRange> busyTimeRanges, int windowDuration) {
        if (busyTimeRanges.isEmpty()) {
            if (timeRangeLeft.getDuration() >= windowDuration)
                start = timeRangeLeft.getFrom();
            return;
        }

        TimeRange currentBusyTimeRange = busyTimeRanges.get(0);
        List<TimeRange> busyTimeRangesLeft = busyTimeRanges.subList(1, busyTimeRanges.size());

        TimeRange newTimeRangeLeft1 = new TimeRange(timeRangeLeft.getFrom(), currentBusyTimeRange.getFrom());
        if (newTimeRangeLeft1.getDuration() >= windowDuration) {
            start = newTimeRangeLeft1.getFrom();
            return;
        }

        findFirsValidIdleWindow(new TimeRange(currentBusyTimeRange.getTo(), timeRangeLeft.getTo()), busyTimeRangesLeft, windowDuration);
    }

    private List<TimeRange> reduceOverlaps(List<TimeRange> busyTimeRanges) {
        int position = 0;
        while (position < busyTimeRanges.size()-1) {
            TimeRange range1 = busyTimeRanges.get(position);
            TimeRange range2 = busyTimeRanges.get(position+1);

            if (range1.getFrom() < range2.getFrom()) {
                if (range2.getTo() <= range1.getTo()){
                    busyTimeRanges.remove(range2);
                } else if (range1.getTo() >= range2.getFrom()){
                    TimeRange newRange = new TimeRange(range1.getFrom(), range2.getTo());
                    busyTimeRanges.remove(range2);
                    busyTimeRanges.remove(range1);
                    busyTimeRanges.add(position, newRange);
                } else position++;
            } else {
                if (range2.getTo() >= range1.getTo()){
                    busyTimeRanges.remove(range1);
                } else if (range2.getTo() >= range1.getFrom()){
                    TimeRange newRange = new TimeRange(range2.getFrom(), range1.getTo());
                    busyTimeRanges.remove(range1);
                    busyTimeRanges.remove(range2);
                    busyTimeRanges.add(position, newRange);
                }
                else position++;
            }
        }
        return busyTimeRanges;
    }
}
