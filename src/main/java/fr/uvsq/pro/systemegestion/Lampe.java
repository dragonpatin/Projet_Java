package fr.uvsq.pro.systemegestion;

/**
 * Classe Lampe
 * Cette classe est une sous-classe de la classe Objet, elle reprend les mêmes attributs et méthodes en y ajoutant des spécificités propres à cette classe
 */
public class Lampe extends Objet{

    /**
     * @param Indice_de_luminosite est l'attribut qui gère la luminosité de l'objet
     */
    protected int Indice_de_luminosite;

    /**
     * Constructeur de la classe
     * @param Indice_de_luminosite une luminosité pour la lampe
     * @param Nom un nom pour la lampe
     * @param Piece la pièce où elle se situe
     * @param AdresseMAC son identifiant unique
     * @param priorite la priorité de la lampe, comprise entre 0 et 3
     * @param consommation la consommation de la lampe
     */
    public Lampe(int Indice_de_luminosite, String Nom, String Piece, String AdresseMAC, int priorite, int consommation){
        this.Indice_de_luminosite = Indice_de_luminosite;
        this.NomObjet = Nom;
        this.PieceMaison = Piece;
        this.AdresseMAC = AdresseMAC;
        this.Priorite = priorite;
        this.Consommation = consommation;
        this.Switch = false;
        this.Duree_utilisation = 0;
    }

    /**
     * @return retourne l'indice de luminosité de l'objet
     */
    public int getLuminosite() {
        if(this.Switch == false){
            return 0;
        }
        return this.Indice_de_luminosite;
    }

    /**
     * Méthode pour modifier la luminosité de l'objet
     * @param Indice_de_luminosite la nouvelle luminosité
     */
    public void ModifieLuminosite(int Indice_de_luminosite){
        this.Indice_de_luminosite = Indice_de_luminosite;
    }
}
