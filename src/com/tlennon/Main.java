package com.tlennon;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Configuration conf = readConfiguration();
        System.out.println(conf.getTimeToLive());
        //Create a thread.
        for(int i = 0; i < conf.getDirs().length; i++) {
            dirCleanerThread thread = new dirCleanerThread(conf.getDirs()[i], conf.getTimeToLive());
            thread.start();
        }
    }

    private static Configuration readConfiguration() {
        BufferedReader reader = null;
        Configuration config = null;
        try {
            File file = new File("./config.json");
            reader = new BufferedReader(new FileReader(file));

            String line;
            String configFile = "";
            while((line = reader.readLine()) != null ) {
                configFile += line;
            }

            Gson gson = new Gson();
            config = gson.fromJson(configFile, Configuration.class);

        }catch (IOException e) {
            e.printStackTrace();

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } finally {

            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return config;
        }
    }
}
