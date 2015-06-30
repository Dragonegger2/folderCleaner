package com.tlennon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tlennon on 6/30/2015.
 */
public class dirCleanerThread implements Runnable {
    private Thread t;
    private String dirName;
    private int daysToRemoval;

    public dirCleanerThread(String dir, int daysToRemoval) {
        dirName = dir;
        this.daysToRemoval = daysToRemoval;
        System.out.println("Creating dir cleaner thread for dir: "  + dirName);
    }

    @Override
    public void run() {
        try {
            Files.walk(Paths.get(dirName))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList())
                    .forEach(path -> {
                        try {
                            checkFileAge(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                            //Add logging failure here.
                            System.out.println("Failed to access " + path);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFileAge(Path file) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

        //Subtracting sixtyDays
        Date datePlusDifference = new Date(attr.creationTime().toMillis() + (daysToRemoval * 24 * 60 * 1000));
        Date todaysDate = new Date();

        if(datePlusDifference.before(todaysDate)) {
            System.out.println("Deleting file " + file);
        }
    }
    public void start () {
        System.out.println("Starting " +  dirName);
        if (t == null)
        {
            t = new Thread (this, dirName);
            t.start ();
        }
    }
}
