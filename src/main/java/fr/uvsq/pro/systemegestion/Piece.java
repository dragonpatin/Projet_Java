package fr.uvsq.pro.systemegestion;
import java.util.Vector;

public class Piece{
    String nom;
    Simulateur S;
    int etage;
    int NombreObjet;
    Vector <Objet> ObjetPiece;
    int consoPiece;


    Piece(String n, int e, Simulateur S){
        this.nom = n;
        this.etage = e;
        this.S = S;
        this.ObjetPiece = new Vector<Objet>();
        this.NombreObjet = 0;
        this.consoPiece = 0;
    }

    public void changerNom(String n){
        this.nom = n;
    }

    public void changerEtage(int e){
        this.etage = e;
    }

    public void calculConsoPiece(){
        int i = 0;
        for(i=0; i<ObjetPiece.size(); i++){
            consoPiece += ObjetPiece.get(i).getConsommation();
        }
    }

    public void ajoutObjet(Objet O){
        ObjetPiece.add(O);
        this.NombreObjet++;
        calculConsoPiece();
    }

    public void SupprimerObjet(Objet O){
        ObjetPiece.remove(O);
        this.NombreObjet--;
        calculConsoPiece();
    }

    public int  getEtage(){
        return this.etage;
    }

    public int  NombreObjet(){
        return this.NombreObjet;
    }

    public Vector getObjetPiece(){
        return ObjetPiece;
    }
    public int getConsoPiece(){
        return this.consoPiece;
    }

    public String getNom(){
        return this.nom;
    }
}