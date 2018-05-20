package fr.uvsq.pro.systemegestion;

/**
 * Classe CapteurTemperature
 * Cette classe est une sous-classe de la classe Objet, elle reprend les memes attributs et méthodes en y ajoutant des spécificités propres a cette classe
 */

public class CapteurTemperature extends Objet{

    /**
     * @param temperature est l'attribut qui va gérer la temperature
     */
    protected int temperature;

    /**
     * Constructeur de la classe
     * @param temperature une température pour le capteur
     */
    public CapteurTemperature(int temperature){
        this.temperature = temperature;
    }

    /**
     * @return retourne la temperature du capteur
     */
    public int getTemperature(){
        return this.temperature;
    }
}

