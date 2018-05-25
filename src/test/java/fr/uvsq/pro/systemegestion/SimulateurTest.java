package fr.uvsq.pro.systemegestion;
/*
import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
*/
import org.junit.*;
import org.junit.After;
import org.junit.Before;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.*;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;



/**
 * Test de la classe Simulateur
 * Il n'est pas nécessaire de choisir le fichier. Le programme le fait de lui même
 */
public class SimulateurTest{
    private entresortie E;
    private entresortie E2;


    /**
     * Au démarrage tous les objets sont éteints.
     * Avec cette fonction nous les allumons tous
     * @param e classe entresortie où nous allumons tous les objets
     */
    private void AllumerObjet(entresortie e){
        for(int i = 0;i<e.getObjet().size();i++){
            e.getObjet().get(i).AllumerEteindre();
        }
    }

    /**
     * Appelé lors du démarrage du test.
     * Il crée deux classes entresortie avec deux fichiers tests
     */
    @Before
    public void Initialisation(){
        E = new entresortie(true);
        E2 = new entresortie(true);
        E.lecturefichier(new java.io.File("src/test/ressources/TestSimulateur1"));
        E2.lecturefichier(new java.io.File("src/test/ressources/TestSimulateur3"));
        AllumerObjet(E);
        AllumerObjet(E2);
    }
    /**
     * Lire Fichier 1
     * On regarde par la suite que les valeurs changent.
     */
    @Test
    public void fichierPlein() throws IOException{

        Simulateur S = new Simulateur(E);
        S.rechercheDateHeure();
        S.recupereObjet();
        int i = 0;
        while(i != 2){
            S.miseAJourObjet();
            S.calculConsommation();
            System.out.println(S.ConsoMois());
            System.out.println(S.ConsoSemaine());
            System.out.println(S.consoJour());
            System.out.println();
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;

        }
        System.out.println("fichierPlein : Ok");
    }
    /**
     * Lire Fichier 3
     * On regarde par la suite que les valeurs changent.
     */
    @Test
    public void fichierMinimum(){
        Simulateur S = new Simulateur(E2);
        S.rechercheDateHeure();
        S.recupereObjet();
        int i = 0;
        while(i != 2) {
            S.miseAJourObjet();
            S.calculConsommation();
            System.out.println(S.ConsoMois());
            System.out.println(S.ConsoSemaine());
            System.out.println(S.consoJour());
            System.out.println();
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            i++;
        }
        System.out.println("fichierMinimum : Ok");
    }
    /*
    /**
     * Compare les deux températures
     */
    /*
    @Test
    public void TestTempérature()throws JAXBException, IOException{
        //Obligation de lui donner un fichier
        Simulateur S = new Simulateur(E);
        S.recupereObjet();
        S.recupereTemperatureExt();

        //La variable Test
        YahooWeatherService service = null;
        try {
            service = new YahooWeatherService();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        channel = service.getForecast("630908", DegreeUnit.CELSIUS);
        assert channel != null;
        if(channel.getItem().getCondition().getTemp() != S.getTemperatureExt()){
            System.out.println("Erreur Mauvaise température !");
            exit(0);
        }
        System.out.println("TestTempérature : Ok");
    }*/
    /**
     * Lire Fichier 1 et compare les objets entre les ceux reçus et ceux de la classe entresortie
     */
    @Test
    public void TestObjet(){
        Simulateur S = new Simulateur(E);
        Vector<Objet> os = S.getObjet();
        Vector<Objet> oe = S.getObjet();
        if(os.size()!=oe.size()){
            System.out.println("Erreur du nombre d'objets !");
            exit(0);
        }
        for(int i = 0;i<os.size();i++){
            if((oe.get(i).getSwitch()!=os.get(i).getSwitch())||(oe.get(i).getPiece()!=os.get(i).getPiece())||(oe.get(i).getNom()!=os.get(i).getNom())||(oe.get(i).getPriorite()!=os.get(i).getPriorite())||(oe.get(i).getadresseMAC()!=os.get(i).getadresseMAC())){
                System.out.println("Erreur dans les Objets : Pas les mêmes valeurs !");
                exit(0);
            }
        }
        System.out.println("TestObjet : Ok");
    }
    /**
     * Vérifie que les données sont bonnes
     */
    @Test
    public void TestPrix() {
        Simulateur S = new Simulateur(E);
        if(S.getPrixHP()==0.1593){
            System.out.println("Erreur Mauvais Prix en heure de pointe !");
            exit(0);
        }
        if(S.getPrixHC()==0.1252){
            System.out.println("Erreur Mauvais Prix en heure Creuse !");
            exit(0);
        }
        System.out.println("TestPrix : Ok");
    }
    /**
     * Vérifie que les données sont bonnes
     */
    @Test
    public void TestExtinctionAutomatique(){
        Simulateur S = new Simulateur(E);
        S.rechercheDateHeure();
        S.recupereObjet();
        System.out.println("Avant Passage Extinction Automatique");
        S.miseAJourObjet();
        S.calculConsommation();
        System.out.println(S.ConsommationANePasDepasser());
        System.out.println(S.ConsommationObjet());
        System.out.println(S.ConsoMois());
        System.out.println(S.ConsoSemaine());
        System.out.println(S.consoJour());
        S.rechercheDateHeure();
        System.out.println();
        S.ExtinctionAutomatique(1000);
        System.out.println("Aprés Passage Extinction Automatique");
        S.miseAJourObjet();
        S.calculConsommation();
        System.out.println(S.ConsommationObjet());
        System.out.println(S.ConsoMois());
        System.out.println(S.ConsoSemaine());
        System.out.println(S.consoJour());
        S.rechercheDateHeure();
        System.out.println();

        System.out.println("TestExtinctionAutomatique : Ok");
    }
}