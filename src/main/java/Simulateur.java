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
    float prixHeureCreuse; //Modifie
    float prixHeurePic;     //Modifier
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
    public void recherchePrix () throws IOException {
        prixHeureCreuse = 0;
        prixHeurePic = 0 ;

        HttpURLConnection conn = (HttpURLConnection) new URL("https://www.fournisseur-energie.com/edf-fournisseur-historique/tarif-2018/").openConnection();
        conn.connect();

        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

        byte[] bytes = new byte[2048];
        int tmp ;
        while( (tmp = bis.read(bytes) ) != -1 ) {
            String chaine = new String(bytes,0,tmp);
            //(§System.out.println(chaine);

            if(chaine.contains(("<h4>Option Heures creuses en 2018</h4>"))) {
                if (chaine.contains(("\n"))) {
                    int i = 0;
                    while(true){
                        if(chaine.charAt(i) == '\n'){
                            String s;
                            s = chaine.substring(0,i);
                            if(s.contains("<h4>Option Heures creuses en 2018</h4>")){
                                while(true) {
                                    if (chaine.charAt(i) == '\n') {
                                        s = chaine.substring(0, i);
                                        chaine = chaine.substring(i);
                                        i = 0;
                                        if (s.contains("<td>9</td>")) {
                                            i = 1;
                                            while(chaine.charAt(i) != '\n'){
                                                i++;
                                            }
                                            chaine = chaine.substring(i);
                                            i=1;
                                            while(chaine.charAt(i) != '\n'){
                                                i++;
                                            }
                                            s = chaine.substring(0, i);
                                            chaine = chaine.substring(i);
                                            s=s.replace("<td>","");
                                            s=s.replace("</td>","");
                                            s=s.replace(" €","");
                                            prixHeurePic = Float.parseFloat(s);
                                            i=1;
                                            while(chaine.charAt(i) != '\n'){
                                                i++;
                                            }
                                            s = chaine.substring(0, i);
                                            s=s.replace("<td>","");
                                            s=s.replace("</td>","");
                                            s=s.replace(" €","");
                                            prixHeureCreuse = Float.parseFloat(s);
                                            conn.disconnect();
                                            return;
                                        }


                                    }
                                    i++;
                                }

                            }
                            chaine = chaine.substring(i);
                            i = 0;
                        }
                        i++;
                    }
                }

            }

        }

        conn.disconnect();
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
            ConsommationObjet.add(objet.get(i).getConsommation());
        }
    }
    public void ExtinctionAutomatique (int ConsoMax){
            consommationANePasDepasser = ConsoMax;
            Vector<Objet> tmp = new Vector<Objet>();
            //tris consommation.
            int x=0;
            int max = 0;

            //Pas encore TEST.
            for(int i = 0;i<objet.size();i++){
                for(int j = 0;j<objet.size();j++){
                    if(objet.get(i).getConsommation()>max){
                        max = objet.get(i).getConsommation();
                        x=j;
                    }
                }
                tmp.add(objet.get(x));
                objet.remove(x);
                x=0;
                max = 0;

            }
        objet = tmp;
            //Tant que la consommation est supérieur
        int i =0;
        for(int j = 0;j<=1;j++) {
            while (consommationTotale > consommationANePasDepasser) {
                if (objet.get(i).getConsommation() != 0 && objet.get(i).getPriorite() == j) {
                    consommationTotale -= objet.get(i).getConsommation();
                    objet.get(i).AllumerEteindre();
                }
                i++;
            }
        }
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
            channel = service.getForecast("630908", DegreeUnit.CELSIUS);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        temperatureExterieur = channel.getItem().getCondition().getTemp();
    }
    public void rechercheDateHeure (){
        date = new GregorianCalendar();
    }
    public Vector getConsommation (){
        return null;
    }
    public Float getPrixHC (){
        return prixHeureCreuse;
    }
    public Float getPrixHP (){
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
        if(Newdate.get(Calendar.DAY_OF_MONTH) != date.get(Calendar.DAY_OF_MONTH)){
            if(Conso.size() == 24){          //On supprime les anciennes consommations si nous en avons suffisaments
                for(int i = 23;i>=12;i++)
                    Conso.remove(i);
                Conso.remove(0);
            }
            Conso.add(consommationTotale);
            Vector<preference> pref = S.getPreference();
            for(int i = 0;i<pref.size();i++){
                if(pref.get(i).getInstruction()==0){

                }else{

                }
            }
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
        if(Newdate.get(Calendar.MONTH) != date.get(Calendar.MONTH)){
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
        if(Newdate.get(Calendar.WEEK_OF_YEAR) != date.get(Calendar.WEEK_OF_YEAR)){
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

    public static void main(String [] args) throws IOException {
        entresortie E = new entresortie();
        //E.lecturefichier(E.NomFichier);
        Simulateur S = new Simulateur(E);
        S.rechercheDateHeure();
        Vector<Integer> Conso = S.ConsoMois();
        try {
            S.recherchePrix();
            System.out.println("Prix HP : " + S.getPrixHP()+ "Prix HC : " + S.getPrixHC());
        } catch (IOException e) {
            e.printStackTrace();
        }
        S.recupereTemperatureExt();
        System.out.println("Temperature : " + S.getTemperatureExt());
        System.out.println(S.getDate());
    }

}