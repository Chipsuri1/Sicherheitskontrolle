package main.employee;

import main.IDCard;

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

    public IDCard getIdCard() {
        return idCard;
    }

}
