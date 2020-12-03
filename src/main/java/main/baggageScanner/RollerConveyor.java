package main.baggageScanner;

import main.employee.Inspector;

import java.util.LinkedList;
import java.util.Queue;

public class RollerConveyor {

    private Inspector inspectorI1 = new Inspector(1, "Clint Eastwood", "31.05.1930", true);
    private Queue<Tray> trays = new LinkedList<>();


    public Queue<Tray> getTrays() {
        return trays;
    }

    public Inspector getInspectorI1() {
        return inspectorI1;
    }
}
