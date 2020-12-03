import main.configuration.Configuration;
import main.configuration.SecurityControl;
import main.passenger.HandBaggage;
import main.passenger.Passenger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class One {

    private SecurityControl securityControl;
    private Queue<Passenger> passengers;
    @BeforeEach
    public void setUp() {
        securityControl = new SecurityControl();
    }

    public void setUpPassengers(){
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

    @TestFactory
    Stream<DynamicTest> dynamicTestsExample() {
        setUpPassengers();
        int size = passengers.size();
        List<DynamicTest> dynamicTestHandBaggagesList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Passenger passengerX = passengers.poll();
            Passenger passengerY = securityControl.getPassengerList().poll();

            HandBaggage[] handBaggagesX = passengerX.getHandBaggage();
            HandBaggage[] handBaggagesY = passengerY.getHandBaggage();

            DynamicTest dynamicTestForHandBaggages = dynamicTest("dynamic test for HandBaggages(" + handBaggagesX + "," + handBaggagesY + ")", () -> {
                boolean found = true;
                for (int j = 0; j < handBaggagesX.length; j++) {

                    for (int k = 0; k < 5; k++) {
                        for (int l = 0; l < 10000; l++) {
                            if(handBaggagesX[j].getLayers()[k].getContent()[l] == 'K'){
                                found = false;
                                for (int m = 0; m < 10000; m++) {
                                    if(handBaggagesY[j].getLayers()[k].getContent()[m] == 'K'){
                                        found = true;
                                    }
                                }
                            }
                        }

                    }
                }
                Assert.assertTrue(found);
            });


            dynamicTestHandBaggagesList.add(dynamicTestForHandBaggages);
        }

        return dynamicTestHandBaggagesList.stream();
    }



}



