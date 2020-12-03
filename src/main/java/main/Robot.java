package main;

import main.configuration.Configuration;
import main.passenger.HandBaggage;
import main.passenger.Layer;

public class Robot {

    private FederalPoliceOffice federalPoliceOffice;

    public Robot(FederalPoliceOffice federalPoliceOffice){
        this.federalPoliceOffice = federalPoliceOffice;
    }



    public void defuse() {
        HandBaggage handBaggage = federalPoliceOffice.getBaggageScanner().getManualPostControl().getTray().getHandBaggage();


        StringBuilder stringBuilder = new StringBuilder();

        for(Layer layer : handBaggage.getLayers()){
            for(int j = 0; j < Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER; j++){
                stringBuilder.append(layer.getContent()[j]);
            }
        }

        int number = 0;
        for(int i = 0; i < handBaggage.getLayers().length; i++){
            for(int j = 0; j < handBaggage.getLayers()[i].getContent().length; j++){
                handBaggage.getLayers()[i].getContent()[j] = stringBuilder.toString().charAt(number);
                number++;
            }
        }


    }
}
