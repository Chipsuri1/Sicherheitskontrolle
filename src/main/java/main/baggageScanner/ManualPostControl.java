package main.baggageScanner;

import main.ExplosiveTraceDetector;
import main.employee.Inspector;

public class ManualPostControl {

    private Inspector inspectorI3 = new Inspector(3, "Bruce Willis", "19.03.1955", true);
    private ExplosiveTraceDetector explosiveTraceDetector = new ExplosiveTraceDetector();


    public Inspector getInspectorI3() {
        return inspectorI3;
    }

    public ExplosiveTraceDetector getExplosiveTraceDetector() {
        return explosiveTraceDetector;
    }
}
