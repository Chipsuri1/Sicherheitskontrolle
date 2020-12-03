package main.configuration;

import main.passenger.HandBaggage;


public class DataGenerator {

    public HandBaggage[] generateBaggage(int numberOfBaggage, String content){

        HandBaggage[] handBaggage = new HandBaggage[numberOfBaggage];

        for(int i = 0; i < numberOfBaggage; i++){
            handBaggage[i] = new HandBaggage();
        }

        if(!content.equals("-")){
            content = content.substring(1, content.length()-1);
            String[] parts = content.split(";");

            for(int i = 0; i < parts.length; i++){
                String[] strings = parts[i].split(",");

                int randomPosition = Configuration.instance.mersenneTwister.nextInt(Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER);

                char prohibitedItem = strings[0].charAt(0);

                handBaggage[Integer.valueOf(strings[1])-1].getLayers()[2].getContent()[randomPosition] = prohibitedItem;
            }
        }

        return handBaggage;
    }
}
