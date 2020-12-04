import main.FederalPoliceOffice.ScanResult;
import main.baggageScanner.Status;
import main.baggageScanner.Tray;
import main.baggageScanner.components.BaggageScanner;
import main.configuration.Configuration;
import main.configuration.SecurityControl;
import main.employee.*;
import main.passenger.HandBaggage;
import main.passenger.Layer;
import main.passenger.Passenger;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class AllTests {

    private SecurityControl securityControl;
    private BaggageScanner baggageScanner;
    private Queue<Passenger> passengers;

    @BeforeEach
    public void setup(){
        securityControl = new SecurityControl();
        baggageScanner = securityControl.getBaggageScanner();
    }


    public void setUpPassengers() {
        passengers = new LinkedList<>();
        for (int i = 0; i < Configuration.instance.NUMBER_OF_PASSENGERS; i++) {
            String[] content = Configuration.instance.fileReader.readContent(i, Configuration.instance.DATA_FILEPATH);
            String name = content[0];
            HandBaggage[] handBaggage = Configuration.instance.dataGenerator.generateBaggage(Integer.valueOf(content[1]), content[2]);
            Passenger passenger = new Passenger(name, handBaggage);
            Arrays.stream(handBaggage).forEach(handBaggage1 -> handBaggage1.setPassenger(passenger));
            passengers.add(passenger);
        }
    }

    @Order(1)
    @Test
    public void wrapTestOne(){
        dynamicTestsExampleName();
        dynamicTestsBaggage();
    }

    @TestFactory
    public Stream<DynamicTest> dynamicTestsExampleName() {
        setUpPassengers();
        int size = passengers.size();
        List<DynamicTest> dynamicTestNameList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Passenger passengerX = passengers.poll();
            Passenger passengerY = securityControl.getPassengerList().poll();

            String nameX = passengerX.getName();
            String nameY = passengerY.getName();
            DynamicTest dynamicTestForNames = dynamicTest("dynamic test for setName(" + nameX + "," + nameY + ")", () -> {
                Assert.assertEquals(nameX, nameY);
            });

            dynamicTestNameList.add(dynamicTestForNames);
        }

        return dynamicTestNameList.stream();
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsBaggage() {
        setup();
        setUpPassengers();
        int baggageCounter = 0;
        int personCounter = 0;
        List<DynamicTest> dynamicTestHandBaggagesList = new ArrayList<>();
        for (Passenger passenger : securityControl.getPassengerList()) {
            personCounter++;
            for (HandBaggage handBaggage : passenger.getHandBaggage()) {
                baggageCounter++;
            }
        }
        for (int i = 0; i < Configuration.instance.NUMBER_OF_PASSENGERS; i++) {
            Passenger passengerX = passengers.poll();
            Passenger passengerY = securityControl.getPassengerList().poll();

            HandBaggage[] handBaggagesX = passengerX.getHandBaggage();
            HandBaggage[] handBaggagesY = passengerY.getHandBaggage();

            DynamicTest dynamicTestForHandBaggages = dynamicTest("dynamic test for HandBaggages(" + handBaggagesX + "," + handBaggagesY + ")", () -> {
                boolean foundK = true;
                boolean foundE = true;
                boolean foundW = true;
                for (int j = 0; j < handBaggagesX.length; j++) {
                    for (int k = 0; k < 5; k++) {
                        for (int l = 0; l < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; l++) {
                            if (handBaggagesX[j].getLayers()[k].getContent()[l] == 'K') {
                                foundK = false;
                                for (int m = 0; m < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; m++) {
                                    if (handBaggagesY[j].getLayers()[k].getContent()[m] == 'K') {
                                        foundK = true;
                                    }
                                }
                            } else if (handBaggagesX[j].getLayers()[k].getContent()[l] == 'E') {
                                foundE = false;
                                for (int m = 0; m < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; m++) {
                                    if (handBaggagesY[j].getLayers()[k].getContent()[m] == 'E') {
                                        foundE = true;
                                    }
                                }
                            } else if (handBaggagesX[j].getLayers()[k].getContent()[l] == 'W') {
                                foundW = false;
                                for (int m = 0; m < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; m++) {
                                    if (handBaggagesY[j].getLayers()[k].getContent()[m] == 'W') {
                                        foundW = true;
                                    }
                                }
                            }
                        }

                    }
                }
                Assert.assertTrue(foundK);
                Assert.assertTrue(foundW);
                Assert.assertTrue(foundE);
            });
            dynamicTestHandBaggagesList.add(dynamicTestForHandBaggages);
        }
        Assert.assertEquals(Configuration.instance.NUMBER_OF_PASSENGERS, personCounter);
        Assert.assertEquals(Configuration.instance.NUMBER_OF_BAGGAGE, baggageCounter);
        return dynamicTestHandBaggagesList.stream();
    }


    @Test
    @Order(2)
    public void assignStationsWithEmployees() {
        Assertions.assertTrue(baggageScanner.getRollerConveyor().getInspectorI1() instanceof Inspector);
        Assertions.assertTrue(baggageScanner.getRollerConveyor().getInspectorI1().getName().equals("Clint Eastwood"));
        Assertions.assertTrue(baggageScanner.getOperatingStation().getInspectorI2() instanceof Inspector);
        Assertions.assertTrue(baggageScanner.getOperatingStation().getInspectorI2().getName().equals("Natalie Portman"));
        Assertions.assertTrue(baggageScanner.getManualPostControl().getInspectorI3() instanceof Inspector);
        Assertions.assertTrue(baggageScanner.getManualPostControl().getInspectorI3().getName().equals("Bruce Willis"));
        Assertions.assertTrue(baggageScanner.getSupervision().getSupervisor() instanceof Supervisor);
        Assertions.assertTrue(baggageScanner.getSupervision().getSupervisor().getName().equals("Jodie Foster"));
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1() instanceof FederalPoliceOfficer);
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1().getName().equals("Wesley Snipes"));
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO2() instanceof FederalPoliceOfficer);
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO2().getName().equals("Toto"));
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO3() instanceof FederalPoliceOfficer);
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO3().getName().equals("Harry"));
        Assertions.assertTrue(baggageScanner.getTechnician() instanceof Technician);
        Assertions.assertTrue(baggageScanner.getTechnician().getName().equals("Jason Statham"));
        Assertions.assertTrue(baggageScanner.getHouseKeeper() instanceof HouseKeeper);
        Assertions.assertTrue(baggageScanner.getHouseKeeper().getName().equals("Jason Clarke"));
    }


    @Test
    @Order(3)
    public void scanInspectorCard(){
        Assertions.assertEquals(baggageScanner.getStatus(), Status.shutdown);
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        Assertions.assertEquals(baggageScanner.getStatus(), Status.locked);

        baggageScanner.setStatus(Status.shutdown);
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "5000");
        Assertions.assertEquals(baggageScanner.getStatus(), Status.locked);

        baggageScanner.getSupervision().getSupervisor().unlock(baggageScanner);
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "4000");
        baggageScanner.getOperatingStation().getCardReader().checkCard(baggageScanner.getOperatingStation().getInspectorI2().swipeCard(), "5000");
        Assertions.assertEquals(baggageScanner.getStatus(), Status.activated);
    }

    @Test
    @Order(4)
    public void checkCardOfEmployee(){
        IDCard idCardProfileTypeK = new IDCard();
        IDCard idCardProfileTypeO = new IDCard();
        IDCard idCardProfileTypeI = new IDCard();
        idCardProfileTypeK.setMagnetStripe(new MagnetStripe(ProfilType.K, "dhbw$20^20_"));
        idCardProfileTypeO.setMagnetStripe(new MagnetStripe(ProfilType.O, "dhbw$20^20_"));
        idCardProfileTypeI.setMagnetStripe(new MagnetStripe(ProfilType.I, "dhbw$20^20_"));

        Assertions.assertFalse(baggageScanner.getOperatingStation().getCardReader().scan(idCardProfileTypeK, "5000"));
        Assertions.assertFalse(baggageScanner.getOperatingStation().getCardReader().scan(idCardProfileTypeO, "5000"));
        Assertions.assertTrue(baggageScanner.getOperatingStation().getCardReader().scan(idCardProfileTypeI, "5000"));
    }

    @Test
    @Order(5)
    public void employeesHaveCorrectUsageRights(){
        Inspector inspectorI1 = new Inspector(1, "Clint Eastwood", "31.05.1930", true);
        FederalPoliceOfficer federalPoliceOfficerO1 = new FederalPoliceOfficer(5, "Wesley Snipes", "31.07.1962", null);
        Technician technician = new Technician(6, "Jason Statham",  "26.07.1967", baggageScanner);
        HouseKeeper houseKeeper = new HouseKeeper(7, "Jason Clarke", "17.07.1969");
        Supervisor supervisor = new Supervisor(4, "Jodie Foster", "19.11.1962", false, false);

        Assertions.assertTrue(baggageScanner.maintenance(technician));
        Assertions.assertFalse(baggageScanner.maintenance(inspectorI1));
        Assertions.assertFalse(baggageScanner.maintenance(federalPoliceOfficerO1));
        Assertions.assertFalse(baggageScanner.maintenance(houseKeeper));
        Assertions.assertFalse(baggageScanner.maintenance(supervisor));

        baggageScanner.setStatus(Status.locked);
        Assertions.assertTrue(baggageScanner.unlock(supervisor));
        Assertions.assertFalse(baggageScanner.unlock(inspectorI1));
        Assertions.assertFalse(baggageScanner.unlock(technician));
        Assertions.assertFalse(baggageScanner.unlock(federalPoliceOfficerO1));
        Assertions.assertFalse(baggageScanner.unlock(houseKeeper));

        Assertions.assertTrue(baggageScanner.getOperatingStation().getInspectorI2().push(baggageScanner.getOperatingStation().getButtonRight()));
        Assertions.assertTrue(baggageScanner.getOperatingStation().getInspectorI2().push(baggageScanner.getOperatingStation().getButtonLeft()));
        Assertions.assertTrue(baggageScanner.getOperatingStation().getInspectorI2().push(baggageScanner.getOperatingStation().getButtonRectangle()));
        Assertions.assertFalse(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO2().push(baggageScanner.getOperatingStation().getButtonRectangle()));
        Assertions.assertFalse(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO2().push(baggageScanner.getOperatingStation().getButtonRight()));
        Assertions.assertFalse(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO2().push(baggageScanner.getOperatingStation().getButtonLeft()));

        baggageScanner.getManualPostControl().setTray(new Tray(new HandBaggage()));
        Assertions.assertTrue(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO2().push(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1().getRemoteControl()));
        Assertions.assertFalse(baggageScanner.getOperatingStation().getInspectorI2().push(baggageScanner.getFederalPoliceOffice().getFederalPoliceOfficerO1().getRemoteControl()));
    }

    @Test
    @Order(6)
    public void onlySupervisorCanUnlockBaggageScanner(){
        baggageScanner.setStatus(Status.locked);
        baggageScanner.getSupervision().getSupervisor().unlock(baggageScanner);
        Assertions.assertEquals(baggageScanner.getStatus(), Status.activated);
    }

    @Test
    @Order(7)
    public void checkIfHandBaggageContainsWeapon(){
        int positionOfWeapon = 100;

        HandBaggage handBaggage = new HandBaggage();
        handBaggage.getLayers()[0].getContent()[positionOfWeapon] = 'W';
        baggageScanner.getScanner().getTrays().add(new Tray(handBaggage));
        baggageScanner.getScanner().startScanning();
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().size(), 1);
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().peek().getResult().getScanResult(), ScanResult.weapon);
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().peek().getResult().getPosition(), "prohibited item | weapon - glock7 detected at position 100");
    }

    @Test
    @Order(8)
    public void checkIfHandBaggageContainsKnife(){
        int positionOfWeapon = 100;

        HandBaggage handBaggage = new HandBaggage();
        handBaggage.getLayers()[0].getContent()[positionOfWeapon] = 'K';
        baggageScanner.getScanner().getTrays().add(new Tray(handBaggage));
        baggageScanner.getScanner().startScanning();
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().size(), 1);
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().peek().getResult().getScanResult(), ScanResult.knife);
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().peek().getResult().getPosition(), "prohibited item | knife detected at position 100");

    }

    @Test
    @Order(9)
    public void checkIfHandBaggageContainsExplosive(){
        int positionOfWeapon = 100;

        HandBaggage handBaggage1 = new HandBaggage();
        handBaggage1.getLayers()[0].getContent()[positionOfWeapon] = 'E';
        baggageScanner.getScanner().getTrays().add(new Tray(handBaggage1));
        baggageScanner.getScanner().startScanning();
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().size(), 1);
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().peek().getResult().getScanResult(), ScanResult.explosive);
        Assertions.assertEquals(baggageScanner.getScanner().getRecords().peek().getResult().getPosition(), "prohibited item | explosive detected at position 100");
    }

    @Order(10)
    @TestFactory
    public Stream<DynamicTest> dynamicTestsRecord() {
        //ich teste jetzt hier obs 609 (das geht grad nicht, kp warum)records gibt als Zahl und prüfe nochmal jeden ab, ob zu jedem baggage/tray ein Record existiert
        securityControl.checkPassengers();
        List<HandBaggage> handBaggages = securityControl.getHandBaggage();
        int counter = 0;
        for (HandBaggage handBaggage : handBaggages) {
            if (handBaggage.getTray().getRecord() != null) {
                counter++;
            }
        }
        Assert.assertEquals(Configuration.instance.NUMBER_OF_BAGGAGE, counter);
        List<DynamicTest> dynamicTestRecordList = new ArrayList<>();
        for (HandBaggage handBaggage : handBaggages) {
            DynamicTest dynamicTestForRecords = dynamicTest("dynamic test for Records(" + handBaggage.getTray().getRecord() + ")", () -> {
                Assert.assertNotNull(handBaggage.getTray().getRecord());
            });
            dynamicTestRecordList.add(dynamicTestForRecords);
        }
        return dynamicTestRecordList.stream();
    }

    @Test
    @Order(11)
    public void checkNoIllegalItem() {
        securityControl.checkPassengers();
        Assert.assertEquals(Configuration.instance.NUMBER_OF_BAGGAGE-4, securityControl.getBaggageScanner().getTrack2().getTrays().size());
        for (Tray tray : securityControl.getBaggageScanner().getTrack2().getTrays()) {
            boolean foundIllegal = false;
            for (Layer layer : tray.getHandBaggage().getLayers()) {
                for (int i = 0; i < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; i++) {
                    if (layer.getContent()[i] == 'K' || layer.getContent()[i] == 'W' || layer.getContent()[i] == 'E') {
                        foundIllegal = true;
                    }
                }
                Assert.assertFalse(foundIllegal);
            }
        }
    }

    @Test
    @Order(12)
    public void checkKnife() {
        int knifeCtr = 0;
        for (HandBaggage handBaggage : securityControl.getHandBaggage()) {
            for (Layer layer : handBaggage.getLayers()) {
                for (int i = 0; i < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; i++) {
                    if (layer.getContent()[i] == 'K') {
                        knifeCtr++;
                    }
                }
            }
        }
        Assert.assertEquals(4, knifeCtr);
        securityControl.checkPassengers();
        knifeCtr = 0;
        for (HandBaggage handBaggage : securityControl.getHandBaggage()) {
            for (Layer layer : handBaggage.getLayers()) {
                for (int i = 0; i < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; i++) {
                    if (layer.getContent()[i] == 'K') {
                        knifeCtr++;
                    }
                }
            }
        }
        Assert.assertEquals(0, knifeCtr);

        Assert.assertEquals(Configuration.instance.NUMBER_OF_BAGGAGE-4, securityControl.getBaggageScanner().getTrack2().getTrays().size());
        for (Tray tray : securityControl.getBaggageScanner().getTrack2().getTrays()) {
            boolean foundIllegal = false;
            for (Layer layer : tray.getHandBaggage().getLayers()) {
                for (int i = 0; i < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; i++) {
                    if (layer.getContent()[i] == 'K' || layer.getContent()[i] == 'W' || layer.getContent()[i] == 'E') {
                        foundIllegal = true;
                    }
                }
            }
            Assert.assertFalse(foundIllegal);
        }
    }

    @Test
    @Order(13)
    public void checkWeapon() {
        int weaponCtr = 0;
        for (HandBaggage handBaggage : securityControl.getHandBaggage()) {
            for (Layer layer : handBaggage.getLayers()) {
                for (int i = 0; i < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; i++) {
                    if (layer.getContent()[i] == 'W') {
                        weaponCtr++;
                    }
                }
            }
        }
        Assert.assertEquals(3, weaponCtr);
        securityControl.checkPassengers();
        weaponCtr = 0;
        for (HandBaggage handBaggage : securityControl.getHandBaggage()) {
            for (Layer layer : handBaggage.getLayers()) {
                for (int i = 0; i < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; i++) {
                    if (layer.getContent()[i] == 'W') {
                        weaponCtr++;
                    }
                }
            }
        }
        Assert.assertEquals(0, weaponCtr);

        Assert.assertEquals(4, securityControl.getBaggageScanner().getFederalPoliceOffice().getFederalPoliceOfficerO3().getBaggagesOfArrested().size());

        Assert.assertEquals(Configuration.instance.NUMBER_OF_BAGGAGE-4, securityControl.getBaggageScanner().getTrack2().getTrays().size());

    }


    @Test
    @Order(14)
    public void checkExplosive() {
        int explosiveCtr = 0;
        for (HandBaggage handBaggage : securityControl.getHandBaggage()) {
            for (Layer layer : handBaggage.getLayers()) {
                for (int i = 0; i < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; i++) {
                    if (layer.getContent()[i] == 'E') {
                        explosiveCtr++;
                    }
                }
            }
        }
        Assert.assertEquals(2, explosiveCtr);
        securityControl.checkPassengers();
        explosiveCtr = 0;
        for (HandBaggage handBaggage : securityControl.getHandBaggage()) {
            for (Layer layer : handBaggage.getLayers()) {
                for (int i = 0; i < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; i++) {
                    if (layer.getContent()[i] == 'E') {
                        explosiveCtr++;
                    }
                }
            }
        }
        Assert.assertEquals(0, explosiveCtr);

        int destroyedBaggagesCtr = 0;
        for (HandBaggage handBaggage : securityControl.getHandBaggage()) {
            if (String.valueOf(handBaggage.getContent()[0][0]).matches("\\w")) {
                destroyedBaggagesCtr++;
            }
        }
        Assert.assertEquals(2, destroyedBaggagesCtr);

    }

}
