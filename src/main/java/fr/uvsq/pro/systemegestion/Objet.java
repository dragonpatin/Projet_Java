package fr.uvsq.pro.systemegestion;
import java.util.Calendar;

/**
 * Classe Objet
 * Cette classe permet de créer et gérer des objets connectés, en leur attribuant un nom, une consommation et une adresse MAC unique par exemple
 */

public class Objet{

    /**
     * @param NomObjet est l'attribut pour nommer l'objet
     * @param PieceMaison est l'attribut pour préciser la pièce où ce trouve l'objet
     * @param Consommation est l'attribut qui précise la consommation de l'objet, elle pourra varier
     * @param Switch est l'attibut pour savoir si l'objet est allumé ou éteint
     * @param Duree_utilisation est l'attribut permettant de savoir la durée d'utilisation de l'objet, elle est en minute
     * @param AdresseMAC est l'attribut permettant d'identifier l'objet, elle est unique pour chaque objet
     * @param Date_allumage est l'attribut qui précise la date à laquelle l'objet à été allumé, pour pouvoir ensuite calculer la durée d'utilisation
     * @param Priorite est l'attribut qui indique la priorité de l'objet qui servira lors de l'extinction automatique des objets par ordre de priorité (0 : priorité faible, 3 ; priorité forte)
     */
    protected String NomObjet;
    protected String PieceMaison;
    protected int Consommation;
    protected boolean Switch;
    protected double Duree_utilisation;
    protected String AdresseMAC;
    protected Calendar Date_allumage;
    protected int Priorite;


    /**
     * Constructeur par défaut de la classe Objet
     * Il ne prend aucun paramètre
     * L'objet est éteint de base et à une priorité faible
     */
    public Objet(){
        this.NomObjet = "";
        this.PieceMaison = "";
        this.Consommation = 0;
        this.Switch = false;
        this.Duree_utilisation = 0;
        this.Priorite = 0;
        this.AdresseMAC = "";
    }

    /**
     * Second constructeur de la classe Objet
     * Il va prendre en paramètre :
     * @param NomObjet un nom pour l'objet
     * @param PieceMaison la pièce de la maison où l'objet est situé
     * @param Consommation la consommation de l'objet
     * @param AdresseMAC une adresse MAC unique
     * @param priorite la priorité de l'objet, compris entre 0 et 3
     */
    public Objet(String NomObjet,String PieceMaison, int Consommation, String AdresseMAC, int priorite){
        this.NomObjet = NomObjet;
        this.PieceMaison = PieceMaison;
        this.Consommation = Consommation;
        this.Switch = false;
        this.Duree_utilisation = 0;
        this.AdresseMAC = AdresseMAC;
        this.Priorite = priorite;
    }

    /**
     * Cette fonction permet d'allumer ou d'éteindre l'objet
     * On calcule également la durée d'utilisation lors de l'extinction de l'objet
     * @return retourne le boolean true
     */
    public boolean AllumerEteindre(){
        if(this.Switch){
            this.Switch = false;
            Calendar Date_extinction = Calendar.getInstance();
            int heure = Date_allumage.get(Calendar.HOUR_OF_DAY);
            int minute = Date_allumage.get(Calendar.MINUTE);
            int seconde = Date_allumage.get(Calendar.SECOND);
            int jour = Date_allumage.get(Calendar.DAY_OF_YEAR);

            jour = (jour - Date_extinction.get(Calendar.DAY_OF_YEAR));
            heure = (heure - Date_extinction.get(Calendar.HOUR_OF_DAY));
            minute = (minute - Date_extinction.get(Calendar.MINUTE));
            seconde = (seconde - Date_extinction.get(Calendar.SECOND));

            if(jour<0){
                jour = -jour;
                if(jour>0){
                    if(heure<0){
                        heure = -heure;
                    }
                    heure = 24 - heure;
                }
            }

            if(jour>=1){
                jour = jour - 1;
            }

            if(heure<0){
                heure = -heure;
            }

            if(minute<0){
                minute = -minute;
            }
            if(seconde<0){
                seconde = -seconde;
                if(seconde>30){
                    minute++;
                }
            }

            Duree_utilisation = minute + (heure + jour*24)*60;
        }
        else {
            this.Switch = true;
            Date_allumage = Calendar.getInstance();
            Duree_utilisation = 0;
        }
        return true;
    }

    /**
     * @return retourne la durée d'utilisation de l'objet
     */
    public double getDuree_utilisation(){
        return this.Duree_utilisation;
    }

    /**
     * @return retourne la consommation de l'objet en la faisant varier avec un nombre aléatoire
     */
    public int getConsommation(){
        if(this.Switch == false){
            return 0;
        }
        int a = (int) (Math.random()*(this.Consommation/3)); //genere un nb aleatoire entre 0 et Consommation/3
        return (this.Consommation + a);
    }

    /**
     * @return retourne le nom de l'objet
     */
    public String getNom(){
        return this.NomObjet;
    }

    /**
     * @return retourne la pièce où est situé l'objet
     */
    public String getPiece(){
        return this.PieceMaison;
    }

    /**
     * Méthode pour modifier le nom d'un objet
     * @param NomObjet un nouveau nom pour l'objet
     */
    public void ModifieNomObjet(String NomObjet){
        this.NomObjet = NomObjet;
    }

    /**
     * Méthode pour modifier la pièce où ce trouve l'objet
     * @param PieceMaison la nouvelle pièce
     */
    public void ModifiePieceMaison(String PieceMaison){
        this.PieceMaison = PieceMaison;
    }

    /**
     * Méthode pour modifier la consommation de l'objet
     * @param Consommation la nouvelle consommation
     */
    public void ModifieConsommation(int Consommation){
        this.Consommation = Consommation;
    }

    /**
     * Méthode pour modifier l'adresse MAC de l'objet
     * @param AdresseMAC la nouvelle adresse
     */
    public void ModifieAdresseMAC(String AdresseMAC){
        this.AdresseMAC = AdresseMAC;
    }

    /**
     * Méthode pour modifier la priorité de l'objet
     * @param Priorite la nouvelle priorité
     */
    public void ModifiePriorite(int Priorite){
        this.Priorite = Priorite;
    }

    /**
     * @return retourne la priorité de l'objet
     */
    public int getPriorite(){
        return this.Priorite;
    }

    /**
     * @return retourne l'adresse MAC de l'objet
     */
    public String getadresseMAC() {
        return this.AdresseMAC;
    }

    /**
     * @return retourne l'état de l'objet, s'il est allumé ou éteint
     */
    public boolean getSwitch(){
        return this.Switch;
    }
}