package fr.uvsq.pro.systemegestion;
import java.util.Calendar;
import java.util.*;

/**
 * class de gestion de donné
 * crée des graph avec les donné du simulateur
 */
public class traitement_donnees {

     // abscisse et ordonnée d'un graphique contenant la consomation global  sur un Ans
     Vector<Integer> absMois;
     Vector<Integer> ordMois;
     // abscisse et ordonnée d'un graphique contenant la consomation global  sur un Ans
     Vector<Integer> absAns;
     Vector<Integer> ordAns;
     // abscisse et ordonnée d'un graphique contenant la consomation global  sur un Jour
     Vector<Integer> absJour;
     Vector<Integer> ordJour;
     // abscisse et ordonnée d'un graphique contenant la consomation instantané sur une heure
     Vector<Integer> absHeure;
     Vector<Integer> ordHeure;

     /**
      * Constructeur de la slasse  traitement_donnees
      * Initialisation de la classe  traitement_donnees
      */
     public traitement_donnees(){
          this.absMois = new Vector<Integer>();
          this.ordMois = new Vector<Integer>();
          this.absAns = new Vector<Integer>();
          this.ordAns = new Vector<Integer>();
          this.absJour = new Vector<Integer>();
          this.ordJour = new Vector<Integer>();
          this.absHeure = new Vector<Integer>();
          this.ordHeure = new Vector<Integer>();
     }

     /**
      * methode de création et de modification des graphique.
      * la premiere execution initialise les graphique.
      * les execution suivante update les graphique pendant que l'application tourne en arriere plan
      * @param S contient les données de consomation utilisé pour faire les graphiques
      */
     public void Creer_un_Graphe(Simulateur S)
     {
          int i;
          int conso =0;
          conso = S.consommationTotale();
          /*for(i=0;i<S.ConsommationObjet().size();i++)
          {
               conso += S.ConsommationObjet().get(i);
          }*/

          if(((S.getDate().MINUTE)%5)!=0)
          {
               if(absHeure.size() == 12)
               {
                    absHeure.remove(11);
                    ordHeure.remove(11);
               }
               absHeure.add(0,S.getDate().MINUTE);
               ordHeure.add(0,conso);
               ordJour.set(0,ordJour.get(0)+(conso/12));
               ordMois.set(0,ordMois.get(0)+(conso/12));
               ordAns.set(0,ordAns.get(0)+(conso/12));
          }
          if((S.getDate().MINUTE)!=0)
          {
               if(absJour.size() == 24)
               {
                    absJour.remove(23);
                    ordJour.remove(23);
               }
               absJour.add(0,S.getDate().HOUR_OF_DAY);
               ordJour.add(0,0);

               if(((S.getDate().HOUR_OF_DAY)==0))
               {
                    if(absMois.size() == 31)
                    {
                         absMois.remove(30);
                         ordMois.remove(30);
                    }
                    absMois.add(0,S.getDate().DAY_OF_MONTH);
                    ordMois.add(0,0);

                    if(((S.getDate().MONTH))!=0)
                    {
                         if(absAns.size() == 12)
                         {
                              absAns.remove(11);
                              ordAns.remove(11);
                         }
                         absAns.add(0,S.getDate().MONTH);
                         ordAns.add(0,0);
                    }
               }
          }
     }

     /**
      * retour l'abscisse du graphique donnant l'évolution de la consomation sur un mois
      * @return tableau de l'abscisse d'un graphique
      */
     public Vector<Integer> get_absMois()
     {
          return absMois;
     }

     /**
      * retour l'ordonnée du graphique donnant l'évolution de la consomation sur un mois
      * la donné est en kW
      * @return tableau de l'ordonnée d'un graphique
      */
     public Vector<Integer> get_ordMois()
     {
          return ordMois;
     }

     /**
      * retour l'abscisse du graphique donnant l'évolution de la consomation sur un an
      * @return tableau de l'abscisse d'un graphique
      */
     public Vector<Integer> get_absAns()
     {
          return absAns;
     }

     /**
      * retour l'ordonnée du graphique donnant l'évolution de la consomation sur un an
      * la donné est en kW
      * @return tableau de l'ordonnée d'un graphique
      */
     public Vector<Integer> get_ordAns()
     {
          return ordAns;
     }

     /**
      * retour l'abscisse du graphique donnant l'évolution de la consomation sur un jour
      * @return tableau de l'abscisse d'un graphique
      */
     public Vector<Integer> get_absJour()
     {
          return absJour;
     }

     /**
      * retour l'ordonnée du graphique donnant l'évolution de la consomation sur un Jour
      * la donné est en kW
      * @return tableau de l'ordonnée d'un graphique
      */
     public Vector<Integer> get_ordJour()
     {
          return ordJour;
     }

     /**
      * retour l'abscisse du graphique donnant l'évolution de la consomation sur une heure
      * @return tableau de l'abscisse d'un graphique
      */
     public Vector<Integer> get_absHeure()
     {
          return absHeure;
     }

     /**
      * retour l'ordonnée du graphique donnant l'évolution de la consomation instantané sur une heure
      * la donné est en kW
      * @return tableau de l'ordonnée d'un graphique
      */
     public Vector<Integer> get_ordHeure()
     {
          return ordHeure;
     }


}

