import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import org.junit.jupiter.api.Test;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.*;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;



/**
 * Test de la classe Simulateur
 * Il n'est pas nécessaire de choisir le fichier Le programme le fait de lui même
 */
public class SimulateurTest {
    /**
     * Lire Fichier 1
     * On regarde par la suit que les valeurs change pendant les 5 min.
     */
    @Test
    public void fichierPlein() throws IOException{
        entresortie E = new entresortie();
        E.lecturefichier(new java.io.File("TestSimulateur1"));
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
                sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;

        }
        System.out.println("fichierPlein : Ok");
    }
    /**
     * Lire Fichier 3
     * On regarde par la suit que les valeurs change pendant les 5 min.
     */
    @Test
    public void fichierMinimum(){
        entresortie E = new entresortie();
        E.lecturefichier(new java.io.File("TestSimulateur3"));
        Simulateur S = new Simulateur(E);
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
                sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            i++;
        }
        System.out.println("fichierMinimum : Ok");
    }

    /**
     * Compare les deux températures
     */
    @Test
    public void TestTempérature()throws JAXBException, IOException{
        entresortie E = new entresortie();
        //Obligation de lui donné un fichier
        E.lecturefichier(new java.io.File("TestSimulateur1"));
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
    }
    /**
     * Lire Fichier 1 et compare les noms entre les 2
     */
    @Test
    public void TestObjet(){
        entresortie E = new entresortie();
        E.lecturefichier(new java.io.File("TestSimulateur1"));
        Simulateur S = new Simulateur(E);
        Vector<Objet> os = S.getObjet();
        Vector<Objet> oe = S.getObjet();
        if(os.size()!=oe.size()){
            System.out.println("Erreur du nombre d'objet !");
            exit(0);
        }
        for(int i = 0;i<os.size();i++){
            if((oe.get(i).getSwitch()!=os.get(i).getSwitch())||(oe.get(i).getPiece()!=os.get(i).getPiece())||(oe.get(i).getNom()!=os.get(i).getNom())||(oe.get(i).getPriorite()!=os.get(i).getPriorite())||(oe.get(i).getadresseMAC()!=os.get(i).getadresseMAC())){
                System.out.println("Erreur dans les Objets : Pas les même valeurs !");
                exit(0);
            }
        }
        System.out.println("TestObjet : Ok");
    }
    /**
     * Vérifie que les données soit bonne
     */
    @Test
    public void TestPrix() {
        entresortie E = new entresortie();
        E.lecturefichier(new java.io.File("TestSimulateur1"));
        Simulateur S = new Simulateur(E);
        if(S.getPrixHP()==0.1593){
            System.out.println("Erreur Mauvais Prix en heure de pointe !");
            exit(0);
        }
        if(S.getPrixHC()==0.1252){
            System.out.println("Erreur Mauvaise Prix en heure Creux !");
            exit(0);
        }
        System.out.println("TestPrix : Ok");
    }
    /**
     * Vérifie que les données soit bonne
     */
    @Test
    public void TestExtinctionAutomatique(){
        entresortie E = new entresortie();
        E.lecturefichier(new java.io.File("TestSimulateur1"));
        Simulateur S = new Simulateur(E);
        S.rechercheDateHeure();
        S.recupereObjet();
        System.out.println("Avant Passage Exctinction Automatique");
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
        System.out.println("Aprés Passage Exctinction Automatique");
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