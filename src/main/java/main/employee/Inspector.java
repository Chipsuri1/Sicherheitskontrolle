package main.employee;

import main.FederalPoliceOffice.ExplosiveTraceDetector;
import main.FederalPoliceOffice.TestStripe;
import main.baggageScanner.Record;
import main.baggageScanner.components.BaggageScanner;
import main.baggageScanner.Status;
import main.baggageScanner.Tray;
import main.passenger.HandBaggage;
import main.passenger.Layer;

import java.util.Queue;

import static main.employee.Type.staff;

public class Inspector extends Employee {

    private boolean isSenior;

    private Record record;

    public Inspector(int id, String name, String birthDate, boolean isSenior) {
        super(id, name, birthDate);

        this.isSenior = isSenior;

        getIdCard().setType(staff);
        getIdCard().setMagnetStripe(new MagnetStripe(ProfilType.I, getIdCard().getSecretKey()));
    }

    public IDCard swipeCard(){
        return getIdCard();
    }

    public void pushHandBaggage(Queue<Tray> rollerConveyor , Queue<Tray> belt){
        int sizeOfRollerConveyor = rollerConveyor.size();
        for(int i = 0; i < sizeOfRollerConveyor; i++){
            Tray tray = rollerConveyor.poll();
            belt.offer(tray);
        }
    }

    public void swipeTestStripe(TestStripe testStripe){
        testStripe.setExp();
    }

    public void putTestStripeIntoExplosiveTraceDetector(ExplosiveTraceDetector explosiveTraceDetector, TestStripe testStripe){
        explosiveTraceDetector.checkTestStripeForExplosive(testStripe);
    }

    public void setAlarm(BaggageScanner baggageScanner) {
        baggageScanner.setStatus(Status.locked);
    }


    public void openBaggageGetKnifeAndThrowAway(HandBaggage handBaggage) {
            for (Layer layer : handBaggage.getLayers()
                 ) {
                for (int i = 0; i < layer.getContent().length; i++) {
                    if(layer.getContent()[i] == 'K'){
                        // if there is the knife, "throw" it away and put an 'a' in
                        layer.getContent()[i] = 'a';
                    }
                }
        }
    }

    public void putTrayToBelt(Tray tray, BaggageScanner baggageScanner) {
        baggageScanner.getBelt().getTrays().offer(tray);
    }

    public void putOnTrack1(BaggageScanner baggageScanner, Tray tray) {
        baggageScanner.getTrack1().putTray(tray);
    }

    public void tellOtherInspector(Inspector inspector, Record record){
        inspector.hearSentence(record);
    }

    public void hearSentence(Record record){
        this.record = record;
    }
}
