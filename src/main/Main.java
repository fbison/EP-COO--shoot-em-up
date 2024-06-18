package main;
import graphics.Draw;


public class Main {
    public static void busyWait(long time) {

        while (System.currentTimeMillis() < time) Thread.yield();
    }

    public static void main(String[] args) {

    }
}