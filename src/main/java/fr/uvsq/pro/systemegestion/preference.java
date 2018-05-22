package fr.uvsq.pro.systemegestion;

public class preference {

     String nom ;
     Objet O;
     int instruction; //0 Allumer et 1 eteindre
     int heure_debut;
     int heure_fin;

     public preference(){
          this.nom = "";
          this.O = null;
          this.instruction = -1;
          this.heure_debut = -1;
          this.heure_fin = -1;
     }

     public preference(String nom, Objet O, int instruction, int hd, int hf){
          this.nom = nom;
          this.O = O;
          this.instruction = instruction;
          this.heure_debut = hd;
          this.heure_fin = hf;
     }

     public void changerNom(String nom){
         this.nom = nom;
     }

     public void changerHeuredebut(int hd){
         this.heure_debut = hd;
     }

     public void changerHeureFin ( int hf){
         this.heure_fin = hf;
     }

     public int getInstruction (){
         return instruction;
     }

     public int getHeureFin (){
        return heure_fin;
     }

     public int getHeuredebut (){
        return heure_debut;
     }

     public String getNom(){
        return this.nom;
     }

     public Objet getObjet(){
          return O;
     }
 }
