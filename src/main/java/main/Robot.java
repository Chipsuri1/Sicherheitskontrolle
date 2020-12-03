package main;

import main.configuration.Configuration;
import main.passenger.HandBaggage;
import main.passenger.Layer;

public class Robot {
    public void defuse(HandBaggage handBaggage) {
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
