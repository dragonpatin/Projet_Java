package fr.uvsq.pro.systemegestion;

/**
 * Classe Radiateur
 * Cette classe est une sous-classe de la classe Objet, elle reprend les mêmes attributs et méthodes en y ajoutant des spécificités propres à cette classe
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
     * @param temperature une température pour le radiateur
     * @param Nom le nom du radiateur
     * @param Piece la pièce où il se trouve
     * @param AdresseMAC son identifiant
     * @param priorite la priorité du radiateur
     * @param consommation la consommation du radiateur
     */
    public Radiateur(int thermostat , int temperature, String Nom, String Piece, String AdresseMAC, int priorite, int consommation){
        this.thermostat = thermostat;
        this.temperature = temperature;
        this.NomObjet = Nom;
        this.PieceMaison = Piece;
        this.AdresseMAC = AdresseMAC;
        this.Priorite = priorite;
        this.Consommation = consommation;
        this.Switch = false;
    }

    /**
     * @return retourne le thermostat du radiateur
     */
    public int getThermostat(){
        if(this.Switch == false){
            return 0;
        }
        return this.thermostat;
    }

    /**
     * @return retourne la température du radiateur
     */
    public int getTemperature(){
        if(this.Switch == false){
            return 0;
        }
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
     * Méthode pour modifier la température du radiateur
     * @param temperature la nouvelle température
     */
    public void ModifieTemperature(int temperature){
        this.temperature = temperature;
    }
}
