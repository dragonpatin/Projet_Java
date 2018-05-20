package fr.uvsq.pro.systemegestion;

/**
 * Classe PriseElectrique
 * Cette classe est une sous-classe de la classe Objet
 */
public class PriseElectrique extends Objet{

    /**
     * Constructeur de la classe
     * @param Switch paramètre pour gérer si la prise est allumée ou éteinte
     * @param Nom le nom de la prise
     * @param Piece la pièce où elle ce trouve
     * @param AdresseMAC son identifiant unique
     */
    public PriseElectrique(boolean Switch, String Nom, String Piece, String AdresseMAC){
        this.Switch = Switch;
        this.NomObjet = Nom;
        this.PieceMaison = Piece;
        this.AdresseMAC = AdresseMAC;
    }
}
