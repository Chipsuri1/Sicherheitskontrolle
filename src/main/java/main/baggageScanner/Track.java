package main.baggageScanner;

import java.util.LinkedList;
import java.util.Queue;

public class Track {

    private Queue<Tray> trays = new LinkedList<>();


    public Queue<Tray> getTrays() {
        return trays;
    }

    public void putTray(Tray tray) {
        trays.offer(tray);
    }
}
