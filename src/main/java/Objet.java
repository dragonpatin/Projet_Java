import java.text.*;
import java.util.Calendar;
import java.util.Date;

public class Objet{

    String NomObjet;
    String PieceMaison;
    int Consommation;
    boolean Switch;
    double Duree_utilisation;
    String AdresseMAC;
    Calendar Date_allumage;  //date et heure pour savoir la durée d'utilisation


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

    public static void main(String [] args){
        Objet lampe = new Objet("lampe", "Cuisine", 10, "AB:12:CD:34:OP");
        System.out.println("Objet : " + lampe.getNom() + " Piece : " + lampe.getPiece() + " Conso : " + lampe.getConsommation() + " AdresseMAC : " + lampe.getadresseMAC());
        System.out.println("Durée : " + lampe.getDuree_utilisation());
        System.out.println(lampe.Switch);

        Calendar c = Calendar.getInstance();
        int heure = 12; //c.get(c.HOUR_OF_DAY);
        int minute = 30; //c.get(c.MINUTE);
        int seconde = 00; //c.get(c.SECOND);

        int milliseconde = (int) c.getTimeInMillis();
        System.out.println("seconde = " + milliseconde/1000);
        int s = milliseconde/1000;
        int m = 0;
        while(s>60){
            m++;
            s = s - 60;
        }
        System.out.println("seconde = " + s);
        int h = 0;
        while(m>60){
            h++;
            m = m - 60;
        }
        System.out.println("minute = " + m);
        int d = 0;
        while(h>24){
            d++;
            h = h - 24;
        }
        System.out.println("heure = " + h);



        System.out.println("Temps : " + heure + ":" + minute + ":" + seconde);

        heure = (heure + 11)%24;
        minute = (minute + 40)%60;
        seconde = (seconde + 50)%60;

        System.out.println("Temps : " + heure + ":" + minute + ":" + seconde);


        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm:ss");
        String date = date1.format(c.getTime());
        System.out.println("Date : " + date);

        SimpleDateFormat date2 = new SimpleDateFormat("HH:mm:ss");
        String date3 = date2.format(c.getTime());
        System.out.println("Date3 : " + date3);
    }
}
