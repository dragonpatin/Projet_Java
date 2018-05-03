import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.util.Date;

public class Simulateur{
    int prixHeureCreuse;
    int prixHeurePic;
    int consommationTotale;
    entresortie S;
    Calendar date;
    int consommationANePasDepasser;
    Vector<Integer> ConsommationObjet;
    Vector<Objet> objet;
    int temperatureExterieur;

    public Simulateur( entresortie e){
        //initialisation de tout.
        this.S = e;
        this.ConsommationObjet =  new Vector<Integer>();
        this.objet = new Vector<Objet>();
        this.consommationTotale = 0;
        //recherchePrix();
        //recupereObjet();
        //calculConsommation();
        //recupereTemperatureExt();
        rechercheDateHeure();
    }
    public void recherchePrix (){
        int prixHeureCreuse = 0;
        int prixHeurePic = 0 ;
    }
    public void calculConsommation (){
        for(int i = 0;i<ConsommationObjet.size();i++){
            consommationTotale += ConsommationObjet.get(i) ;
        }
    }
    public void recupereObjet (){
        objet = S.getObjet();
        for(int i = 0 ;i<objet.size();i++){
            ConsommationObjet.add(objet.get(i).getConsommation());
        }
    }
    public void miseAJourObjet (){
        objet = S.getObjet();
        ConsommationObjet.clear();
        for(int i = 0;i<objet.size();i++){
            ConsommationObjet.add(objet.get(i).getConsommation());
        }
    }
    public void ExtinctionAutomatique (int ConsoMax){
            consommationANePasDepasser = ConsoMax;
    }
    public void recupereTemperatureExt (){
        temperatureExterieur = 0;
    }
    public void rechercheDateHeure (){
        date = Calendar.getInstance();
    }
    public Vector getConsommation (){
        return null;
    }
    public int getPrixHC (){
        return prixHeureCreuse;
    }
    public int getPrixHP (){
        return prixHeurePic;
    }
    public int ConsommationANePasDepasser ( ){
        return consommationANePasDepasser;
    }
    public int consommationTotale (){
        return consommationTotale;
    }

    public Calendar getDate (){
        return date;
    }
    public Vector ConsommationObjet (){
        return ConsommationObjet;
    }
    public int getTemperatureExt (){
        return temperatureExterieur;
    }
    public Vector getObjet (){
        return objet;
    }
    public Vector consoJour (){

        Calendar Newdate = Calendar.getInstance();
        Vector<Integer> Conso = S.consoJour();
        if(Newdate.DAY_OF_MONTH != date.DAY_OF_MONTH){
            if(Conso.size() == 6){          //On supprime les anciennes consommations si nous en avons suffisaments
                Conso.remove(0);
            }
            Conso.add(consommationTotale);
        }
        else{
            int tmp;
            if(Conso.size()==0){
                tmp = 0;
            }else{
                tmp = Conso.get(Conso.size()-1);
                Conso.remove(Conso.size()-1);}
            Conso.add(tmp+consommationTotale);
        }
        date = Newdate;
        return Conso;
    }
    public Vector ConsoMois (){
        Calendar Newdate = Calendar.getInstance();
        Vector<Integer> Conso = S.consoMois();
        if(Newdate.MONTH != date.MONTH){
            if(Conso.size() == 6){          //On supprime les anciennes consommations si nous en avons suffisaments
                Conso.remove(0);
            }
            Conso.add(consommationTotale);
        }
        else{
            int tmp;
            if(Conso.size()==0){
                tmp = 0;
            }else{
                tmp = Conso.get(Conso.size()-1);
                Conso.remove(Conso.size()-1);}
            Conso.add(tmp+consommationTotale);
        }
        date = Newdate;
        return Conso;
    }
    public Vector ConsoSemaine (){
        Calendar Newdate = Calendar.getInstance();
        Vector<Integer> Conso = S.consoSemaine();
        if(Newdate.WEEK_OF_YEAR != date.WEEK_OF_YEAR){
            if(Conso.size() == 6){          //On supprime les anciennes consommations si nous en avons suffisaments
                Conso.remove(0);
            }
            Conso.add(consommationTotale);
        }
        else{
            int tmp;
            if(Conso.size()==0){
                tmp = 0;
            }else{
            tmp = Conso.get(Conso.size()-1);
            Conso.remove(Conso.size()-1);}
            Conso.add(tmp+consommationTotale);
        }
        date = Newdate;
        return Conso;
    }

    public static void main(String [] args)
    {
        entresortie E = new entresortie();
        E.lecturefichier(E.NomFichier);
        Simulateur S = new Simulateur(E);
        S.rechercheDateHeure();
        Vector<Integer> Conso = S.ConsoMois();
    }

}