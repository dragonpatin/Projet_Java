package fr.uvsq.pro.systemegestion;

/**
 * Classe CapteurTemperature
 * Cette classe est une sous-classe de la classe Objet, elle reprend les mêmes attributs et méthodes en y ajoutant des spécificités propres à cette classe
 */

public class CapteurTemperature extends Objet{

    /**
     * @param temperature est l'attribut qui va gérer la temperature
     */
    protected int temperature;

    /**
     * Constructeur de la classe
     * @param temperature une température pour le capteur
     * @param Nom un nom pour le capteur
     * @param Piece la pièce où le capteur se trouve
     * @param AdresseMAC son identifiant unique
     */
    public CapteurTemperature(int temperature, String Nom, String Piece, String AdresseMAC){
        this.temperature = temperature;
        this.NomObjet = Nom;
        this.PieceMaison = Piece;
        this.Switch = false;
        this.AdresseMAC = AdresseMAC;
        this.Consommation = 0;
        this.Duree_utilisation = 0;
    }

    /**
     * @return retourne la température du capteur
     */
    public int getTemperature(){
        if(this.Switch == false){
            return 0;
        }
        return this.temperature;
    }
}

