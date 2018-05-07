import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.util.Date;

public class Objet{

    String NomObjet;
    String PieceMaison;
    int Consommation;
    boolean Switch;
    double Duree_utilisation;
    String AdresseMAC;
    int Priorite;
    Calendar date;  //date et heure pour savoir la durée d'utilisation


    //Constructeurs :

    public Objet(){
        this.NomObjet = "Objet1";
        this.PieceMaison = "Salon";
        this.Consommation = 10;
        this.Switch = false;
        this.Duree_utilisation = 0;
        this.Priorite = 0;
        this.AdresseMAC = "AA:00:BB:A1:B2";
    }

    public Objet(String NomObjet,String PieceMaison, int Consommation, String AdresseMAC, int priorite){
        this.NomObjet = NomObjet;
        this.PieceMaison = PieceMaison;
        this.Consommation = Consommation;
        this.Switch = false;
        this.Duree_utilisation = 0;
        this.AdresseMAC = AdresseMAC;
        this.Priorite = priorite;
    }

    //Méthodes :

    public boolean AllumerEteindre(){   //ajouter le calcul de la duree d'utilisation
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
        if(this.Switch == false)
            return 0;
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

    public void ModifiePriorite(int Priorite){
        this.Priorite = Priorite;
    }

    public int getPriorite(){
        return this.Priorite;
    }

    public String getadresseMAC() {
        return this.AdresseMAC;
    }

    public static void main(String [] args){
        Objet lampe = new Objet("lampe", "Cuisine", 10, "AB:12:CD:34:OP", 0);
        System.out.println("Objet : " + lampe.getNom() + " Piece : " + lampe.getPiece() + " Conso : " + lampe.getConsommation() + " AdresseMAC : " + lampe.getadresseMAC() + " Priorité : " + lampe.getPriorite());
        System.out.println("Durée : " + lampe.getDuree_utilisation());
        System.out.println(lampe.Switch);

        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");

        System.out.println(df.format(c.getTime()));
    }
}
