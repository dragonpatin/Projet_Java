import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;


public class entresortie{

    File NomFichier;
    Vector <Objet> ListeObjet;
    Vector <Objet> ObjetFavoris;
    Vector <preference> Preference;
    Vector <Integer> ConsoJour;
    Vector <Integer> ConsoMois;
    Vector <Integer> ConsoSemaine;


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

    public void lecturefichier(File NomFichier){
        try{
           // System.out.printf("test");
            String str;
            BufferedReader fichier = new BufferedReader(new FileReader(NomFichier));
            Objet obj = new Objet();

            //Création de l'objet de lecture
            while ((str = fichier.readLine()) != null) {
                //System.out.printf("%s\n",str);
               // System.out.printf("%d\n",nombrebloc);
                if(str.equals("#")){
                    System.out.printf("test1");
                    str = fichier.readLine();
                    String NomObjet = str;
                    str = fichier.readLine();
                    String PieceMaison=str;
                    str = fichier.readLine();
                    int Consommation = Integer.parseInt(str);
                    str = fichier.readLine();
                    String AdresseMAC = str;
                    str = fichier.readLine();
                    String favori = str;
                    str = fichier.readLine();
                    int priorite = Integer.parseInt(str);

                    obj = new Objet(NomObjet,PieceMaison,Consommation,AdresseMAC,priorite);
                    ListeObjet.addElement(obj);

                    if(favori=="true"){
                        ObjetFavoris.addElement(obj);
                    }
                }

                else if (str.equals("+")){
                    System.out.printf("test2");
                    str = fichier.readLine();
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

                else if(str.equals("-")){
                    System.out.printf("test3");
                    str = fichier.readLine();
                    int consojour = Integer.parseInt(str);
                    ConsoJour.addElement(consojour);
                }

                else if(str.equals("%")){
                    System.out.printf("test4");
                    str = fichier.readLine();
                    int consosemaine = Integer.parseInt(str);
                    ConsoSemaine.addElement(consosemaine);
                }

                else if(str.equals("?")){
                    System.out.printf("test5");
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

    public void ecriture ( File NomFichier){
        String test,fina= "";
        test = enregistre(NomFichier);
        int acc,acc2;
       // System.out.printf("test = %s\n",test);
        for(int i =0;i<this.ListeObjet.size();i++) {
            acc = test.indexOf("#");
            acc2 = test.indexOf("+");
            String before = test.substring(0, acc + 1);
            String after = test.substring(acc2);
            String code = ListeObjet.elementAt(i).NomObjet +"\n" + ListeObjet.elementAt(i).PieceMaison + "\n"
                    + ListeObjet.elementAt(i).Consommation + "\n" + ListeObjet.elementAt(i).AdresseMAC + "\n" + "false"
                    + ListeObjet.elementAt(i).Priorite;
            fina = before + "\n" + code + "\n" + after;

            System.out.printf("finaltext = %s\n",fina);
        }



        for(int i =0;i<this.Preference.size();i++) {
            acc = fina.indexOf("+");
            acc2 = fina.indexOf("-");
            String before = fina.substring(0, acc + 1);
            String after = fina.substring(acc2);
            String code = Preference.elementAt(i).nom +"\n" + Preference.elementAt(i).O.getNom()
                    + "\n" + Preference.elementAt(i).instruction + "\n" + Preference.elementAt(i).heure_debut+"\n"+
                    + Preference.elementAt(i).heure_fin;

            fina = before + "\n" + code + "\n" + after;
            System.out.printf("finaltext = %s\n",fina);
        }

        for(int i =0;i<this.ConsoJour.size();i++) {
            acc = fina.indexOf("-");
            acc2 = fina.indexOf("%");
            String before = fina.substring(0, acc + 1);
            String after = fina.substring(acc2);
            String code = ConsoJour.elementAt(i).toString();
            fina = before + "\n" + code + "\n" + after;
            System.out.printf("finaltext = %s\n",fina);
        }

        for(int i =0;i<this.ConsoSemaine.size();i++) {
            acc = fina.indexOf("%");
            acc2 = fina.indexOf("?");
            String before = fina.substring(0, acc + 1);
            String after = fina.substring(acc2);
            String code = ConsoSemaine.elementAt(i).toString();
            fina = before + "\n" + code + "\n" + after;
            System.out.printf("finaltext = %s\n",fina);
        }

        for(int i =0;i<this.ConsoMois.size();i++) {
            acc = fina.indexOf("?");
            String before = fina.substring(0, acc + 1);
            String code = ConsoMois.elementAt(i).toString();
            fina = before + "\n" + code + "\n";
            System.out.printf("finaltext = %s\n",fina);
        }
    }
    public void modifieObjet ( Vector ListeObjet ){
        // UTILISE LES FONCTIONS DANS OBJET

    }
    public void modifieConsommation ( Vector ListeObjet ){

    }
    public void modifiePreference(Vector Preference){

    }
    public void modifiePiece(Vector ListeObjet){

    }
    public Vector getObjet (){
        return ListeObjet;
    }
    /*public Objet getConsommation (){
        return ListeObjet.elementAt();
    }*/
    public Vector getPreference (){
        return Preference;
    }
    /*public Vector getPiece (){
        return null;
    }*/
    public Vector consoJour (){
        return this.ConsoJour;
    }
    public Vector consoMois (){
        return this.ConsoMois;
    }
    public Vector consoSemaine (){
        return this.ConsoSemaine;
    }

    public static void main(String [] args)
    {
        entresortie E = new entresortie();
        E.lecturefichier(E.NomFichier);
        E.ecriture(E.NomFichier);
    }
}