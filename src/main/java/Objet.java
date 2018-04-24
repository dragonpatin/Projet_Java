public class Objet{


    String NomObjet;
    String PieceMaison;
    int Consommation;
    boolean Switch;
    double Duree_utilisation;
    String AdresseMAC;

    public Objet(){

    }
    public Objet(String NomObjet,String PieceMaison, int Consommation, String AdresseMAC){

    }

    public boolean AllumerEteindre (){
        return true;
    }
    public double getDuree_utilisation (){
        return 0;
    }
    public int getConsommation ( ){
        return 0;
    }
    public String getNom(){
        return null;
    }
    public String getPiece (){
        return null;
    }
    public void ModifieNomObjet ( String NomObjet ){

    }
    public void ModifiePieceMaison ( String PieceMaison ){

    }
    public void ModifieConsommation ( int Consommation ){

    }
    public void ModifieAdresseMAC ( String AdresseMAC ){

    }
    public String getadresseMAC (){
        return null;
    }
}