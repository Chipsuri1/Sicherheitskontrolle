package main.baggageScanner;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Belt {
    private Queue<Tray> trays = new LinkedList<>();


    public Queue<Tray> getTrays() {
        return trays;
    }
}
