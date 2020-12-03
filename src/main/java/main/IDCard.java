package main;

import java.util.UUID;

public class IDCard {

    private UUID id;
    private String validUntil;
    private MagnetStripe magnetStripe;
    private boolean isLocked;
    private Type type;
    private final String secretKey;

    public IDCard(){
        this.id = UUID.randomUUID();
        this.validUntil = "26.07.2025";
        this.secretKey = "dhbw$20^20_";
        this.isLocked = false;
        this.magnetStripe = null;
        this.type = null;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setMagnetStripe(MagnetStripe magnetStripe) {
        this.magnetStripe = magnetStripe;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public MagnetStripe getMagnetStripe() {
        return magnetStripe;
    }
}
