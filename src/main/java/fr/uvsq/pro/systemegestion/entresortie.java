/**
 * Java source Entrée Sortie
 */
package fr.uvsq.pro.systemegestion;
import java.io.*;
import java.util.*;
import javax.swing.*;


public class entresortie{

    /**
     * @param NomFichier est l'attribut qui permet de garder en sauvegarde le fichier de sauvegarde des données
     * @param ObjetFavoris C'est une liste qui permet de garder les objets favoris demandé par l'utilisateur
     * @param Preference C'est une liste qui permet de sauvegarder toutes les préférences sur chaque objet
     * @param ConsoJour C'est une liste qui permet de donner la consommation journalière de tous les jours déjà sauvegarder ou en calcule
     * @param ConsoMois C'est une liste qui permet de donner la consommation de chaque Mois déjà en sauvegarde ou en calcule
     * @param ConsoSemaine C'est une liste qui permet de donner la consommation de chaque Semaine déjà en sauvegarde ou en calcule
     */

    File NomFichier;
    Vector <Objet> ListeObjet;
    Vector <Objet> ObjetFavoris;
    Vector <preference> Preference;
    Vector <Integer> ConsoJour;
    Vector <Integer> ConsoMois;
    Vector <Integer> ConsoSemaine;

    /**
     * C'est le premier constructeur de la classe entresortie.
     * Il est ici pour créer un fichier avec les différentes disposition de la page qui permettront de le lire et d'écrire correctement
     * @param nomfichier Il va prendre en paramètre un nom de fichier
     * @throws IOException Si jamais il y a une erreur dans la création du fichier
     */

    public entresortie(String nomfichier){
        try {
            this.NomFichier = new File(nomfichier);
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(nomfichier)));
            writer.write("#"+"\n"+"+"+"\n"+"-"+"\n"+"%"+"\n"+"?");
            writer.close();
            this.ListeObjet = new Vector<Objet>();
            this.ObjetFavoris = new Vector<Objet>();
            this.Preference = new Vector<preference>();
            this.ConsoJour = new Vector<Integer>();
            this.ConsoMois = new Vector<Integer>();
            this.ConsoSemaine = new Vector<Integer>();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * C'est le deuxième constructeur de la classe entresortie.
     * Il ne prend aucun paramètre car nous utilisons un JfileChooser qui permet d'ouvrire un Jframe pour choisir son fichier
     * S'il n'a pas de fichier alors le code s'arrête
     */

    public entresortie(){
        //JFileChooser dialogue = new JFileChooser();
        //dialogue.showOpenDialog(null);
        //System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
        //this.NomFichier = dialogue.getSelectedFile();

        this.ListeObjet = new Vector<Objet>();
        this.ObjetFavoris = new Vector<Objet>();
        this.Preference = new Vector<preference>();
        this.ConsoJour = new Vector<Integer>();
        this.ConsoMois = new Vector<Integer>();
        this.ConsoSemaine = new Vector<Integer>();
        }

    /**
     * Elle permet de lire le fichier choisi au préalable
     * @param NomFichier Elle prend en paramètre le fichier en question
     * Elle va lire le fichier d'une façon bien précise, si le fichier a été modifier ou corrompu de manière délibéré
     * ou accidentellement alors il ne pourra plus le lire et une erreur apparaitra.
     */


    public void lecturefichier(File NomFichier){
        try{
            String str;
            BufferedReader fichier = new BufferedReader(new FileReader(NomFichier));
            Objet obj = new Objet();

            //Création de l'objet de lecture
            while ((str = fichier.readLine()) != null) {
                if(str.equals("#")){
                    str = fichier.readLine();
                    String NomObjet = str;
                    str = fichier.readLine();
                    String PieceMaison=str;
                    str = fichier.readLine();
                    int Consommation = Integer.parseInt(str);
                    str = fichier.readLine();
                    String AdresseMAC = str;
                    str = fichier.readLine();
                    int priorite = Integer.parseInt(str);

                    obj = new Objet(NomObjet,PieceMaison,Consommation,AdresseMAC,priorite);
                    ListeObjet.addElement(obj);

                    if(priorite==3){
                        ObjetFavoris.addElement(obj);
                    }
                }

                else if (str.equals("+")){
                    str = fichier.readLine();
                    if(!(str.equals("-") || str .equals("%") || str.equals("?"))){
                        String Nom = str;
                        str = fichier.readLine();
                        for(int j=0; j<ListeObjet.size();j++){
                            if(ListeObjet.elementAt(j).getNom().equals(str)){
                                obj = ListeObjet.elementAt(j);
                            }
                        }
                        str = fichier.readLine();
                        int instruction = Integer.parseInt(str);
                        str = fichier.readLine();
                        int heure_debut = Integer.parseInt(str);
                        str = fichier.readLine();
                        int heure_fin = Integer.parseInt(str);

                        preference P = new preference(Nom,obj,instruction,heure_debut,heure_fin);
                        Preference.addElement(P);
                    }

                }

                else if(str.equals("-")){
                    str = fichier.readLine();
                    if(!(str .equals("%") || str.equals("?"))){
                        int consojour = Integer.parseInt(str);
                        ConsoJour.addElement(consojour);
                    }

                }

                else if(str.equals("%")){
                    str = fichier.readLine();
                    if(!str.equals("?")){
                        int consosemaine = Integer.parseInt(str);
                        ConsoSemaine.addElement(consosemaine);
                    }

                }

                else if(str.equals("?")){
                    str = fichier.readLine();
                    int consomois = Integer.parseInt(str);
                    ConsoMois.addElement(consomois);
                }
            }
            fichier.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette fonction permet d'enregistrer le nom du fichier et le fichier en entier dans un string pour pouvoir le modifier a souhait
     * @param f Elle prends en paramètre le nom du fichier
     * @return Elle return le string qui contient tout fichier lu
     */
    public static String enregistre(File f) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
            StringWriter out = new StringWriter();
            int b;
            while ((b=in.read()) != -1)
                out.write(b);
            out.flush();
            out.close();
            in.close();
            return out.toString();
        }
        catch (IOException ie)
        {
            ie.printStackTrace();
        }
        return null;
    }

