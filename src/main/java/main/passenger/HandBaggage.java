package main.passenger;

import main.baggageScanner.Tray;

public class HandBaggage {

    private Passenger passenger = null;
    private Tray tray;
    private char[][] content;
    private Layer[] layers = new Layer[5];

    public HandBaggage(){
        for(int i = 0; i < layers.length; i++){
            layers[i] = new Layer();
        }
        content = new char[1000][50];
    }

    public Layer[] getLayers() {
        return layers;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void setTray(Tray tray) {
        this.tray = tray;
    }

    public Tray getTray() {
        return tray;
    }

    public char[][] getContent() {
        return content;
    }
}
