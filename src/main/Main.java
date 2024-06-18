package main;
import graphics.Draw;
import graphics.GameFrame;
import components.enemies.EnemiesArmy;
import components.enemies.EnemyTypeOne;
import components.enemies.EnemyTypeTwo;
import components.Player;
import gameLib.Util;

import java.time.Instant;

public class Main {
    public static void busyWait(long time) {

        while (System.currentTimeMillis() < time) Thread.yield();
    }

    public static void main(String[] args) {
        var currentTime = Instant.now();
        Player player = new Player(Util.ACTIVE, Util.WIDTH/2, Util.HEIGHT*0.90,
                0.25, 0.25, 12.0, Instant.EPOCH, Instant.EPOCH, currentTime, null );
    }
}