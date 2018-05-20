package fr.uvsq.pro.systemegestion;
import java.util.Vector;
import static java.lang.System.exit;

/**
 * Classe Pièce
 * Cette classe permet de gérer les pièces de la maison. Ces pièces contiendront des objets connectés.
 */
public class Piece{

    /**
     * @param nom le nom de la pièce
     * @param etage l'étage où ce situe la pièce
     * @param NombreObjet le nombre d'objet présent dans la pièce
     * @param ObjetPiece c'est une liste des objets présents dans la pièce
     * @param consoPiece la consommation total des objets présents dans la pièce
     */
    String nom;
    int etage;
    int NombreObjet;
    Vector <Objet> ObjetPiece;
    int consoPiece;

    /**
     * Constructeur de la classe
     * @param n le nom de la pièce
     * @param e l'étage où est située la pièce
     */
    Piece(String n, int e){
        this.nom = n;
        this.etage = e;
        this.ObjetPiece = new Vector<Objet>();
        this.NombreObjet = 0;
        this.consoPiece = 0;
    }

    /**
     * Méthode pour modifier le nom de la pièce
     * @param n le nouveau nom de la pièce
     */
    public void changerNom(String n){
        this.nom = n;
    }

    /**
     * Méthode pour modifier l'étage où est située la pièce
     * @param e le nouvel étage
     */
    public void changerEtage(int e){
        this.etage = e;
    }

    /**
     * Méthode pour la calculer la consommation de la pièce, cad la consommation de tous les objets de la pièce
     */
    public void calculConsoPiece(){
        int i = 0;
        for(i=0; i<ObjetPiece.size(); i++){
            consoPiece += ObjetPiece.get(i).getConsommation();
        }
    }

    /**
     * Méthode pour ajouter un objet dans la pièce
     * @param O le nouvel objet à ajouter
     */
    public void ajoutObjet(Objet O){
        int i = 0;
        for(i=0; i<ObjetPiece.size(); i++){
            if(ObjetPiece.get(i).getadresseMAC() == O.getadresseMAC()){
                System.out.println("Erreur, AdresseMAC déjà existante");
                exit(0);
            }
        }
        ObjetPiece.add(O);
        this.NombreObjet++;
        calculConsoPiece();
    }

    /**
     * Méthode pour supprimer un objet de la pièce
     * @param O l'objet à supprimer
     */
    public void SupprimerObjet(Objet O){
        ObjetPiece.remove(O);
        this.NombreObjet--;
        calculConsoPiece();
    }

    /**
     * @return retourne l'étage où est située la pièce
     */
    public int  getEtage(){
        return this.etage;
    }

    /**
     * @return retourne le nombre d'objet de la pièce
     */
    public int  getNombreObjet(){
        return this.NombreObjet;
    }

    /**
     * @return retourne la liste des objets de la pièce
     */
    public Vector getObjetPiece(){
        return ObjetPiece;
    }

    /**
     * @return retourne la consommation de la pièce, cad celle de la totalité des objet de la pièce
     */
    public int getConsoPiece(){
        return this.consoPiece;
    }

    /**
     * @return retourne le nom de la pièce
     */
    public String getNom(){
        return this.nom;
    }
}