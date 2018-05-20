package fr.uvsq.pro.systemegestion;

/**
 * Classe PriseElectrique
 * Cette classe est une sous-classe de la classe Objet
 */
public class PriseElectrique extends Objet{

    /**
     * Constructeur de la classe
     * @param Switch paramètre pour gérer si la prise est allumée ou éteinte
     */
    public PriseElectrique(boolean Switch){
        this.Switch = Switch;
    }
}
