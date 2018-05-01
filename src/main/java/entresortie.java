
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
            String str;
            int nombrebloc = 0;
            BufferedReader fichier = new BufferedReader(new FileReader(NomFichier));
            Objet obj = new Objet();

            //Création de l'objet de lecture
            while ((str = fichier.readLine()) != null) {
                if(str=="##") nombrebloc++;
                if(nombrebloc==1){
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

                    obj = new Objet(NomObjet,PieceMaison,Consommation,AdresseMAC);
                    ListeObjet.addElement(obj);

                    if(favori=="true"){
                        ObjetFavoris.addElement(obj);
                    }
                }

                else if (nombrebloc==2){
                    str = fichier.readLine();
                    String Nompreference = str;
                    str = fichier.readLine();
                    for(int j=0; j<ListeObjet.size();j++){
                        if(ListeObjet.elementAt(j).getNom()==str){
                            obj = ListeObjet.elementAt(j);
                        }
                    }
                    str = fichier.readLine();
                    int instruction = Integer.parseInt(str);
                    str = fichier.readLine();
                    int heure_debut = Integer.parseInt(str);
                    str = fichier.readLine();
                    int heure_fin = Integer.parseInt(str);

                    preference P = new preference(Nompreference,obj,heure_debut,heure_fin);
                    Preference.addElement(P);
                }

                else if(nombrebloc==3){
                    str = fichier.readLine();
                    int consojour = Integer.parseInt(str);
                    ConsoJour.addElement(consojour);
                }

                else if(nombrebloc==4){
                    str = fichier.readLine();
                    int consosemaine = Integer.parseInt(str);
                    ConsoJour.addElement(consosemaine);
                }

                else if(nombrebloc==5){
                    str = fichier.readLine();
                    int consomois = Integer.parseInt(str);
                    ConsoJour.addElement(consomois);
                }
            }
            fichier.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ecriture ( File NomFichier){

    }
    public void modifieObjet ( Vector ListeObjet ){

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
    public Objet getConsommation (){
        return null;
    }
    public Vector getPreference (){
        return null;
    }
    public Vector getPiece (){
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