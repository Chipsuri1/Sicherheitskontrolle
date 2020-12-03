package main.employee;

import main.MagnetStripe;
import main.ProfilType;

public class HouseKeeper extends Employee {
    public HouseKeeper(int id, String name, String birthDate) {
        super(id, name, birthDate);

        getIdCard().setMagnetStripe(new MagnetStripe(ProfilType.K, getIdCard().getSecretKey()));

    }
}
