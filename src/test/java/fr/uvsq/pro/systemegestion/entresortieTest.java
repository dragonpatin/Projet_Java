package fr.uvsq.pro.systemegestion;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.fail;

/**
 * Fonction de test de la classe entree sortie
 * Il n'a pas d'effet sur le programme en lui même, il est juste ici pour éviter que le programme est des erreurs quelconques.
 * Donc il ne faut pas modifier les fichiers suivant sous peinne de test fail : entreesortie et entresortie2
 */
public class entresortieTest {

    private entresortie testentre;


    @Before
    public void Initialisation(){
        File file  = new java.io.File("testentreesortie");
        testentre = new entresortie(true);
        testentre.lecturefichier(file);

    }

    /**
     * Il va lire le fichier entresortie
     * Il va lire le fichier et vérifier ce qu'il a écrit dans les vecteurs
     */
    @Test
    public void testlecture(){
        System.out.println(testentre.getObjet().elementAt(0).getNom());
        if(!(testentre.getObjet().elementAt(0).getNom().equals("lampe") || testentre.getObjet().elementAt(0).getadresseMAC().equals("5462745627") || testentre.getObjet().elementAt(0).getPiece().equals("chambre"))
                || testentre.getObjet().elementAt(0).getConsommation() != 0 || testentre.getObjet().elementAt(0).getPriorite() != 3
                    ){
            fail("Erreur l'objet lu n'est pas le bon");
        }
        if(!(testentre.getObjet().elementAt(1).getNom().equals("radiateur") || testentre.getObjet().elementAt(1).getadresseMAC().equals("5462745626") || testentre.getObjet().elementAt(1).getPiece().equals("salon"))
                || testentre.getObjet().elementAt(1).getConsommation() != 0 || testentre.getObjet().elementAt(1).getPriorite() != 3
                ){
            fail("Erreur l'objet lu n'est pas le bon");
        }
        System.out.println("Test de lecture  : OK");
    }

    /**
     * Il va écrire dans le fichier entresortie2
     * Il va écrire de tel façon que lors qu'il va le relire, que le vecteur possède ce qu'il a écrit
     */
    @Test
    public void testecriture(){
        File file  = new java.io.File("testentreesortie2");
        //System.out.println(file);
        Objet E = new Objet("frigo","salon",15,"AA:BB:89",3);
        testentre.getObjet().addElement(E);
        testentre.ecriture(file);
        testentre.lecturefichier(file);
        System.out.println(testentre.getObjet().elementAt(2).getNom());
        if(!(testentre.getObjet().elementAt(2).getNom().equals("frigo") || testentre.getObjet().elementAt(2).getadresseMAC().equals("AA:BB:89") || testentre.getObjet().elementAt(2).getPiece().equals("salon"))
                || testentre.getObjet().elementAt(2).getConsommation() != 0 || testentre.getObjet().elementAt(2).getPriorite() != 3
                ){
            fail("Erreur l'objet lu n'est pas le bon, l'écritue a échoué");
        }
        System.out.println("Test de écriture  : OK");
    }

}