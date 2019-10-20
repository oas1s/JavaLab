package ru.javalab.downloader;

import ru.javalab.app.MyThread;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] UrlsArr = args;
        MyThread[] threads = new MyThread[UrlsArr.length];
        for (int i = 0; i <UrlsArr.length; i++) {
            threads[i] = new MyThread(UrlsArr[i]);
            threads[i].start();
        }
        for (int i = 0; i <UrlsArr.length ; i++) {
            threads[i].join();
        }
    }
}
