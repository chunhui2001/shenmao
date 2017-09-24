package com.supercard.lab.thread.semaphore;

import java.util.concurrent.Semaphore;

public class Connection {

    private  static Connection instance = new Connection();
    private int connections = 0;

    private Semaphore sem = new Semaphore(7);

    private Connection() {

    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {

        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sem.release();
        }

    }

    public void doConnect() throws InterruptedException {

        synchronized (this) {
            connections++;
            System.out.println("Current connections: " + connections);
        }

        Thread.sleep(2000);

        synchronized (this) {
            connections--;
        }

    }

}
