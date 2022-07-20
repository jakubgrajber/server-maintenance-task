package com.capgemini;

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
        findFirsValidIdleWindow(new TimeRange(BEGINNING_OF_THE_DAY, END_OF_THE_DAY), busyTimeRanges, windowDuration);
        return start;
    }

    private static void findFirsValidIdleWindow(TimeRange timeRangeLeft, List<TimeRange> busyTimeRanges, int windowDuration){
        if (busyTimeRanges.isEmpty()){
            if (timeRangeLeft.getDuration() >=windowDuration)
                start = timeRangeLeft.getFrom();
            return;
        }

        TimeRange currentBusyTimeRange = busyTimeRanges.get(0);
        List<TimeRange> busyTimeRangesLeft = busyTimeRanges.subList(1, busyTimeRanges.size());

        TimeRange newTimeRangeLeft1 = new TimeRange(timeRangeLeft.getFrom(), currentBusyTimeRange.getFrom());
        if (newTimeRangeLeft1.getDuration() >= windowDuration){
            start = newTimeRangeLeft1.getFrom();
            return;
        }

        findFirsValidIdleWindow(new TimeRange(currentBusyTimeRange.getTo(), timeRangeLeft.getTo()), busyTimeRangesLeft,windowDuration);
    }
}
