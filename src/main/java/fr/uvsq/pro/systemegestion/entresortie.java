/**
 * Java source Entrée Sortie
 */
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
        JFileChooser dialogue = new JFileChooser();
        dialogue.showOpenDialog(null);
        System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
        this.NomFichier = dialogue.getSelectedFile();

        this.ListeObjet = new Vector<Objet>();
        this.ObjetFavoris = new Vector<Objet>();
        this.Preference = new Vector<preference>();
        this.ConsoJour = new Vector<Integer>();
        this.ConsoMois = new Vector<Integer>();
        this.ConsoSemaine = new Vector<Integer>();
        }

    /**
     * C'est le troisième constructeur de la classe entresortie.
     * Celui ci est utilisé uniquement pour les tests.
     * Il initialise toute les variables sauf NomFichier qui sera transmis plus tard par les tests
     */
    public entresortie(boolean b){
        this.ListeObjet = new Vector<Objet>();
        this.ObjetFavoris = new Vector<Objet>();
        this.Preference = new Vector<preference>();
        this.ConsoJour = new Vector<Integer>();
        this.ConsoMois = new Vector<Integer>();
        this.ConsoSemaine = new Vector<Integer>();
    }

    /**
     * Permet d'inverser les valeurs des consommation pour pouvoir les écrires.
     */
    public Vector<Integer> inversion_Objet(Vector<Integer> conso ){
        Vector<Integer> tmp = new Vector<Integer>();
        for(int i = conso.size()-1;i >= 0;i--){
            tmp.add(conso.get(i));
        }
        return tmp;
    }
    /**
     * Elle permet de lire le fichier choisi au préalable
     * @param NomFichier Elle prend en paramètre le fichier en question
     * Elle va lire le fichier d'une façon bien précise, si le fichier a été modifier ou corrompu de manière délibéré
     * ou accidentellement alors il ne pourra plus le lire et une erreur apparaitra.
     */


    public void lecturefichier(File NomFichier){
        if( this.NomFichier != null) {
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
        }}
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
     * en question.
     * Attention elle l'écrit d'une certaine façon pour que la fonction de lecture puisse la lire.
     * @param NomFichier est le fichier où l'on veut écrire
     */
    public void ecriture ( File NomFichier){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(NomFichier));
            writer.write("#"+"\n"+"+"+"\n"+"-"+"\n"+"%"+"\n"+"?");
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        String test,fina= "";

        int acc;

        for(int i =0;i<this.ListeObjet.size();i++) {
                test = enregistre(NomFichier);
                if(i==this.ListeObjet.size()-1){
                    acc = test.indexOf("#");
                    String before = test.substring(0, acc + 1);
                    String after = test.substring(acc + 2);
                    String code = ListeObjet.elementAt(i).NomObjet + "\n" + ListeObjet.elementAt(i).PieceMaison + "\n"
                            + ListeObjet.elementAt(i).Consommation + "\n" + ListeObjet.elementAt(i).AdresseMAC + "\n"
                            + ListeObjet.elementAt(i).Priorite;
                    fina = before + "\n" + code + "\n" + after;
                }
                else {
                    acc = test.indexOf("#");
                    String before = test.substring(0, acc + 1);
                    String after = test.substring(acc + 2);
                    String code = "#" + "\n" + ListeObjet.elementAt(i).NomObjet + "\n" + ListeObjet.elementAt(i).PieceMaison + "\n"
                            + ListeObjet.elementAt(i).Consommation + "\n" + ListeObjet.elementAt(i).AdresseMAC + "\n"
                            + ListeObjet.elementAt(i).Priorite;
                    fina = before + "\n" + code + "\n" + after;
                }
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
            test = enregistre(NomFichier);
            if(i==this.Preference.size()-1) {
                acc = test.indexOf("+");
                String before = fina.substring(0, acc + 1);
                String after = fina.substring(acc + 2);
                String code = Preference.elementAt(i).nom + "\n" + Preference.elementAt(i).O.getNom()
                        + "\n" + Preference.elementAt(i).instruction + "\n" + Preference.elementAt(i).heure_debut + "\n" +
                        +Preference.elementAt(i).heure_fin;

                fina = before + "\n" + code + "\n" + after;
            }
            else{
                acc = test.indexOf("+");
                String before = fina.substring(0, acc + 1);
                String after = fina.substring(acc + 2);
                String code = "+" + "\n" +Preference.elementAt(i).nom + "\n" + Preference.elementAt(i).O.getNom()
                        + "\n" + Preference.elementAt(i).instruction + "\n" + Preference.elementAt(i).heure_debut + "\n" +
                        +Preference.elementAt(i).heure_fin;

                fina = before + "\n" + code + "\n" + after;
            }
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
        System.out.println(ConsoJour);
        Vector<Integer> tmp1 = inversion_Objet(ConsoJour);
        System.out.println(tmp1);
        for(int i =0;i<tmp1.size();i++) {
            test = enregistre(NomFichier);
            if(i==tmp1.size()-1) {
                acc = test.indexOf("-");
                String before = fina.substring(0, acc + 1);
                String after = fina.substring(acc + 2);
                String code = tmp1.elementAt(i).toString();
                fina = before + "\n" + code + "\n" + after;
            }
            else{
                acc = test.indexOf("-");
                String before = fina.substring(0, acc + 1);
                String after = fina.substring(acc + 2);
                String code = "-" + "\n" + tmp1.elementAt(i).toString();
                fina = before + "\n" + code + "\n" + after;
            }
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
        Vector<Integer> tmp2 = inversion_Objet(ConsoSemaine);
        for(int i =0;i<tmp2.size();i++) {
            test = enregistre(NomFichier);
            if (i == tmp2.size() - 1) {
                acc = test.indexOf("%");
                String before = fina.substring(0, acc + 1);
                String after = fina.substring(acc + 2);
                String code = tmp2.elementAt(i).toString();
                fina = before + "\n" + code + "\n" + after;
            }
            else {
                acc = test.indexOf("%");
                String before = fina.substring(0, acc + 1);
                String after = fina.substring(acc + 2);
                String code = "%" + "\n" + tmp2.elementAt(i).toString();
                fina = before + "\n" + code + "\n" + after;
            }
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
        Vector<Integer> tmp3 = inversion_Objet(ConsoMois);
        for(int i =0;i<tmp3.size();i++) {
            test = enregistre(NomFichier);
            if(i==tmp3.size()-1) {
                acc = test.indexOf("?");
                String before = fina.substring(0, acc + 1);
                String code = tmp3.elementAt(i).toString();
                fina = before + "\n" + code + "\n";
            }
            else{
                acc = test.indexOf("?");
                String before = fina.substring(0, acc + 1);
                String code = "?" + "\n" + tmp3.elementAt(i).toString();
                fina = before + "\n" + code + "\n";
            }
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

    /**
     * La modification d'un objet peut intervenir lors de l'utilisation de l'interface avec l'utilisateur. Par exemple on veut modifier si l'on
     * veut qu'il soit éteint ou allumé.
     * @param obj C'est l'objet que l'on à modifié.
     */

    public void modifieObjet (Objet obj ){
        for(int i =0 ; i<ListeObjet.size();i++){
            if(ListeObjet.elementAt(i).equals(obj)){
                ListeObjet.removeElementAt(i);
                ListeObjet.addElement(obj);
                ecriture(this.NomFichier);
            }
        }
    }

    public void modifieObjet (Vector<Objet> obj ){
        ListeObjet = obj;
    }

    /**
     * Cette fonction permet de mettre à jour les préférences d'un objet en particulier.
     * @param pref c'est l'objet préférence que l'on aura créer ou modifier au préalable
     */
    public void modifiePreference(preference pref){
        for(int i =0 ; i<Preference.size();i++){
            if(Preference.elementAt(i).equals(pref)){
                Preference.removeElementAt(i);
                Preference.addElement(pref);
                ecriture(this.NomFichier);
            }
        }
    }

    /**
     *Elle va return la liste des objets stockés
     * @return
     */
    public Vector<Objet> getObjet (){
        return ListeObjet;
    }

    /**
     * Elle va return la consommation de l'objet en question.
     * @return
     */
    public int getConsommation (){
        for(int i =0 ; i<ListeObjet.size();i++){
            if(ListeObjet.elementAt(i).equals(this)){
                return ListeObjet.elementAt(i).Consommation;
            }
        }
        return 0;
    }

    /**
     * Elle va return la la liste des préférences stockés dans le vecteur préférence
     * @return
     */
    public Vector<preference> getPreference (){
        return Preference;
    }

    /**
     * Elle va return la pièce où se trouve l'objet stocké
     * @return
     */
    public String getPiece (){
        for(int i =0 ; i<ListeObjet.size();i++){
            if(ListeObjet.elementAt(i).equals(this)){
                return ListeObjet.elementAt(i).PieceMaison;
            }
        }
        return null;
    }

    /**
     * Elle va return le vecteur consoJour
     * @return
     */
    public Vector<Integer> consoJour (){
        return this.ConsoJour;
    }

    /**
     * Elle va return le vecteur ConsoMois
     * @return
     */
    public Vector<Integer> consoMois (){
        return this.ConsoMois;
    }

    /**
     *Elle va return le vecteur Consosemaine
     * @return
     */
    public Vector<Integer> consoSemaine (){
        return this.ConsoSemaine;
    }


}
