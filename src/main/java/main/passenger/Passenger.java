package main.passenger;

public class Passenger {

    private String name;
    private HandBaggage[] handBaggage;
    private boolean arrested;

    public Passenger(String name, HandBaggage[] handBaggage){
        this.handBaggage = handBaggage;
        this.name = name;
        arrested = false;
    }

    public HandBaggage[] getHandBaggage() {
        return handBaggage;
    }

    public void setArrested(boolean arrested) {
        this.arrested = arrested;
    }

    public String getName() {
        return name;
    }
}
