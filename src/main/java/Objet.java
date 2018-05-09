import java.text.*;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Objet{

    protected String NomObjet;
    protected String PieceMaison;
    protected int Consommation;
    protected boolean Switch;
    protected double Duree_utilisation;  //Durée d'utilisation en minute
    protected String AdresseMAC;
    protected Calendar Date_allumage;  //date et heure pour savoir la durée d'utilisation
    protected int Priorite; // 0 : priorite basse, 3 : priorité haute


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

    public boolean AllumerEteindre(){   //ajouter le calcul de la duree d'utilisation pour les jours
        if(this.Switch){
            this.Switch = false;
            Calendar Date_extinction = Calendar.getInstance();
            int heure = Date_allumage.get(Calendar.HOUR_OF_DAY);
            int minute = Date_allumage.get(Calendar.MINUTE);

            heure = (heure - Date_extinction.get(Calendar.HOUR_OF_DAY))%24;
            minute = (minute - Date_extinction.get(Calendar.MINUTE));

            if(heure<0){
                heure = -heure;
            }
            if(minute<0){
                minute = -minute;
            }

            Duree_utilisation = minute + heure*60;
        }
        else {
            this.Switch = true;
            Date_allumage = Calendar.getInstance();
            Duree_utilisation = 0;
        }
        return true;
    }

    public double getDuree_utilisation(){
        return this.Duree_utilisation;
    }

    public int getConsommation(){
        if(this.Switch == false){
            return 0;
        }
        Random r = new Random();
        int a = -(this.Consommation/4) + r.nextInt(this.Consommation/4 - (-this.Consommation/4));   //genere un nb aleatoire entre -Consommation/4 et Consommation/4
        return (this.Consommation + a);
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

    public boolean getSwitch(){
        return this.Switch;
    }

    public static void main(String [] args) throws InterruptedException {
        Objet lampe = new Objet("lampe", "Cuisine", 10, "AB:12:CD:34:OP", 0);
        System.out.println("Objet : " + lampe.getNom() + " Piece : " + lampe.getPiece() + " Conso : " + lampe.getConsommation() + " AdresseMAC : " + lampe.getadresseMAC() + " Priorité : " + lampe.getPriorite());
        System.out.println("Durée : " + lampe.getDuree_utilisation());
        System.out.println(lampe.Switch);

        lampe.AllumerEteindre();
        System.out.println(lampe.Switch);
        System.out.println(lampe.getConsommation());
        System.out.println(lampe.getDuree_utilisation() + " min");

        //TimeUnit.MINUTES.sleep(1);  //wait de 1 minute

        lampe.AllumerEteindre();
        System.out.println(lampe.Switch);
        System.out.println(lampe.getDuree_utilisation() + " min");

    }
}
