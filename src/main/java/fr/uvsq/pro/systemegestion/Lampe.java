package fr.uvsq.pro.systemegestion;

/**
 * Classe Lampe
 * Cette classe est une sous-classe de la classe Objet, elle reprend les memes attributs et méthodes en y ajoutant des spécificités propres a cette classe
 */
public class Lampe extends Objet{

    /**
     * @param Indice_de_luminosite est l'attribut qui gere la luminosité de l'objet
     */
    protected int Indice_de_luminosite;

    /**
     * Constructeur de la classe
     * @param Indice_de_luminosite une luminosité pour la lampe
     */
    public Lampe(int Indice_de_luminosite){
        this.Indice_de_luminosite = Indice_de_luminosite;
    }

    /**
     * @return retourne l'indice de luminosité de l'objet
     */
    public int getLuminosite() {
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
