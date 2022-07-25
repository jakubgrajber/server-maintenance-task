package com.capgemini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ServerMaintenanceManagerApp {
    public static void main(String[] args) {

        Path path = Path.of("data/time-ranges.txt");
        List<String> timeRangesStrings = new ArrayList<>();

        try (Stream<String> lines = Files.lines(path)) {
            timeRangesStrings = lines.toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String file = "data/duration.txt";
        short duration = 0;

        try (Scanner scanner = new Scanner(new File(file))) {
            if (scanner.hasNextInt())
                duration = scanner.nextShort();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<TimeRange> timeRanges = timeRangesStrings.stream().map(TimeRange::parseTimeRange).collect(Collectors.toCollection(ArrayList::new));

        timeRanges.forEach(System.out::println);

        System.out.println("Duration: " + duration);

        IdleTimeServerWindowFinder finder = new IdleTimeServerWindowFinder();

        int result = finder.getMaintenanceWindowStart(timeRanges, duration);

        if (result<0){
            System.out.println("There is no spare time for maintenance");
        } else
            System.out.println("You can start after " + result + " minutes from midnight");
    }
}
