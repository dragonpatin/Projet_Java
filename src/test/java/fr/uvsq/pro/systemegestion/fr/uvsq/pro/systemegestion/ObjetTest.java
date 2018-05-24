package fr.uvsq.pro.systemegestion;
import org.junit.Test;

import java.util.Calendar;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Classe de test de la classe Objet
 */
public class ObjetTest {
    Objet test = new Objet("lampe","salon",15,"AA:BB:89",3);
    CapteurTemperature capteurtest = new CapteurTemperature(20,"capteur","salon","HG:67:LM");
    Lampe lampetest = new Lampe(30,"lampe","chambre","GG:QA:34",1,30);
    PriseElectrique prisetest = new PriseElectrique(false,"prise","salon","HJ:SD:40");
    Radiateur radiateurtest = new Radiateur(7,22,"radiateur","chambre","AA:JH:34",2,40);

    /**
     * Test de création d'un objet
     */
    @Test
    public void TestCreationObjet(){
        if(test.getNom() != "lampe" || test.getPiece() != "salon" || test.getadresseMAC() != "AA:BB:89" || test.getPriorite() != 3 || test.getDuree_utilisation() != 0 || test.getSwitch() != false){
            fail("Erreur dans la création de l'objet");
        }
        System.out.println("Test de création de l'objet : OK");
    }

    @Test
    public void TestCreationRadiateur(){
        if(radiateurtest.getSwitch() == false){
            radiateurtest.AllumerEteindre();
        }
        if(radiateurtest.getNom() != "radiateur" || radiateurtest.getTemperature() != 22 || radiateurtest.getThermostat() != 7 || radiateurtest.getPiece() != "chambre" || radiateurtest.getadresseMAC() != "AA:JH:34" || radiateurtest.getPriorite() != 2){
            fail("Erreur dans la création du radiateur");
        }
        System.out.println("Test de création du radiateur : OK");
    }

    /**
     * Test de création d'une prise
     */
    @Test
    public void TestCreationPrise(){
        if(prisetest.getNom() != "prise" || prisetest.getPiece() != "salon" || prisetest.getSwitch() != false || prisetest.getadresseMAC() != "HJ:SD:40"){
            fail("Erreur dans la création de la prise");
        }
        System.out.println("Test de création de la prise : OK");
    }

    /**
     * Test de création d'un capteur
     */
    @Test
    public void TestCreationCapteurTemperature(){
        if(capteurtest.getSwitch() == false){
            capteurtest.AllumerEteindre();
        }
        if(capteurtest.getTemperature() != 20 || capteurtest.getNom() != "capteur" || capteurtest.getPiece() != "salon" || capteurtest.getadresseMAC() != "HG:67:LM" || capteurtest.getSwitch() != true){
            fail("Erreur dans la création du capteur");
        }
        System.out.println("Test de création du capteur : OK");
    }

    /**
     * Test de création d'une lampe
     */
    @Test
    public void TestCreationLampe(){
        if(lampetest.getSwitch() == false){
            lampetest.AllumerEteindre();
        }
        if(lampetest.getLuminosite() != 30 || lampetest.getNom() != "lampe" || lampetest.getPiece() != "chambre" || lampetest.getadresseMAC() != "GG:QA:34" || lampetest.getPriorite() != 1 || lampetest.getSwitch() != true){
            fail("Erreur dans la création de la lampe");
        }
        System.out.println("Test de création de la lampe : OK");
    }

    /**
     * Test d'allumage/extinction d'un objet
     */
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

    /**
     * Test du calcul de la durée d'utilisation d'un objet
     */
    @Test
    public void TestDurée_utilisation(){
        if(test.getSwitch() == false){
            test.AllumerEteindre();
        }
        else{
            test.AllumerEteindre();
            test.AllumerEteindre();
        }
        System.out.println("Test durée d'utilisation en cours..");

        try{
            sleep(60000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        test.AllumerEteindre();
        System.out.println("Durée : " + test.getDuree_utilisation());
        if(test.getDuree_utilisation() != 1){
            fail("Erreur de la durée d'utilisation");
        }
        System.out.println("Test de la durée d'utilisation : OK");
    }

    /**
     * Test de modifications des attributs d'un objet
     */
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

    @Test
    public void TestModifRadiateur(){
        radiateurtest.ModifieTemperature(15);
        radiateurtest.ModifieThermostat(2);
        if(radiateurtest.getSwitch() == false){
            radiateurtest.AllumerEteindre();
        }

        if(radiateurtest.getThermostat() != 2 || radiateurtest.getTemperature() != 15){
            fail("Erreur modification radiateur");
        }
        System.out.println("Test de modification du radiateur : OK");
    }
}