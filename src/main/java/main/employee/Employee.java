package main.employee;

import main.IDCard;
import main.button.Button;

public abstract class Employee {

    protected IDCard idCard;
    protected int id;
    protected String name;
    protected String birthDate;


    public Employee(int id, String name, String birthDate){
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;


        idCard = new IDCard();
    }


    public boolean push(Button button){
        return button.buttonAction(this);
    }

    public IDCard getIdCard() {
        return idCard;
    }

    public int getId() {
        return id;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }
}
