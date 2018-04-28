import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.util.Date;
public class Simulateur{
    int prixHeureCreuse;
    int prixHeurePic;
    int consommationTotale;
    entresortie S;
    SimpleDateFormat date;
    int consommationANePasDepasser;
    Vector<Integer> ConsommationObjet;
    Vector<Objet> objet;
    int temperatureExterieur;

    public Simulateur( entresortie e){
        //initialisation de tout.
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
        DateFormatSymbols monDFS = new DateFormatSymbols();
        String[] joursCourts = new String[] {
                 "",
                "Dimanche",
                "Lundi",
                "Mardi",
                "Mercredi",
                "Jeudi",
                "Vendredi",
                "Samedi" };
        monDFS.setShortWeekdays(joursCourts);
        date = new SimpleDateFormat(
                "EEE dd MMM yyyy HH:mm:ss",
                monDFS);

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

    public SimpleDateFormat getDate (){
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

        //Calcul de prévisualisation.


        return S.consoJour();
    }
    public Vector ConsoMois (){
        return S.consoMois();
    }
    public Vector ConsoSemaine (){
        return S.consoSemaine();
    }

    public static void main(String [] args)
    {
        entresortie E = new entresortie();
        Simulateur S = new Simulateur(E);
        S.rechercheDateHeure();
        Date aujourdhui = new Date();
        System.out.println(S.getDate().format(aujourdhui));
    }

}