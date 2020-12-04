import main.Record;
import main.Status;
import main.baggageScanner.Tray;
import main.configuration.Configuration;
import main.configuration.SecurityControl;
import main.passenger.HandBaggage;
import main.passenger.Layer;
import main.passenger.Passenger;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class Kek {

    private SecurityControl securityControl;
    private Queue<Passenger> passengers;

    @BeforeEach
    public void setUp() {
        securityControl = new SecurityControl();
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

    @Order(1)
    @TestFactory
    Stream<DynamicTest> dynamicTestsBaggage() {
        setUpPassengers();
        int size = passengers.size();
        int baggageCounter = 0;
        int personCounter = 0;
        List<DynamicTest> dynamicTestHandBaggagesList = new ArrayList<>();
        for (Passenger passenger : securityControl.getPassengerList()) {
            personCounter++;
            for (HandBaggage handBaggage : passenger.getHandBaggage()) {
                baggageCounter++;
            }
        }
        for (int i = 0; i < size; i++) {
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
                        for (int l = 0; l < 10000; l++) {
                            if (handBaggagesX[j].getLayers()[k].getContent()[l] == 'K') {
                                foundK = false;
                                for (int m = 0; m < 10000; m++) {
                                    if (handBaggagesY[j].getLayers()[k].getContent()[m] == 'K') {
                                        foundK = true;
                                    }
                                }
                            } else if (handBaggagesX[j].getLayers()[k].getContent()[l] == 'E') {
                                foundE = false;
                                for (int m = 0; m < 10000; m++) {
                                    if (handBaggagesY[j].getLayers()[k].getContent()[m] == 'E') {
                                        foundE = true;
                                    }
                                }
                            } else if (handBaggagesX[j].getLayers()[k].getContent()[l] == 'W') {
                                foundW = false;
                                for (int m = 0; m < 10000; m++) {
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
        Assert.assertEquals(568, personCounter);
        Assert.assertEquals(609, baggageCounter);
        return dynamicTestHandBaggagesList.stream();
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
        Assert.assertEquals(609, counter);
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
        Assert.assertEquals(605, securityControl.getBaggageScanner().getTrack2().getTrays().size());
        for (Tray tray : securityControl.getBaggageScanner().getTrack2().getTrays()) {
            boolean foundIllegal = false;
            for (Layer layer : tray.getHandBaggage().getLayers()) {
                for (int i = 0; i < 10000; i++) {
                    if (layer.getContent()[i] == 'K' || layer.getContent()[i] == 'W' || layer.getContent()[i] == 'E') {
                        foundIllegal = true;
                    }
                }
            }
            Assert.assertFalse(foundIllegal);
        }
    }

    @Test
    @Order(12)
    public void checkKnife() {
        int knifeCtr = 0;
        for (HandBaggage handBaggage : securityControl.getHandBaggage()) {
            for (Layer layer : handBaggage.getLayers()) {
                for (int i = 0; i < 10000; i++) {
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
                for (int i = 0; i < 10000; i++) {
                    if (layer.getContent()[i] == 'K') {
                        knifeCtr++;
                    }
                }
            }
        }
        Assert.assertEquals(0, knifeCtr);

        Assert.assertEquals(605, securityControl.getBaggageScanner().getTrack2().getTrays().size());
        for (Tray tray : securityControl.getBaggageScanner().getTrack2().getTrays()) {
            boolean foundIllegal = false;
            for (Layer layer : tray.getHandBaggage().getLayers()) {
                for (int i = 0; i < 10000; i++) {
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
        setUpPassengers();
        securityControl = new SecurityControl();
        int weaponCtr = 0;
        for (HandBaggage handBaggage : securityControl.getHandBaggage()) {
            for (Layer layer : handBaggage.getLayers()) {
                for (int i = 0; i < 10000; i++) {
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
                for (int i = 0; i < 10000; i++) {
                    if (layer.getContent()[i] == 'W') {
                        weaponCtr++;
                    }
                }
            }
        }
        Assert.assertEquals(0, weaponCtr);

        Assert.assertEquals(4, securityControl.getBaggageScanner().getFederalPoliceOffice().getFederalPoliceOfficerO3().getBaggagesOfArrested().size());

        Assert.assertEquals(605, securityControl.getBaggageScanner().getTrack2().getTrays().size());

    }


    @Test
    @Order(14)
    public void checkExplosive() {


    }
}