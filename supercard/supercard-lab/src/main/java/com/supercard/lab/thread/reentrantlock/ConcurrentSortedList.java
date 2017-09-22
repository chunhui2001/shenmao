package com.supercard.lab.thread.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/*
    交替锁定链表中的两个相邻节点，允许不涉及被锁部分的其它线程自由访问连标, 从而避免锁定整个链表
 */

public class ConcurrentSortedList {

    private class Node {

        int value;
        Node prev;
        Node next;
        boolean isroot = false;

        ReentrantLock rlock = new ReentrantLock();

        Node() {}

        Node(int value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

    }

    private final Node head;
    private final Node tail;

    public ConcurrentSortedList() {
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.prev = head;
        head.isroot = true;
    }

    public void insert(int value) {

        Node current = head;
        current.rlock.lock();

        Node next = current.next;

        try {

            while (true) {

                next.rlock.lock();

                try {

                    if (next == tail || next.value < value) {
                        Node newNode = new Node(value, current, next);
                        next.prev = newNode;
                        current.next = newNode;
                        return;
                    }

                } finally {
                    current.rlock.unlock();
                }

                current = next;
                next = current.next;

            }

        } finally {
            next.rlock.unlock();
        }

    }

    public void print(Node node) {

//        while (node.next != null) {
//            System.out.println(node.next.value + " " + (node == tail));
//            if (node == tail) return;
//            print(node.next);
//
//        }

        if (node.next != tail) {
            System.out.println(node.next.value + " " + (node.next != tail));
            print(node.next);
        }
    }

    public static void main(String[] args) {

        ConcurrentSortedList concurrentSortedList = new ConcurrentSortedList();

        System.out.println(concurrentSortedList.head.next == concurrentSortedList.tail);

        concurrentSortedList.insert(6);
        concurrentSortedList.insert(3);
        concurrentSortedList.insert(5);
        concurrentSortedList.insert(9);
        concurrentSortedList.insert(8);
        concurrentSortedList.insert(99);
        concurrentSortedList.insert(17);
        concurrentSortedList.insert(9879);

        concurrentSortedList.print(concurrentSortedList.head);

    }

}
