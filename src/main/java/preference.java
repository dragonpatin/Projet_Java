public class preference {
     String nom ;
     Objet O;
     int instruction; //0 Allumer et 1 eteind
     int heure_debut;
     int heure_fin;


     public preference (String nom, Objet O,int instruction, int hd, int hf){
          this.nom=nom;
          this.O=O;
          this.instruction=instruction;
          this.heure_debut=hd;
          this.heure_fin=hf;
     }
     public void changerNom( String nom){

     }
     public void changerHeuredebut(int hd){

     }
     public void changerHeureFin ( int hf){

     }
     public int getInstruction (){
         return 0;
     }
     public int getHeureFin (){
        return 0;
     }
     public int getHeuredebut (){
        return 0;
     }
     public String getNom(){
        return null;
     }
     public Objet getObjet(){
          return O;
     }
 }
