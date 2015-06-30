package com.tlennon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.Iterator;
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
            List<File> filesInFolder = Files.walk(Paths.get(dirName))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            Iterator<File> iterator = filesInFolder.iterator();
            while(iterator.hasNext()){
                Path path = iterator.next().toPath();
                try {
                    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                    Date datePlusDifference = new Date(attr.creationTime().toMillis() + (daysToRemoval * 24 * 60 * 1000));
                    Date todaysDate = new Date();

                    if(datePlusDifference.before(todaysDate)) {
                        System.out.println("Deleting file " + path);
                        Files.delete(path);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to access + " + path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
