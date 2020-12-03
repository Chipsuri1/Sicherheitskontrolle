package main.passenger;

import main.configuration.Configuration;


public class Layer {

    private char[] content = new char[Configuration.instance.NUMBER_OF_CONTENT_PER_LAYER];

    public Layer(){
        fillLayers();
    }

    private void fillLayers(){
        final String string = "abcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i < content.length; i++){
            int randomChar = Configuration.instance.mersenneTwister.nextInt(string.length());
            content[i] = string.charAt(randomChar);
        }
    }

    public char[] getContent() {
        return content;
    }

}
