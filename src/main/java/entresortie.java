
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;


public class entresortie{

    File NomFichier;
    Vector <Objet> ListeObjet;
    Vector <Objet> ObjetFavoris;
    Vector <String> Preference;
    Vector <Integer> consoJour;
    Vector <Integer> ConsoMois;
    Vector <Integer> ConsoSemaine;


    public entresortie(){
        JFileChooser dialogue = new JFileChooser();
        dialogue.showOpenDialog(null);
        System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
        this.NomFichier = dialogue.getSelectedFile();

        this.ListeObjet = new Vector<Objet>();
        this.ObjetFavoris = new Vector<Objet>();
        this.Preference = new Vector<String>();
        this.consoJour = new Vector<Integer>();
        this.ConsoMois = new Vector<Integer>();
        this.ConsoSemaine = new Vector<Integer>();
    }

    public void lecturefichier(File NomFichier){
        try{
            String str;
            int nombrebloc = 0;
            BufferedReader fichier = new BufferedReader(new FileReader(NomFichier));

            //Création de l'objet de lecture
            while ((str = fichier.readLine()) != null) {
                if(str=="##") nombrebloc++;
                if(nombrebloc==1){

                }
                System.out.println(str);
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
        return null;
    }
    public Vector getConsommation (){
        return null;
    }
    public Vector getPreference (){
        return null;
    }
    public Vector getPiece (){
        return null;
    }
    public Vector consoJour (){
        return null;
    }
    public Vector consoMois (){
        return null;
    }
    public Vector consoSemaine (){
        return null;
    }
}