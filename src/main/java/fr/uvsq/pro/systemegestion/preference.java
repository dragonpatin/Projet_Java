package fr.uvsq.pro.systemegestion;

/**
 * Classe préférence
 * Cette classe gère les préférence de l'utilisateur
 */
public class preference {

     /**
      * @param nom
      * @param O Objet
      * @param instruction l'utilisateur peut choisir si l'objet sera allumé ou éteint : 0 pour allumer et 1 pour éteindre
      * @param heure_debut heure de début de l'instruction
      * @param heure_fin heure de fin de l'instruction
      */
     String nom ;
     Objet O;
     int instruction;
     int heure_debut;
     int heure_fin;

     /**
      * Constructeur par défaut de la classe
      */
     public preference(){
          this.nom = "";
          this.O = null;
          this.instruction = -1;
          this.heure_debut = -1;
          this.heure_fin = -1;
     }

     /**
      * Second constructeur de la classe
      * @param nom le nom
      * @param O l'objet
      * @param instruction l'instruction
      * @param hd heure de début
      * @param hf heure de fin
      */
     public preference(String nom, Objet O, int instruction, int hd, int hf){
          this.nom = nom;
          this.O = O;
          this.instruction = instruction;
          this.heure_debut = hd;
          this.heure_fin = hf;
     }

     /**
      * Méthode pour changer le nom
      * @param nom le nouveau nom
      */
     public void changerNom(String nom){
         this.nom = nom;
     }

     /**
      * Méthode pour changer l'heure de début
      * @param hd la nouvelle heure de début
      */
     public void changerHeuredebut(int hd){
         this.heure_debut = hd;
     }

     /**
      * Méthode pour changer l'heure de fin
      * @param hf la nouvelle heure de fin
      */
     public void changerHeureFin ( int hf){
         this.heure_fin = hf;
     }

     /**
      * @return retourne l'instruction
      */
     public int getInstruction (){
         return instruction;
     }

     /**
      * @return retourne l'heure de fin
      */
     public int getHeureFin (){
        return heure_fin;
     }

     /**
      * @return retourne l'heure de début
      */
     public int getHeuredebut (){
        return heure_debut;
     }

     /**
      * @return retourne le nom
      */
     public String getNom(){
        return this.nom;
     }

     /**
      * @return retourne l'objet
      */
     public Objet getObjet(){
          return O;
     }
 }
