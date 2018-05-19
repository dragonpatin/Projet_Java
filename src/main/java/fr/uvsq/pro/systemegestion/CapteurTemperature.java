package fr.uvsq.pro.systemegestion;
public class CapteurTemperature extends Objet{

    protected int temperature;

    public CapteurTemperature(int temperature){
        this.temperature = temperature;
    }

    public int getTemperature(){
        return this.temperature;
    }
}

