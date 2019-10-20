package ru.javalab.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

public class MyThread extends Thread {
    public String string;

    public MyThread(String string) {
        this.string = string;
    }

    @Override
    public void run() {
        String extension = string.substring(string.lastIndexOf('/')+1);
        URL url = null;
        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            boolean xst = new File(extension).exists();
            int counter = 0;
            while (xst) { // фикс одинаковых файлов
                xst = new File(extension + "_" + counter).exists();
                counter++;
            }
            Files.copy(inputStream, new File(extension + "_" +counter).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

