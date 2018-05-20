package fr.uvsq.pro.systemegestion;
import org.junit.Test;
import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class ObjetTest {
    Objet test = new Objet("lampe","salon",15,"AA:BB:89",3);

    @Test
    public void TestCreationObjet(){
        if(test.getNom() != "lampe" || test.getPiece() != "salon" || test.getadresseMAC() != "AA:BB:89" || test.getPriorite() != 3 || test.getDuree_utilisation() != 0 || test.getSwitch() != false){
            fail("Erreur dans la création de l'objet");
        }
        System.out.println("Test de création de l'objet : OK");
    }

    @Test
    public void TestSwitch(){
        if(test.getSwitch() == true){
            test.AllumerEteindre();
            if(test.getSwitch() != false){
                fail("L'objet ne s'est pas éteint");
            }
        }
        else{
            test.AllumerEteindre();
            if(test.getSwitch() != true){
                fail("L'objet ne s'est pas allumé");
            }
        }
        System.out.println("Test allumage/extinction de l'objet : OK");
    }

    @Test
    public void TestDurée_utilisation(){
        if(test.getSwitch() == false){
            test.AllumerEteindre();
        }
        else{
            test.AllumerEteindre();
            test.AllumerEteindre();
        }

        try{
            sleep(60000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        test.AllumerEteindre();
        if(test.getDuree_utilisation() != 1){
            fail("Erreur de la durée d'utilisation");
        }
        System.out.println("Test de la durée d'utilisation : OK");
    }

    @Test
    public void TestModifObjet(){
        test.ModifieNomObjet("radiateur");
        test.ModifiePieceMaison("chambre");
        test.ModifieAdresseMAC("BB:KO:12");
        test.ModifiePriorite(1);

        if(test.getNom() != "radiateur" || test.getPiece() != "chambre" || test.getadresseMAC() != "BB:KO:12" || test.getPriorite() != 1){
            fail("Erreur modification objet");
        }
        System.out.println("Test de modification de l'objet : OK");
    }
}