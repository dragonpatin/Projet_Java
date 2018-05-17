import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.io.*;
import java.net.*;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

public class Simulateur{
    private float prixHeureCreuse; //Modifie
    private float prixHeurePic;     //Modifier
    private int consommationTotale;
    private entresortie S;
    private Calendar date;
    private int consommationANePasDepasser;
    private Vector<Integer> ConsommationObjet;
    private Vector<Objet> objet;
    private int temperatureExterieur;

    public Simulateur( entresortie e){
        //initialisation de tout.
        this.S = e;
        this.ConsommationObjet = new Vector<Integer>();
        this.objet = new Vector<Objet>();
        this.consommationTotale = 0;
        rechercheDateHeure();
        consommationTotale = 1000;
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
            //Pour une raison étrange cela peut ne pas bien fonctionner
            //Nous devons utilisé les deux écritures possibles de la ligne
            if(chaine.contains("<h4>Option Heures creuses en 2018</h4>")|| chaine.contains("<h4 style=\"text-align: left\">Option Heures creuses en 2018</h4>")) {
                if (chaine.contains(("\n"))) {
                    int i = 0;
                    while(true){
                        if(chaine.charAt(i) == '\n'){
                            String s;
                            s = chaine.substring(0,i);
                            if(s.contains("<h4>Option Heures creuses en 2018</h4>") || s.contains("<h4 style=\"text-align: left\">Option Heures creuses en 2018</h4>")){
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
        consommationTotale = 0;
        for(int i = 0;i<ConsommationObjet.size();i++){
            consommationTotale += ConsommationObjet.get(i) ;
        }
    }
    public void recupereObjet (){
        objet = S.getObjet();
    }
    public void miseAJourObjet (){
        ConsommationObjet.clear();
        for(int i = 0;i<objet.size();i++){
            ConsommationObjet.add(objet.get(i).getConsommation());
        }
    }
    public void ExtinctionAutomatique (int ConsoMax){
            consommationANePasDepasser = ConsoMax;
            Vector<Objet> tmp = new Vector<Objet>();
            Vector<Integer> tmpConsoO = new Vector<Integer>();
            //tris consommation.
            int x=0;
            int max = 0;
            int n = objet.size();
            //Pas encore TEST.
            for(int i = 0;i<n;i++){
                for(int j = 0;j<objet.size();j++){
                    if(objet.get(j).getConsommation()>max){
                        max = ConsommationObjet.get(j);
                        x=j;
                    }
                }
                tmp.add(objet.get(x));
                objet.remove(x);
                tmpConsoO.add(ConsommationObjet.get(x));
                ConsommationObjet.remove(x);
                x=0;
                max = 0;
            }
        objet = tmp;
        ConsommationObjet = tmpConsoO;
            //Tant que la consommation est supérieur

        for(int i = 0;i<objet.size();i++){
            System.out.println(objet.get(i).getNom());
        }
        for(int j = 0;j<=3;j++) {
            int i =0;
            while (consommationTotale >= consommationANePasDepasser && i < objet.size()) {
                if (ConsommationObjet.get(i) != 0 && objet.get(i).getPriorite() == j) {
                    System.out.println(consommationTotale+ " "+j);
                    consommationTotale -= ConsommationObjet.get(i);
                    objet.get(i).AllumerEteindre();
                    System.out.println(consommationTotale);
                }
                i++;
            }
        }
    }
    public void recupereTemperatureExt () throws JAXBException, IOException {
        YahooWeatherService service = null;
        try {
            service = new YahooWeatherService();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        channel = service.getForecast("630908", DegreeUnit.CELSIUS);
        assert channel != null;
        temperatureExterieur = channel.getItem().getCondition().getTemp();
    }
    public void rechercheDateHeure (){
        date = new GregorianCalendar();
    }
    //public Vector getConsommation (){
    //    return null;
    //}
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
        int minute = Newdate.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);
        minute = minute/60;
        if(minute==0){minute = 60;}
        if(Newdate.get(Calendar.HOUR_OF_DAY) != date.get(Calendar.HOUR_OF_DAY)){
            if(Conso.size() == 24){          //On supprime les anciennes consommations si nous en avons suffisaments
                for(int i = 23;i>=12;i++)
                    Conso.remove(i);
                Conso.remove(0);
            }
            Conso.add(consommationTotale/minute);
        }
        else{
            int tmp;
            if(Conso.size()==0){
                tmp = 0;
            }else{
                int j = Conso.size()-12;
                for(int i = Conso.size()-1;i>=j;i--) {
                    Conso.remove(i);
                }
                tmp = Conso.get(Conso.size()-1);
                Conso.remove(Conso.size()-1);}
            Conso.add(tmp+(consommationTotale/minute));
        }
        Vector<preference> pref;
        pref = S.getPreference();
        int Tab[]=new int[12];
        for(int i = 0;i<12;i++)
            Tab[i] = consommationTotale;

        System.out.println(Newdate.get(Calendar.HOUR_OF_DAY));
        if(!pref.isEmpty()) {
            for (int i = 0; i < pref.size(); i++) {
                int t = -1;
                for (int j = 0; j < objet.size(); j++) {
                    if (objet.get(j) == pref.get(i).getObjet()) {
                        t = j;
                    }
                }
                if (t == -1) {
                    exit(1);
                }
                if (!((pref.get(i).getInstruction() == 0) && objet.get(t).getSwitch()) || ((pref.get(i).getInstruction() == 1) && !objet.get(t).getSwitch())) {

                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 1 )%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 1)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[0] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[0] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 2)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 2)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[1] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[1] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 3)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 3)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[2] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[2] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 4)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 4)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[3] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[3] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 5)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 5)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[4] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[4] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 6)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 6)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[5] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[5] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 7)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 7)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[6] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[6] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 8)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 8)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[7] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[7] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 9)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 9)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[8] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[8] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 10)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 10)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[9] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[9] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 11)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 11)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[10] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[10] -= ConsommationObjet.get(t);
                        }
                    }
                    if ((pref.get(i).getHeuredebut() <= (Newdate.get(Calendar.HOUR_OF_DAY) + 12)%24) && (pref.get(i).getHeureFin() > (Newdate.get(Calendar.HOUR_OF_DAY) + 12)%24)) {
                        if ((pref.get(i).getInstruction() == 0)) {
                            objet.get(t).AllumerEteindre();
                            Tab[11] += objet.get(t).Consommation;
                            objet.get(t).AllumerEteindre();
                        } else {
                            Tab[11] -= ConsommationObjet.get(t);
                        }
                    }
                }
                //Si pas trouver exit(1)
                //Si Preference allumer = objet allumer ou Preference eteindre = objet eteindre rien faire
                //si heure debut>= heure actuelle +1 et heure fin <= heure actuelle+1
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +2 et heure fin <= heure actuelle+2
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +3 et heure fin <= heure actuelle+3
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +4 et heure fin <= heure actuelle+4
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +5 et heure fin <= heure actuelle+5
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +6 et heure fin <= heure actuelle+6
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +7 et heure fin <= heure actuelle+7
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +8 et heure fin <= heure actuelle+8
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +9 et heure fin <= heure actuelle+9
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +10 et heure fin <= heure actuelle+10
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +11 et heure fin <= heure actuelle+11
                //Si instruction allumer consototale +
                //Sinon consototal -
                //si heure debut>= heure actuelle +12 et heure fin <= heure actuelle+12
                //Si instruction allumer consototale +
                //Sinon consototal -
            }
        }
        for(int i = 0 ;i<12;i++)
            Conso.add(Tab[i]);
        date = Newdate;
        return Conso;
    }
    public Vector ConsoMois (){
        Calendar Newdate = Calendar.getInstance();
        Vector<Integer> Conso = S.consoMois();

        //On calcule le temps qui c'est écoulé.
        //consommation total étant en Watt/heure
        //Il nous faut savoir la consommation utilisé entre les deux temps
        int minute = Newdate.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);
        minute = minute/60;
        //divisiopn zero impossible;
        if(minute==0){minute = 60;}
        if(Newdate.get(Calendar.MONTH) != date.get(Calendar.MONTH)){
            if(Conso.size() == 6){          //On supprime les anciennes consommations si nous en avons suffisaments
                Conso.remove(0);
            }
            Conso.add(consommationTotale/minute);
        }
        else{
            int tmp;
            if(Conso.size()==0){
                tmp = 0;
            }else{
                tmp = Conso.get(Conso.size()-1);
                Conso.remove(Conso.size()-1);
            }
            Conso.add(tmp+(consommationTotale/minute));
        }
        date = Newdate;
        return Conso;
    }
    public Vector ConsoSemaine (){
        Calendar Newdate = Calendar.getInstance();
        Vector<Integer> Conso = S.consoSemaine();
        //On calcule le temps qui c'est écoulé.
        //consommation total étant en Watt/heure
        //Il nous faut savoir la consommation utilisé entre les deux temps
        int minute = Newdate.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);
        minute = minute/60;
        if(minute==0){minute = 60;}
        if(Newdate.get(Calendar.WEEK_OF_YEAR) != date.get(Calendar.WEEK_OF_YEAR)){
            if(Conso.size() == 4){          //On supprime les anciennes consommations si nous en avons suffisaments
                Conso.remove(0);
            }
            Conso.add(consommationTotale/minute);
        }
        else{
            int tmp;
            if(Conso.size()==0){
                tmp = 0;
            }else{
            tmp = Conso.get(Conso.size()-1);
            Conso.remove(Conso.size()-1);
            }
            Conso.add(tmp+(consommationTotale/minute));
        }
        date = Newdate;
        return Conso;
    }

    public static void main(String [] args) throws IOException {
        entresortie E = new entresortie();
        E.lecturefichier(E.NomFichier);
        Simulateur S = new Simulateur(E);
        S.rechercheDateHeure();
        try {
            S.recherchePrix();
            System.out.println("Prix HP : " + S.getPrixHP()+ "Prix HC : " + S.getPrixHC());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            S.recupereTemperatureExt();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("Temperature : " + S.getTemperatureExt());
        System.out.println("Heure : " + S.getDate().get(Calendar.HOUR_OF_DAY));
        S.recupereObjet();
        S.miseAJourObjet();
        S.calculConsommation();
        System.out.println(S.consommationTotale());
        while(true) {
            S.miseAJourObjet();
            S.calculConsommation();
            System.out.println(S.ConsommationANePasDepasser());
            System.out.println(S.ConsommationObjet());
            System.out.println(S.ConsoMois());
            System.out.println(S.ConsoSemaine());
            System.out.println(S.consoJour());
            System.out.println();
            S.ExtinctionAutomatique(1000);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
