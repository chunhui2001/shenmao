package com.supercard.lab.thread.download;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

public class Downloader extends Thread {

    private InputStream in;
    private OutputStream out;

    private ArrayList<ProgressListener> listeners;

    public Downloader(URL url, String outputFilename) throws IOException {

        in = url.openConnection().getInputStream();
        out = new FileOutputStream(outputFilename);
        listeners = new ArrayList<>();

    }

    public synchronized void addListener(ProgressListener listener) {
        listeners.add(listener);
    }

    /* public synchronized void updateProgress(int n) {

         for (ProgressListener listener: listeners) {
            // 此处调用了外部方法，外部方法可能持有另外一把锁，
            // 导致对加锁顺序一无所知的情况下使用了俩把锁, 此时有可能发生死锁
            // 解决思路是:
            // [****] 避免持有锁时调用外部方法，
            // [****] 或在遍历之前对 linteners 进行保护性复制, 然后在针对副本进行遍历
            listener.onProgress(n);
        }

    } */

    public void updateProgress(int n) {

        ArrayList<ProgressListener> listenersCopy = null;

        synchronized (this) {
            // 保护性复制，避免调用外部方法时持有另外一把锁
            // 考虑将 listeners 定义成 CopyOnArrayList<ProgressListener>
            listenersCopy = (ArrayList<ProgressListener>) listeners.clone();
        }

        for (ProgressListener listener: listenersCopy) {
            listener.onProgress(n);
        }

    }

    public void run() {

        int n=0, total = 0;

        byte[] buffer = new byte[1024];

        try {

            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0 , n);
                total += n;
                updateProgress(total);
            }

        } catch (IOException e) {}

    }

}
