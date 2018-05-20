package fr.uvsq.pro.systemegestion;

/**
 * Classe Radiateur
 * Cette classe est une sous-classe de la classe Objet, elle reprend les memes attributs et méthodes en y ajoutant des spécificités propres a cette classe
 */
public class Radiateur extends Objet{

    /**
     * @param thermostat est l'attribut gérant le thermostat du radiateur
     * @param temperature est l'attribut gérant la température du radiateur
     */
    protected int thermostat;
    protected int temperature;

    /**
     * Constructeur de la classe
     * @param thermostat un thermostat pour le radiateur
     * @param temperature une temperature pour le radiateur
     */
    public Radiateur(int thermostat , int temperature){
        this.thermostat = thermostat;
        this.temperature = temperature;
    }

    /**
     * @return retourne le thermostat du radiateur
     */
    public int getThermostat(){
            return this.thermostat;
    }

    /**
     * @return retourne la temperature du radiateur
     */
    public int getTemperature(){
            return this.temperature;
    }

    /**
     * Méthode pour modifier le thermostat du radiateur
     * @param thermostat le nouveau thermostat
     */
    public void ModifieThermostat(int thermostat){
        this.thermostat = thermostat;
    }

    /**
     * Méthode pour modifier la temperature du radiateur
     * @param temperature la nouvelle temperature
     */
    public void ModifieTemperature(int temperature){
        this.temperature = temperature;
    }
}
