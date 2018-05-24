package fr.uvsq.pro.systemegestion;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.SystemColor;

import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;




public class traitement_donnees{
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
	JFrame frame = new JFrame();
	JPanel graphpanel = new JPanel();
	JPanel gradupanel = new JPanel();
    String TitreGraph;
    int nb_valeur;
    int val_max_ord;

 	
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
 	
    
     public traitement_donnees(String TitreGraph,Vector<Integer> conso,int valeur_max){
    	 this.TitreGraph=TitreGraph+" (Kwh)"; 
    	 this.nb_valeur=conso.size();
    	 this.val_max_ord = valeur_max;
    	 init_affichage();
    	 affiche_graph_conso(conso,val_max_ord);
     }

     /**
      * methode de création et de modification des graphique.
      * la premiere execution initialise les graphique.
      * les execution suivante update les graphique pendant que l'application tourne en arriere plan
      * @param S contient les données de consomation utilisé pour faire les graphiques
      */
     @SuppressWarnings("static-access")
	public void Creer_un_Graphe(Simulateur S)
     {
         
          int conso =0;
          conso = S.consommationTotale();
//          for(i=0;i<S.ConsommationObjet().size();i++)
//          {
//               conso += S.ConsommationObjet().get(i);
//          }

          if(((S.getDate().MINUTE)%5)!=0)
          {
               if(absHeure.size() == 12)
               {
                    absHeure.remove(11);
                    ordHeure.remove(11);
               }
               absHeure.add(0,S.getDate().MINUTE);
               ordHeure.add(0,conso);
               ordJour.add(0);
               ordMois.add(0);
               ordAns.add(0);
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
     
     
     private void init_affichage() {

  		frame.setBounds(100, 100,775,500);
  		frame.setVisible(true);
  		frame.setTitle(this.TitreGraph);
  		frame.getContentPane().setLayout(null);		
  		graphpanel.setBounds(105, 67, 550, 300);
  		frame.getContentPane().add(graphpanel);
  		graphpanel.setLayout(null);	
  		

  		
  		JLabel titregraph = new JLabel(TitreGraph);
  		titregraph.setFont(new Font("Verdana", Font.BOLD,16));
  		titregraph.setHorizontalAlignment(SwingConstants.CENTER);
  		titregraph.setBounds(105, 13, 550, 30);
  		frame.getContentPane().add(titregraph);
  		
  		JPanel gradupanel = new JPanel();
  		gradupanel.setBackground(SystemColor.inactiveCaption);
  		gradupanel.setBounds(105, 370, 550, 30);
  		frame.getContentPane().add(gradupanel);
  		gradupanel.setLayout(null);
  		
  		JLabel consomax = new JLabel(""+val_max_ord);
  		consomax.setBounds(70, 70, 56, 16);
  		frame.getContentPane().add(consomax);
  		
  		JLabel cons2 = new JLabel(""+val_max_ord/2);
  		cons2.setBounds(70, 209, 56, 16);
  		frame.getContentPane().add(cons2);
  		
  		JLabel cons1 = new JLabel(""+((val_max_ord/2)+val_max_ord/4));
  		cons1.setBounds(70, 132, 56, 16);
  		frame.getContentPane().add(cons1);
  		
  		JLabel cons3 = new JLabel(""+val_max_ord/4);
  		cons3.setBounds(70, 284, 56, 16);
  		frame.getContentPane().add(cons3);
  		
  		int x=0;
	   	int w = (graphpanel.getWidth()/nb_valeur)-6; 	
	   	if (nb_valeur<4) w = (graphpanel.getWidth()/4)-6;
  		for(int i = 0; i<nb_valeur;i++) {
  		JLabel lblNewLabel_1 = new JLabel(""+(i+1));
  		lblNewLabel_1.setBounds(x-2, 5,w, 16);
  		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
  		gradupanel.add(lblNewLabel_1);
  		x+=graphpanel.getWidth()/nb_valeur;
  		}
  			
     }

     public void graphe_temps_reel(Simulateur S, int echelle_de_temps){

     }
     
     
     public void aff_canvas_graph(int nb) {
   		int y=0;
  		Canvas canvas_0 = new Canvas();
  		canvas_0.setBackground(Color.GRAY);
  		canvas_0.setBounds(0, 150, 550, 1);
  		graphpanel.add(canvas_0);
  		
  		Canvas canvas_1 = new Canvas();
  		canvas_1.setBackground(Color.GRAY);
  		canvas_1.setEnabled(false);
  		canvas_1.setBounds(0, 75, 550, 1);
  		graphpanel.add(canvas_1);
  		
  		Canvas canvas_2 = new Canvas();
  		canvas_2.setBackground(Color.GRAY);
  		canvas_2.setBounds(0, 225, 550, 1);
  		graphpanel.add(canvas_2);
  		
  		Canvas canvas_3 = new Canvas();
  		canvas_3.setBackground(Color.GRAY);
  		canvas_3.setBounds(0, 1, 550, 1);
  		graphpanel.add(canvas_3);
  		
   		for(int i= 0 ; i < 6; i++) {
   	   		Canvas canvas = new Canvas();
   	   		canvas.setBackground(SystemColor.controlHighlight);
   	   		canvas.setBounds(0, 250-y, 550, 25);
   	   		graphpanel.add(canvas);
   	   		y+=50;
   		}  

     }
     
     public void affiche_graph_conso(Vector<Integer>conso,int pic) {
    	 int x =0,h=0,h_max=290;
    	 int nb=conso.size();
    	 int lg = graphpanel.getWidth();
    	 int w = (lg/nb)-6; 	
    	 if (nb<4) w = (lg/4)-6;
    	 int ecart = w/6;
    	 
    	for(int i= 0 ; i < conso.size(); i++) {
   		h= (conso.elementAt(i)*h_max)/pic;
  		
   		Canvas canvas = new Canvas();
		canvas.setBackground(new Color(100,150,0));
   		canvas.setBounds(x,300-h,w-ecart,h); 		
   		graphpanel.add(canvas);	
   		x+=lg/nb;
   		}
    	aff_canvas_graph(nb);
     }
}


