package fr.uvsq.pro.systemegestion;
import java.util.Vector;

public class Piece{
    String nom;
    Simulateur S;
    int etage;
    int NombreObjet;
    Vector <Objet> ObjetPiece;
    int consoPiece;


    Piece(String n, int e, Simulateur S ){

    }


    public void changerNom( String n){

    }
    public void changerEtage(int e){

    }
    public void calculConsoPiece (){

    }
    public void ajoutObjet(Objet O){

    }
    public void SupprimerObjet(Objet O){

    }
    public int  getEtage (){
        return 0;
    }
    public int  NombreObjet (){
        return 0;
    }
    public Vector getObjetPiece (){
        return ObjetPiece;
    }
    public int getConsoPiece (){
        return 0;
    }
    public String getNom(){
        return null;
    }
}