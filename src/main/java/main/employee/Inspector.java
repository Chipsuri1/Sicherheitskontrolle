package main.employee;

import main.Record;
import main.*;
import main.baggageScanner.BaggageScanner;
import main.baggageScanner.Tray;
import main.button.Button;
import main.passenger.HandBaggage;
import main.passenger.Layer;
import main.passenger.Passenger;

import java.util.Queue;

import static main.Type.staff;

public class Inspector extends Employee {

    private boolean isSenior;

    private Record record;
    private FederalPoliceOfficer officer1;
    private FederalPoliceOfficer officer2;
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

    public void push(Button button){
        button.buttonAction();
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

    public FederalPoliceOfficer getOfficer1() {
        return officer1;
    }

    public FederalPoliceOfficer getOfficer2() {
        return officer2;
    }

    public void setOfficer1(FederalPoliceOfficer officer1) {
        this.officer1 = officer1;
    }

    public void setOfficer2(FederalPoliceOfficer officer2) {
        this.officer2 = officer2;
    }


    public void tellOtherInspector(Inspector inspector, Record record){
        inspector.hearSentence(record);
    }

    public void hearSentence(Record record){
        this.record = record;
    }
}
