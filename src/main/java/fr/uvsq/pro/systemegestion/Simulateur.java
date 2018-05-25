package fr.uvsq.pro.systemegestion;
/*
import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
*/
import javax.xml.bind.JAXBException;
import java.util.*;
import java.io.*;
import java.net.*;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

/**
 * Classe Simulateur
 * Il gère les objets.
 * Utilisation de consoMois, ConsoJour, ConsoSemaine : il est conseillé de faire appel aux 3 pour la sauvegarde
 * Pour le module interface, il faut demander les 3 et utiliser la fonction rechercheDateHeure pour mettre à jour le temps
 */
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

    /**
     * Constructeur de la classe Simulateur
     * Initialisation de la classe simulateur
     * @param e La classe entresortie : elle contient tous les objets et les préférences.
     */
    public Simulateur( entresortie e){
        //initialisation de tout.
        this.S = e;
        this.ConsommationObjet = new Vector<Integer>();
        this.objet = new Vector<Objet>();
        this.consommationTotale = 0;
        rechercheDateHeure();
        consommationTotale = 0;
    }

    /**
     * Recherche sur internet du prix de l'electricité chez EDF avec une puissance de 9 kWa.
     * Il récupère le prix en heure creuse et heure de pointe
     * @throws IOException Exception lancée s'il y a une erreur lors de la connexion au site Web
     */
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
            //Nous devons donc utiliser les deux écritures possibles de la ligne
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

    /**
     * Calcule La consommation de tous les objets.
     * Il utilise le vecteur Consommation qui contient la consommation de chaque objet.
     */
    public void calculConsommation (){
        consommationTotale = 0;
        for(int i = 0;i<ConsommationObjet.size();i++){
            consommationTotale += ConsommationObjet.get(i) ;
        }
    }

    /**
     * Récupère les objets dans la classe entresortie pour être à jour.
     */
    public void recupereObjet (){
        objet = S.getObjet();
    }

    /**
     * On réinitialise le vecteur ConsommationObjet puis nous récupérons la consommation de
     * chaque objet que nous stockons dans ConsommationObjet.
     */
    public void miseAJourObjet (){
        rechercheDateHeure();
        ConsommationObjet.clear();
        for(int i = 0;i<objet.size();i++){
            ConsommationObjet.add(objet.get(i).getConsommation());
        }
    }

    /**
     * Explication rapide : Nous trions les objets en fonction de leur consommation.
     * Ensuite nous parcourons chaque priorité de 3 à 1 et nous éteignons tous les objets jusqu'à ce que la ConsommationTotal soit inférieure à ConsoMax
     * @param ConsoMax Entier qui représente la limite à ne pas dépasser
     */
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
            //Tant que la consommation est supérieure
        for(int j = 3;j>=1;j--) {
            int i =0;
            while (consommationTotale >= consommationANePasDepasser && i < objet.size()) {
                if (ConsommationObjet.get(i) != 0 && objet.get(i).getPriorite() == j) {
                    consommationTotale -= ConsommationObjet.get(i);
                    objet.get(i).AllumerEteindre();
                }
                i++;
            }
        }
    }
/*
    /**
     * Récupère la température Extérieure ressentie à Versailles
     * @throws JAXBException Necesaire pour le fonctionnement de la bibliothèque
     * @throws IOException Exception
     */
/*
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
    */

    /**
     * Récupère la date au moment t. Il récupère la date et l'heure
     */
    public void rechercheDateHeure (){
        date = new GregorianCalendar();
    }
    //public Vector getConsommation (){
    //    return null;
    //}

    /**
     * Retourne Le prix en heure creuse de EDF
     * @return le prix en heure creuse
     */
    public Float getPrixHC (){
        return prixHeureCreuse;
    }

    /**
     * Retourne Le prix en heure de pic de EDF
     * @return le prix en heure de pic de consommation
     */
    public Float getPrixHP (){
        return prixHeurePic;
    }

    /**
     *
     * @return La consommation à ne pas dépasser
     */
    public int ConsommationANePasDepasser ( ){
        return consommationANePasDepasser;
    }
    
     /**
      * Modifie la valeur de la consommation à ne pas dépasser
      */
      public void SetConsommationANePasDepasser (int c ){
         this.consommationANePasDepasser=c;
      }

    /**
     * Somme de la consommation de tous les objets
     * @return La consommation totale des objets actuellement
     */
    public int consommationTotale (){
        return consommationTotale;
    }

    /**
     *
     * @return La date où le Simulateur a recupéré la consommation des objets
     */
    public Calendar getDate (){
        return date;
    }

    /**
     *
     * @return Retourne le vecteur qui contient la consommation de tous les objets
     */
    public Vector ConsommationObjet (){
        return ConsommationObjet;
    }
    /*
    /**
     *
     * @return retourne la température extérieure de Versailles
     */
    /*
    public int getTemperatureExt () throws JAXBException, IOException {
        try {
            recupereTemperatureExt();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temperatureExterieur;
    }*/

    /**
     *
     * @return Le vector objet contenant les objets
     */
    public Vector<Objet> getObjet (){
        return objet;
    }

    /**
     * On récupère la consommation avec la classe engtresortie, on le met à jour avec la consommation actuelle puis grâce aux préférences de l'utilisateur et de la consommation actuelle nous faisons une simulation sur 12h
     * @return La consommation heure par heure avec 12 heures de simulation + les anciennes données sauvegardées
     */
    public Vector<Integer> consoJour (){
        Calendar Newdate = Calendar.getInstance();
        Vector<Integer> Conso = S.consoJour();
        int minute = Newdate.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);
        minute = minute/60;
        if(minute==0){minute = 60;}
        if(Newdate.get(Calendar.HOUR_OF_DAY) != date.get(Calendar.HOUR_OF_DAY)){
            if(Conso.size() == 24){          //On supprime les anciennes consommations si nous en avons suffisament
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
        return Conso;
    }

    /**
     * On récupére la consommation avec la classe engtresortie, on le met à jour avec la consommation actuelle
     * @return La consommation des mois
     */
    public Vector<Integer> ConsoMois (){
        Calendar Newdate = Calendar.getInstance();
        Vector<Integer> Conso = S.consoMois();

        //On calcule le temps qui s'est écoulé.
        //consommation totale étant en Watt/heure
        //Il nous faut savoir la consommation utilisée entre les deux temps
        int minute = Newdate.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);
        minute = minute/60;
        //divisiopn zero impossible;
        if(minute==0){minute = 60;}
        if(Newdate.get(Calendar.MONTH) != date.get(Calendar.MONTH)){
            if(Conso.size() == 6){          //On supprime les anciennes consommations si nous en avons suffisament
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
        return Conso;
    }

    /**
     * On récupère la consommation avec la classe entresortie, on le met à jour avec la consommation actuelle
     * @return La consommation des Semaines
     */
    public Vector<Integer> ConsoSemaine (){
        Calendar Newdate = Calendar.getInstance();
        Vector<Integer> Conso = S.consoSemaine();
        //On calcule le temps qui s'est écoulé.
        //consommation totale étant en Watt/heure
        //Il nous faut savoir la consommation utilisée entre les deux temps
        int minute = Newdate.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);
        minute = minute/60;
        if(minute==0){minute = 60;}
        if(Newdate.get(Calendar.WEEK_OF_YEAR) != date.get(Calendar.WEEK_OF_YEAR)){
            if(Conso.size() == 4){          //On supprime les anciennes consommations si nous en avons suffisament
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
        return Conso;
    }

}