    /**
     * Cette fonction permet d'écrire dans le fichier en temps réel, les modifications effectuées ainsi sur les vecteurs de la classe
     * en question
     * @param NomFichier
     */
    public void ecriture ( File NomFichier){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.NomFichier));
            writer.write("#"+"\n"+"+"+"\n"+"-"+"\n"+"%"+"\n"+"?");
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        String test,fina= "";

        int acc;
        for(int i =0;i<this.ListeObjet.size();i++) {
            test = enregistre(NomFichier);
            acc = test.indexOf("#");
            String before = test.substring(0, acc+1);
            String after = test.substring(acc+2);
            System.out.println(before);
            System.out.println(after);
            String code = ListeObjet.elementAt(i).NomObjet +"\n" + ListeObjet.elementAt(i).PieceMaison + "\n"
                    + ListeObjet.elementAt(i).Consommation + "\n" + ListeObjet.elementAt(i).AdresseMAC + "\n"
                    + ListeObjet.elementAt(i).Priorite;
            fina = before + "\n" + code + "\n" + after;
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(NomFichier));
                writer.write(fina);
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        for(int i =0;i<this.Preference.size();i++) {
            acc = fina.indexOf("+");
            String before = fina.substring(0, acc + 1);
            String after = fina.substring(acc+2);
            String code = Preference.elementAt(i).nom +"\n" + Preference.elementAt(i).O.getNom()
                    + "\n" + Preference.elementAt(i).instruction + "\n" + Preference.elementAt(i).heure_debut+"\n"+
                    + Preference.elementAt(i).heure_fin;

            fina = before + "\n" + code + "\n" + after;
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(NomFichier));
                writer.write(fina);
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        for(int i =0;i<this.ConsoJour.size();i++) {
            acc = fina.indexOf("-");
            String before = fina.substring(0, acc + 1);
            String after = fina.substring(acc +2);
            String code = ConsoJour.elementAt(i).toString();
            fina = before + "\n" + code + "\n" + after;
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(NomFichier));
                writer.write(fina);
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        for(int i =0;i<this.ConsoSemaine.size();i++) {
            acc = fina.indexOf("%");
            String before = fina.substring(0, acc + 1);
            String after = fina.substring(acc +2 );
            String code = ConsoSemaine.elementAt(i).toString();
            fina = before + "\n" + code + "\n" + after;
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(NomFichier));
                writer.write(fina);
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        for(int i =0;i<this.ConsoMois.size();i++) {
            acc = fina.indexOf("?");
            String before = fina.substring(0, acc + 1);
            String code = ConsoMois.elementAt(i).toString();
            fina = before + "\n" + code + "\n";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(NomFichier));
                writer.write(fina);
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void modifieObjet ( Vector ListeObjet,Objet obj ){
        // UTILISE LES FONCTIONS DANS OBJET
        for(int i =0 ; i<ListeObjet.size();i++){
            if(ListeObjet.elementAt(i).equals(obj)){
                ListeObjet.removeElementAt(i);
                ListeObjet.addElement(obj);
                try{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(this.NomFichier));
                    writer.write("#"+"\n"+"+"+"\n"+"-"+"\n"+"%"+"\n"+"?");
                    writer.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
                ecriture(this.NomFichier);
            }
        }
    }
    public void modifieConsommation ( Vector ListeObjet){
        //utilité ?
    }
    public void modifiePreference(Vector Preference, preference pref){
        preference p = new preference();
        for(int i =0 ; i<Preference.size();i++){
            if(Preference.elementAt(i).equals(pref)){
                Preference.removeElementAt(i);
                Preference.addElement(pref);
                try{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(this.NomFichier));
                    writer.write("#"+"\n"+"+"+"\n"+"-"+"\n"+"%"+"\n"+"?");
                    writer.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
                ecriture(this.NomFichier);
            }
        }
    }
    public void modifiePiece(Vector ListeObjet){
            //un utilité ?
    }
    public Vector getObjet (){
        return ListeObjet;
    }
    public int getConsommation (){
        for(int i =0 ; i<ListeObjet.size();i++){
            if(ListeObjet.elementAt(i).equals(this)){
                return ListeObjet.elementAt(i).Consommation;
            }
        }
        return 0;
    }
    public Vector getPreference (){
        return Preference;
        /*for(int i =0 ; i<Preference.size();i++){
            if(Preference.elementAt(i).equals(this)){
                return Preference.elementAt(i);
            }
        }
        return null;*/
    }

    public String getPiece (){
        for(int i =0 ; i<ListeObjet.size();i++){
            if(ListeObjet.elementAt(i).equals(this)){
                return ListeObjet.elementAt(i).PieceMaison;
            }
        }
        return null;
    }
    public Vector consoJour (){
        return this.ConsoJour;
    }
    public Vector consoMois (){
        return this.ConsoMois;
    }
    public Vector consoSemaine (){
        return this.ConsoSemaine;
    }


}