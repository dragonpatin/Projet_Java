public class Objet{


    String NomObjet;
    String PieceMaison;
    int Consommation;
    boolean Switch;
    double Duree_utilisation;
    String AdresseMAC;


    //Constructeurs :

    public Objet(){
        this.NomObjet = "Objet1";
        this.PieceMaison = "Salon";
        this.Consommation = 10;
        this.Switch = false;
        this.Duree_utilisation = 0;
        this.AdresseMAC = "AA:00:BB:A1:B2";
    }

    public Objet(String NomObjet,String PieceMaison, int Consommation, String AdresseMAC){
        this.NomObjet = NomObjet;
        this.PieceMaison = PieceMaison;
        this.Consommation = Consommation;
        this.Switch = false;
        this.Duree_utilisation = 0;
        this.AdresseMAC = AdresseMAC;
    }

    //Méthodes :

    public boolean AllumerEteindre(){
        if(this.Switch){
            this.Switch = false;
        }
        else {
            this.Switch = true;
        }
        return true;
    }

    public double getDuree_utilisation(){
        return this.Duree_utilisation;
    }

    public int getConsommation(){
        return this.Consommation;
    }

    public String getNom(){
        return this.NomObjet;
    }

    public String getPiece(){
        return this.PieceMaison;
    }

    public void ModifieNomObjet(String NomObjet){
        this.NomObjet = NomObjet;
    }

    public void ModifiePieceMaison(String PieceMaison){
        this.PieceMaison = PieceMaison;
    }

    public void ModifieConsommation(int Consommation){
        this.Consommation = Consommation;
    }

    public void ModifieAdresseMAC(String AdresseMAC){
        this.AdresseMAC = AdresseMAC;
    }

    public String getadresseMAC() {
        return this.AdresseMAC;
    }
}
