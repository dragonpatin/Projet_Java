import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Condition;
import com.github.fedy2.weather.data.unit.DegreeUnit;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.util.Date;
import java.io.*;
import java.net.*;
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
        rechercheDateHeure();
    }
    public void recherchePrix (){
        int prixHeureCreuse = 0;
        int prixHeurePic = 0 ;

        try{
            URL url = new URL("http://www.siteduzero.com/forum-83-429067-p1-recuperation-de-donnees-web.html#r4003054");

            URLConnection con=url.openConnection();
            System.out.println(con.getContent());
            InputStream input = con.getInputStream();
            while(input.available()>0)
                System.out.print((char)input.read());
        }
        catch(MalformedURLException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public void calculConsommation (){
        for(int i = 0;i<ConsommationObjet.size();i++){
            consommationTotale += ConsommationObjet.get(i) ;
        }
    }
    public void recupereObjet (){
        objet = S.getObjet();
    }
    public void miseAJourObjet (){
        recupereObjet();
        ConsommationObjet.clear();
        for(int i = 0;i<objet.size();i++){
            if(objet.get(i).AllumerEteindre())
                ConsommationObjet.add(objet.get(i).getConsommation());
        }
    }
    public void ExtinctionAutomatique (int ConsoMax){
            consommationANePasDepasser = ConsoMax;
    }
    public void recupereTemperatureExt (){
        YahooWeatherService service = null;
        try {
            service = new YahooWeatherService();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        try {
            channel = service.getForecast("610264", DegreeUnit.CELSIUS);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        temperatureExterieur = channel.getItem().getCondition().getTemp();
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
            if(Conso.size() == 24){          //On supprime les anciennes consommations si nous en avons suffisaments
                for(int i = 23;i>=12;i++)
                    Conso.remove(i);
                Conso.remove(0);
            }
            Conso.add(consommationTotale);
        }
        else{
            int tmp;
            if(Conso.size()==0){
                tmp = 0;
            }else{
                for(int i = 23;i>=12;i++)
                    Conso.remove(i);
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
            if(Conso.size() == 4){          //On supprime les anciennes consommations si nous en avons suffisaments
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
        //E.lecturefichier(E.NomFichier);
        Simulateur S = new Simulateur(E);
        S.rechercheDateHeure();
        Vector<Integer> Conso = S.ConsoMois();
        //S.recherchePrix();
        S.recupereTemperatureExt();
    }

}