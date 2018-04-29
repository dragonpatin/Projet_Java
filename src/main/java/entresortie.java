import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JFileChooser;


public class entresortie{

    File NomFichier;
    Vector ListeObjet;
    Vector ObjetFavoris;
    Vector Preference;
    Vector consoJour;
    Vector ConsoMois;
    Vector ConsoSemaine;

    public entresortie(String Nom)
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Sauvegarde.txt")));
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.NomFichier = new File("Sauvegarde.txt");
    }

    public entresortie(){
        JFileChooser dialogue = new JFileChooser();
        dialogue.showOpenDialog(null);
        System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
        this.NomFichier = dialogue.getSelectedFile();
    }

    public void lecturefichier(File NomFichier, String donnees){
        File file = NomFichier;
        FileReader fr;
        String str;
        if(donnees == "Objet"){

        }
        else if(donnees == "ObjetFavori"){

        }
        else if(donnees == "Preference"){

        }
        else if(donnees == "consoJour"){

        }
        else if(donnees == "ConsoMois"){

        }
        else if(donnees == "ConsoSemaine"){

        }
        try{
            //Création de l'objet de lecture
            fr = new FileReader(file);
            str = "";
            int i = 0;
            //Lecture des données
            while((i = fr.read()) != -1)
                str += (char)i;

            //Affichage
            System.out.println(str);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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