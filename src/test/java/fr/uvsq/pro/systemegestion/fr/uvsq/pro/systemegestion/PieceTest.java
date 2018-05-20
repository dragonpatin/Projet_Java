package fr.uvsq.pro.systemegestion;
import org.junit.Before;
import org.junit.Test;
import java.util.Vector;
import static org.junit.Assert.*;

public class PieceTest {
    private entresortie E;
    Piece piecetest = new Piece("Salon",0);

    @Before
    public void Initialisation(){
        E = new entresortie(true);
        E.lecturefichier(new java.io.File("TestPiece"));
    }

    @Test
    public void TestCreationPiece(){
        if(piecetest.getNom() != "Salon" || piecetest.getEtage() != 0 || piecetest.getNombreObjet() != 0 || piecetest.getConsoPiece() != 0){
            fail("Erreur dans la création de la pièce");
        }
        System.out.println("Test de création de la pièce : OK");
    }

    @Test
    public void TestAjoutObjet(){
        Vector<Objet> O = new Vector<Objet>();
        O = E.getObjet();
        int i = 0;
        for(i=0; i<O.size(); i++){
            piecetest.ajoutObjet(O.get(i));
        }
        Vector<Objet> Obj = new Vector<Objet>();
        Obj = piecetest.getObjetPiece();
        if(!Obj.firstElement().getNom().equals("radiateur10") || !Obj.firstElement().getPiece().equals("chambre1") || !Obj.firstElement().getadresseMAC().equals("90::76::6B") && piecetest.getNombreObjet() != 2){
            fail("Erreur lors de l'ajout d'un objet");
        }
        System.out.println("Test d'ajout d'un objet : OK");
    }

    @Test
    public void TestSuppressionObjet(){
        Vector<Objet> O = new Vector<Objet>();
        O = E.getObjet();
        int i = 0;
        for(i=0; i<O.size(); i++){
            piecetest.ajoutObjet(O.get(i));
        }
        Vector<Objet> Obj = new Vector<Objet>();
        Obj = piecetest.getObjetPiece();

        piecetest.SupprimerObjet(Obj.firstElement());
        if(Obj.firstElement().getNom().equals("radiateur10") || Obj.firstElement().getPiece().equals("chambre1") || Obj.firstElement().getadresseMAC().equals("90::76::6B") && piecetest.getNombreObjet() == 2){
            fail("Erreur lors de la suppression d'un objet");
        }
        System.out.println("Test de suppression d'un objet : OK");
    }
}