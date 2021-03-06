/**
 * Java source Interface
 */
package fr.uvsq.pro.systemegestion;
import java.lang.Integer;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.Timer;

/**
 * Classe Interface :
 * Classe permettant l'afichage de l'interface graphique et le simulateur
 */

public class Interface implements ActionListener {
	private JFrame frame;
	private JPanel piecepanel = new JPanel();
	private JPanel piececontent = new JPanel();
	private JPanel menupanel = new JPanel();
	private JPanel objetpanel = new JPanel();
	private JPanel objetcontent = new JPanel();
	private JPanel infobjcontent = new JPanel();
	private JPanel infobjpanel = new JPanel();
	private JPanel listobjpanel = new JPanel();
	private JPanel favorispanel = new JPanel();
	private JPanel simupanel = new JPanel();
	private Vector<JButton> btpiece = new Vector<JButton>();
	private Vector<JButton> btobjet = new Vector<JButton>();	
	private Vector<Piece> piece = new Vector<Piece>(); 
	private Piece pieceactuelle;	
	private Objet objetactuel = new Objet();
	private entresortie donnee;
	private String panelprec="";
	private String panelactu="Menu";
	//----------------------------------------------
	//----------------------------------------------
	//----------------------------------------------
	//Création d'un seule Simulateur car plusieur engendre un conflit
	Simulateur s;
	public static void main(String[] args){					
					Interface window = new Interface();
					window.frame.setVisible(true);										
	}
	
	/**
     * Constructeur de la classe Interface
     * Permet de lancer l'interface
     */
	public Interface(){
		initialiseUI();
	}

	/**
	 * Permet l'affichage de tous les boutons 
	 */
	 
	private void initialiseUI(){
		frame = new JFrame();
		frame.setBounds(100, 100,600,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnaddPref = new JButton("Ajouter preference ");
        btnaddPref.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ajout_preference(objetactuel);
                infobjcontent.revalidate();
                infobjcontent.repaint();
                affiche_infos_objet(objetactuel);
            }
        });
        btnaddPref.setBounds(320, 0, 180, 25);
        infobjpanel.add(btnaddPref);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setIcon(new ImageIcon("src/main/resources/retour.png"));
		btnRetour.setBounds(460, 703, 122, 28);
		frame.getContentPane().add(btnRetour);
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( piecepanel.isVisible() ) {
					piecepanel.setVisible(false);
					menupanel.setVisible(true);
				}
				if( simupanel.isVisible() ) {
					simupanel.setVisible(false);
					menupanel.setVisible(true);

				}
				if(favorispanel.isVisible()) {
					favorispanel.setVisible(false);
					menupanel.setVisible(true);
				}
				if( listobjpanel.isVisible() ) {
					listobjpanel.setVisible(false);
					menupanel.setVisible(true);
				}
				if( objetpanel.isVisible() ) {
					piecepanel.setVisible(true);
					objetpanel.setVisible(false);
				}
				if( infobjpanel.isVisible() && !panelprec.equals("favoris") && !panelprec.equals("listobj") ) {
					infobjpanel.setVisible(false);
					objetpanel.setVisible(true);
				}
				if( panelprec.equals("favoris") ) {
					panelprec="objfavoris";
					panelactu="favoris";
					favorispanel.setVisible(true);
					affiche_objet(donnee.ObjetFavoris,favorispanel);
					infobjpanel.setVisible(false);
				}
				if( panelprec.equals("listobj") ) {
					panelprec="objlistobj";
					panelactu="listobj";
					listobjpanel.setVisible(true);
					affiche_objet(donnee.getObjet(),listobjpanel);
					infobjpanel.setVisible(false);	
				}			
			}});
		
		
		menupanel.setBounds(0, 0, 582, 697);
		frame.getContentPane().add(menupanel);
		menupanel.setLayout(null);
		
		JButton PIECE = new JButton("Afficher pièces");
		PIECE.setHorizontalAlignment(SwingConstants.LEFT);
		PIECE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piecepanel.setVisible(true);
				menupanel.setVisible(false);
				objetpanel.setVisible(false);
				simupanel.setVisible(false);
				panelactu="";
				if(donnee !=null )affiche_piece();
				else {JOptionPane.showMessageDialog(piecepanel, "Liste des pièces vide", "Information", JOptionPane.WARNING_MESSAGE);
				menupanel.setVisible(true);
				piecepanel.setVisible(false);
				;}}}				
		); 
		
		PIECE.setBounds(12, 39, 195, 25);
		menupanel.add(PIECE);
		
		JLabel lblMenus = new JLabel("Menu");
		lblMenus.setBounds(12, 12, 70, 15);
		menupanel.add(lblMenus);
		
		JButton btsimu = new JButton("Lancer le simulateur");
		btsimu.setFont(new Font("Dialog", Font.BOLD, 11));
		btsimu.setHorizontalAlignment(SwingConstants.LEFT);
		btsimu.setIcon(new ImageIcon("src/main/resources/play-256.png"));
		btsimu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piecepanel.setVisible(false);
				objetpanel.setVisible(false);			
				if(donnee !=null ) {
				menupanel.setVisible(false);
				simupanel.setVisible(true);
				lancer_simulateur();
				affiche_simulateur();}
				else 
				JOptionPane.showMessageDialog(simupanel, "Impossible de lancer le simulateur", "Information", JOptionPane.WARNING_MESSAGE);
			}
		});
		btsimu.setBounds(12, 115, 195, 25);
		menupanel.add(btsimu);
		
		JButton btfav = new JButton("Objets favoris");
		btfav.setHorizontalAlignment(SwingConstants.LEFT);
		btfav.setIcon(new ImageIcon("src/main/resources/fa1v.png"));
		btfav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			menupanel.setVisible(false);
			favorispanel.setVisible(true);
			pieceactuelle=null;
			panelactu="favoris";
			if(donnee !=null )affiche_objet(donnee.ObjetFavoris,favorispanel);
			else {JOptionPane.showMessageDialog(favorispanel, "Aucun Favoris", "Information", JOptionPane.WARNING_MESSAGE);
			menupanel.setVisible(true);
			listobjpanel.setVisible(false);}}
		});
		btfav.setBounds(12, 77, 195, 25);
		menupanel.add(btfav);
		
		JButton btlistobj = new JButton("Liste objets ");
		btlistobj.setHorizontalAlignment(SwingConstants.LEFT);
		btlistobj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menupanel.setVisible(false);
				listobjpanel.setVisible(true);
				pieceactuelle=null;
				panelactu="listobj";
				if( donnee != null  )affiche_objet(donnee.getObjet(),listobjpanel);
				else {JOptionPane.showMessageDialog(listobjpanel, "Liste Objet vide", "Information", JOptionPane.WARNING_MESSAGE);
				menupanel.setVisible(true);
				listobjpanel.setVisible(false);
				}
			}
		});
		btlistobj.setBounds(12, 153, 195, 25);
		menupanel.add(btlistobj);
		
		piecepanel.setBounds(12, 0, 570, 697);
		frame.getContentPane().add(piecepanel);
		piecepanel.setLayout(null);
		
		
		
		JButton btaddpiece = new JButton("Ajouter piece");
		btaddpiece.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				ajout_piece();
				piecepanel.revalidate();
				piecepanel.repaint();
				affiche_piece();
				System.out.println("ajout pièce");
			}
		});
		btaddpiece.setBounds(388, 0, 182, 25);
		piecepanel.add(btaddpiece);
		piececontent.setBounds(-13, 25, 583, 672);
		piecepanel.add(piececontent);
		piececontent.setLayout(null);		
		piecepanel.setVisible(false);
		infobjpanel.setBounds(0, 0, 594, 697);
		frame.getContentPane().add(infobjpanel);
		infobjpanel.setLayout(null);
		
		
		infobjcontent.setBounds(12, 64, 484, 517);
		infobjpanel.add(infobjcontent);
		infobjcontent.setLayout(null);
		
		JButton btnSupprimerObjet = new JButton("");
		btnSupprimerObjet.setIcon(new ImageIcon("src/main/resources/corb.png"));
		btnSupprimerObjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supprime_objet(objetactuel);
				infobjpanel.setVisible(false);				
				if( pieceactuelle != null ) {
				affiche_objet(pieceactuelle.getObjetPiece(),objetcontent);
				objetpanel.setVisible(true);}
				else {listobjpanel.setVisible(true);
				affiche_objet(donnee.getObjet(),listobjpanel);
				}
			}
		});
		btnSupprimerObjet.setBounds(517, 13, 65, 38);
		infobjpanel.add(btnSupprimerObjet);
		
		JButton btmodifnom = new JButton("modifier");
		btmodifnom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = JOptionPane.showInputDialog(piecepanel, "Changer le nom de l'objet", JOptionPane.QUESTION_MESSAGE);
				System.out.println("\n"+"nom objet :"+ objetactuel.getNom()+"\n");
				if( nom != null) {
				objetactuel.ModifieNomObjet(nom);
				donnee.modifieObjet(objetactuel);
				infobjcontent.revalidate();
				infobjcontent.repaint();
				affiche_infos_objet(objetactuel);
				}
			}
		});
		btmodifnom.setFont(new Font("Dialog", Font.BOLD, 10));
		btmodifnom.setBounds(500, 100, 82, 20);
		infobjpanel.add(btmodifnom);
		
		JButton btmodifadr = new JButton("modifier");
		btmodifadr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String adr = JOptionPane.showInputDialog(piecepanel, "Changer l'adresse MAC", JOptionPane.QUESTION_MESSAGE);			
				if( adr != null ) {
				if (contientAdrMAC(donnee.getObjet(),adr)) {
					JOptionPane.showMessageDialog(infobjcontent, "Adresse MAC déjà utilisée", "Information", JOptionPane.WARNING_MESSAGE);}
				else {
				objetactuel.ModifieAdresseMAC(adr);
				infobjcontent.revalidate();
				infobjcontent.repaint();
				affiche_infos_objet(objetactuel);}
				}
			}
		});
		btmodifadr.setFont(new Font("Dialog", Font.BOLD, 10));
		btmodifadr.setBounds(500, 132, 82, 20);
		infobjpanel.add(btmodifadr);
		
		JButton btmodifconso = new JButton("modifier");
		btmodifconso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				String conso=JOptionPane.showInputDialog(piecepanel, "Changer la consommation de l'objet", JOptionPane.QUESTION_MESSAGE);
				
				if( conso != null) {
				objetactuel.ModifieConsommation(Integer.parseInt(conso));
				infobjcontent.revalidate();
				infobjcontent.repaint();
				affiche_infos_objet(objetactuel);
				}
			}
		});
		btmodifconso.setFont(new Font("Dialog", Font.BOLD, 10));
		btmodifconso.setBounds(500, 164, 82, 20);
		infobjpanel.add(btmodifconso);
		
		JButton btmodifpriorite = new JButton("modifier");
		btmodifpriorite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prt = JOptionPane.showInputDialog(piecepanel, "Changer priorité", JOptionPane.QUESTION_MESSAGE);
				if( prt != null) {
					if(Integer.parseInt(prt)> -1 && Integer.parseInt(prt) < 4) {
						objetactuel.ModifiePriorite(Integer.parseInt(prt));
						infobjcontent.revalidate();
						infobjcontent.repaint();
						affiche_infos_objet(objetactuel);}
					else {JOptionPane.showMessageDialog(infobjcontent, "Priorité comprise entre 0 et 3", "Information", JOptionPane.WARNING_MESSAGE);}}}
		});
		btmodifpriorite.setFont(new Font("Dialog", Font.BOLD, 10));
		btmodifpriorite.setBounds(500, 220, 82, 20);
		infobjpanel.add(btmodifpriorite);
		
		JButton btonoff = new JButton("on/off");
		btonoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objetactuel.AllumerEteindre();		
				objetcontent.revalidate();
				objetcontent.repaint();
				infobjpanel.revalidate();
				infobjpanel.repaint();
				affiche_infos_objet(objetactuel);
				System.out.println(objetactuel.getSwitch());
				
			}
		});
		btonoff.setBounds(500, 256, 82, 20);
		infobjpanel.add(btonoff);
		
		JButton btaddfavoris = new JButton("");
		btaddfavoris.setIcon(new ImageIcon("src/main/resources/fa1v.png"));
		btaddfavoris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			ajout_objet_favoris(objetactuel);
			}
		});
		btaddfavoris.setBounds(12, 13, 65, 38);
		
		infobjpanel.add(btaddfavoris);
				
		listobjpanel.setBounds(0, 0, 594, 697);
		frame.getContentPane().add(listobjpanel);
		
		listobjpanel.setLayout(null);
		objetpanel.setBounds(0, 0, 594, 697);
		frame.getContentPane().add(objetpanel);
		objetpanel.setLayout(null);
		
		
		
		objetcontent.setBackground(SystemColor.control);
		objetcontent.setBounds(0, 38, 570, 618);
		objetpanel.add(objetcontent);
		objetcontent.setLayout(null);
		
		objetpanel.add(objetcontent);
		
		JButton btnaddObjet = new JButton("Ajouter objet ");
		btnaddObjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajout_objet(pieceactuelle);
				objetpanel.revalidate();
				objetpanel.repaint();	
				System.out.println("ajout objet");
				affiche_objet(pieceactuelle.getObjetPiece(),objetcontent);
						
			}
		});
		btnaddObjet.setBounds(353, 0, 150, 25);
		objetpanel.add(btnaddObjet);
		
		JButton btsuppiece = new JButton("Supprimer pièce");
		btsuppiece.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supprime_piece(pieceactuelle);
				piecepanel.setVisible(true);
				menupanel.setVisible(false);
				objetpanel.setVisible(false);			
				affiche_piece();
			}
		});
		btsuppiece.setBounds(431, 666, 150, 25);
		objetpanel.add(btsuppiece);
		
		JButton btmodpiece = new JButton("modifier nom de la piece");
		btmodpiece.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = JOptionPane.showInputDialog(piecepanel, "Modifier le nom de la pièce", JOptionPane.QUESTION_MESSAGE);
				if(nom !=null) {
				pieceactuelle.changerNom(nom);
				btpiece.elementAt(piece.indexOf(pieceactuelle)).setText(nom);
				for (Objet o : pieceactuelle.ObjetPiece) {
					o.ModifiePieceMaison(nom);
				}
				}
			}
		});
		btmodpiece.setBounds(200, 666, 210, 25);
		objetpanel.add(btmodpiece);
		frame.setResizable(false);
		

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		mnFichier.setForeground(new Color(0, 128, 0));
		menuBar.add(mnFichier);
		
		JMenuItem mntmSauvegarder = new JMenuItem("Sauvegarder");
		mntmSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sauvegarder_fichier();
			}
		});
		mntmSauvegarder.setForeground(new Color(46, 139, 87));
		mnFichier.add(mntmSauvegarder);
		
		JMenuItem mntmImporterFichier = new JMenuItem("Charger sauvegarde");
		mntmImporterFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charger_sauvegarde();
			}
		});
		mntmImporterFichier.setForeground(new Color(46, 139, 87));
		mnFichier.add(mntmImporterFichier);
		frame.getContentPane().setLayout(null);
		
		simupanel.setBounds(0, 0, 594, 697);
		frame.getContentPane().add(simupanel);
		simupanel.setLayout(null);
		simupanel.setVisible(false);
		
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 744, 594, -743); 
		frame.getContentPane().add(layeredPane);			
		
		favorispanel.setBounds(0, 0, 594, 690);
		frame.getContentPane().add(favorispanel);
		favorispanel.setLayout(null);
		
		infobjpanel.setVisible(false);
		listobjpanel.setVisible(false);
		objetpanel.setVisible(false);
		favorispanel.setVisible(false);
	}

	/**
	 * Permet de faire des actions en fonciton du bouton cliqué
	 */
	 
	public void actionPerformed(ActionEvent e) {
		
		//action sur les boutons de pieces 
		for(int i=0 ; i< btpiece.size() ; i++) {
			if(e.getSource() ==  btpiece.elementAt(i) ) {						
				System.out.println( piece.elementAt(i).getNom());
				menupanel.setVisible(false);
				objetpanel.setVisible(true);
				piecepanel.setVisible(false);
				affiche_objet( piece.elementAt(i).getObjetPiece(),objetcontent);
			    pieceactuelle= piece.elementAt(i);	
				}			
		}		
		//action sur les boutons d'objets
		for( int i=0 ; i< btobjet.size() ; i++) {
			if(e.getSource() ==  btobjet.elementAt(i) ) {		
				if(pieceactuelle != null) {
				objetactuel =  pieceactuelle.getObjetPiece().elementAt(i);
				objetpanel.setVisible(false);
				infobjpanel.setVisible(true);
				}
				
				else if (panelactu.equals("favoris"))
				{objetactuel = donnee.ObjetFavoris.elementAt(i);
				 panelprec = panelactu;
				 panelactu="objfavoris";
				 favorispanel.setVisible(false);
				 infobjpanel.setVisible(true);
				}	

				else if (panelactu.equals("listobj")) 
				{ objetactuel = donnee.getObjet().elementAt(i);
				panelprec = panelactu;
				panelactu="objlistobj";
				listobjpanel.setVisible(false);
				infobjpanel.setVisible(true);
				}						
				affiche_infos_objet(objetactuel);
			}		
		}
	}

	/**
	 * Ajoute les boutons pièce ainsi que leurs actions
	 */
	public void setBtpiece() {
		for(int i=0;i<piece.size();i++) {
			btpiece.add(new JButton( piece.elementAt(i).getNom() ));
			btpiece.elementAt(i).addActionListener(this);}	
	} 
	
	/**
	 * Ajout des pièces dans le vecteur btpièce
	 */

	public void ajout_piece(){ 	
	    String nom = JOptionPane.showInputDialog(piecepanel, "Nom de la pièce", JOptionPane.QUESTION_MESSAGE);
	    
	    if( nom != null ) {
	    if ( pieceExist(nom) != -1 )JOptionPane.showMessageDialog(piecepanel, "Pièce déja existante", "Information", JOptionPane.WARNING_MESSAGE);
	    else {
	    piece.add(new Piece(nom,0));	 
	    btpiece.addElement(new JButton(piece.lastElement().getNom()));
	    btpiece.lastElement().addActionListener(this);}}       
	}
	
	
	/**
	 * Affichage des boutons pièces 
	 */
	public void affiche_piece(){
		int x=15,y=10;
		piececontent.removeAll();		
			for(int i=0;i<btpiece.size();i++){				
				btpiece.elementAt(i).setBounds(x, y, 180, 115);
				btpiece.elementAt(i).setBackground(Color.BLACK);
				btpiece.elementAt(i).setForeground(Color.LIGHT_GRAY);
				piececontent.add(btpiece.elementAt(i));			
				if(x >=395 ) {x=15;y+=125;}
				else x+=190;}
	}
	
	/**
	 * Affichage les boutons du simulateur : consoJour, consoSemaine , consMois ,Extinction automatique
	 */
	public void affiche_simulateur() {
		simupanel.removeAll();
		
		JButton modextinction = new JButton("modifier");
		modextinction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String conso=JOptionPane.showInputDialog(piecepanel, "Changer la consommation limite", JOptionPane.QUESTION_MESSAGE);	
				if( conso != null) {
				s.SetConsommationANePasDepasser(Integer.parseInt(conso));
				simupanel.revalidate();
				simupanel.repaint();
				affiche_simulateur();
				}
			}
		});
		modextinction.setBounds(247, 0, 97, 25);
		simupanel.add(modextinction);
		JLabel lblNewLabel = new JLabel("Consommation limite : "+s.ConsommationANePasDepasser()+" Wh");
		lblNewLabel.setBounds(12, 4, 231, 16);
		simupanel.add(lblNewLabel);


		JButton consoj = new JButton("ConsoJour");
		consoj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(donnee != null )
					new traitement_donnees("Consommation du jour ",donnee.ConsoJour,5000);
				else JOptionPane.showMessageDialog(simupanel, "Aucune donnée n'a été importée !", "Information", JOptionPane.WARNING_MESSAGE);
		}});
		consoj.setBounds(12, 40, 185, 25);
		consoj.setBackground(Color.BLACK);
		consoj.setForeground(Color.LIGHT_GRAY);
		simupanel.add(consoj);
		
		JButton consos = new JButton("ConsoSemaine");
		consos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				if(donnee != null )
					new traitement_donnees("Consommation de la semaine ",donnee.ConsoSemaine,5000);
				else JOptionPane.showMessageDialog(simupanel, "Aucune donnée n'a été importée !", "Information", JOptionPane.WARNING_MESSAGE);
			}
		});
		consos.setBounds(12,90, 185, 25);
		consos.setBackground(Color.BLACK);
		consos.setForeground(Color.LIGHT_GRAY);
		simupanel.add(consos);
		
		JButton consom = new JButton("ConsoMois");
		consom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				if(donnee != null )
					new traitement_donnees("Consommation du mois ",donnee.ConsoMois,15000);
				else JOptionPane.showMessageDialog(simupanel, "Aucune donnée n'a été importée !", "Information", JOptionPane.WARNING_MESSAGE);
			}
		});
		consom.setBounds(12,140, 185, 25);
		consom.setBackground(Color.BLACK);
		consom.setForeground(Color.LIGHT_GRAY);
		simupanel.add(consom);

		JButton exctinction = new JButton("Exctinction Auto");
		exctinction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(donnee != null ){
					System.out.println(donnee.getObjet().size());
					s.recupereObjet();
					s.miseAJourObjet();
					s.calculConsommation();
					s.ExtinctionAutomatique(s.ConsommationANePasDepasser());
					donnee.modifieObjet(s.getObjet());
					//On mais à jour les données dans.
					lancer_simulateur();
					JOptionPane.showMessageDialog(simupanel, "Extinction automatique lancée", "Information", JOptionPane.WARNING_MESSAGE);
				}
				else JOptionPane.showMessageDialog(simupanel, "Aucune donnée n'a été importée !", "Information", JOptionPane.WARNING_MESSAGE);
			}
		});
		exctinction.setBounds(12,190, 185, 25);
		exctinction.setBackground(Color.BLACK);
		exctinction.setForeground(Color.LIGHT_GRAY);
		simupanel.add(exctinction);

	}

	/**
	 * Ajoute les boutons objet ainsi que leurs actions
	 */
	public void setBtobjet(Vector<Objet> p) {
		btobjet.removeAllElements();	
		for(int i=0; i<p.size() ;i++){
			JButton tmp= new JButton(p.elementAt(i).getNom() );
			tmp.addActionListener(this);
			btobjet.addElement(tmp); }	
	}

	/**
	 * Affichage des objets 
	 * @param p : Vecteur d'objet que l'on affiche
	 * @param pan le panel JPanel sur lequel on affiche les objets
	 */
	public void affiche_objet(Vector<Objet> p , JPanel pan){
		int y=10;
		int h=40;
		
		pan.removeAll();		
		if ( p.isEmpty()  == false && donnee!=null ) {				
			setBtobjet(p);			
			for(int i=0;i<btobjet.size();i++) {	
				btobjet.elementAt(i).setBounds(20, y+5, 150, h-10);
				pan.add(btobjet.elementAt(i));							
				Canvas canvas = new Canvas();
				if(i%2==0)canvas.setBackground(Color.LIGHT_GRAY);
				else canvas.setBackground(Color.white);		
				canvas.setBounds(12, y, 540, h);
				pan.add(canvas);			
				y += (h+5) ;					
			}
		}
	}
	/**
	 * Méthode pour créer les objets à partir de l'interface
	 */
	public void ajout_objet(Piece p) {

		String[] items = {"1","2","3"};
		JComboBox<String> combo = new JComboBox<String>(items);
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Priorité Objet:"));
		panel.add(combo);
		panel.add(new JLabel("Nom Objet:"));
		panel.add(field1);
		panel.add(new JLabel("Consommation:"));
		panel.add(field2);
		panel.add(new JLabel("AdresseMAC:"));
		panel.add(field3);

		int conso = 0;
		int prio = 0;

		int result = JOptionPane.showConfirmDialog(null, panel, "Nouvel objet", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			try {
				conso = Integer.parseInt(field2.getText());
				prio = Integer.parseInt((String) combo.getSelectedItem());
				System.out.println(prio);
			} catch (NumberFormatException nfe) {
				System.out.println("Consommation not a number");
				JOptionPane.showMessageDialog(piecepanel, "Consommation doit etre un nombre", "Information", JOptionPane.WARNING_MESSAGE);
			}

			if (contientAdrMAC(donnee.getObjet(), field3.getText())) {
				System.out.println("AdresseMAC already used");
				JOptionPane.showMessageDialog(infobjcontent, "Adresse MAC déjà utilisée", "Information", JOptionPane.WARNING_MESSAGE);
			}
			else {
				if (field1.getText() != null && conso != 0) {
					donnee.getObjet().addElement(new Objet(field1.getText(), pieceactuelle.getNom(), conso, field3.getText(), prio));
					ajoutObjetDansPiece(pieceactuelle, field1.getText());
				}
			}
		}
		else {
			System.out.println("Cancelled");
		}
	}
	
	/**
	 * Ajoute objet dans la pièce indiquée en paramètre
	 */
	public void ajoutObjetDansPiece(Piece p,String nom) {	    
	    if( nom != null ) {p.getObjetPiece().addElement(donnee.getObjet().lastElement());}
	}
	
	/**
	 * Méthode pour ajouter les objets dans favoris depuis l'interface
	 * @param o : l'objet sera mis dans le vecteur favoris
	 */
	@SuppressWarnings("static-access")
	public void ajout_objet_favoris(Objet o) {
		JOptionPane jop = new JOptionPane();
		if( !donnee.ObjetFavoris.contains(o) ) {
		donnee.ObjetFavoris.addElement(o);
		jop.showMessageDialog(null, "Objet ajouté dans les favoris", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
		donnee.ObjetFavoris.removeElement(o);
		jop.showMessageDialog(null, "Objet supprimé des favoris", "Information", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Méthode pour supprimer un objet à partir de l'interface
	 * @param o : l'Objet en paramètre sera supprimé
	 */
	public void supprime_objet(Objet o) {
		for(Piece p : piece) {
			if( p.getObjetPiece().contains(o)) {
				p.getObjetPiece().remove(o);
				break;}}
		donnee.getObjet().remove(o);
		if( donnee.ObjetFavoris.contains(o)  ) donnee.ObjetFavoris.remove(o);
		if(pieceactuelle != null)pieceactuelle.getObjetPiece().remove(o);
		
	}
	
	/**
	 * Méthode pour supprimer une pi§ce à partir de l'interface
	 * @param p : la pièce en paramètre sera supprimée
	 */
	public void supprime_piece(Piece p) {
		btpiece.removeElementAt(piece.indexOf(p));
		for( Objet o : p.getObjetPiece() ) {
			if( donnee.getObjet().contains(o))donnee.getObjet().remove(o);
			if( donnee.ObjetFavoris.contains(o))donnee.ObjetFavoris.remove(o);			
		}	
		piece.removeElement(p);	
	}

	/**
	 * Méthode pour sauvegarder les données actuelles dans le fichier importé auparavant
	 */
	public void sauvegarder_fichier(){		
		System.out.println("Lancement sauvegarde");
		if( donnee != null )donnee.ecriture(donnee.NomFichier);	
		else JOptionPane.showMessageDialog(menupanel, "Aucun fichier importé", "Information", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Méthode permettant d'importer un fichier et permettra donc d'utiliser les données du fichier
	 */
	@SuppressWarnings("static-access")
	public void charger_sauvegarde(){
        JOptionPane jop = new JOptionPane();
        if(donnee!=null ) {
        jop.showMessageDialog(menupanel, "Sauvegarde déja importée", "Information", JOptionPane.WARNING_MESSAGE);}
        else {
            System.out.println("Charger sauvegarde");
            donnee = new entresortie();
            donnee.lecturefichier(donnee.NomFichier);
        if(donnee!=null && donnee.NomFichier != null) {
        jop.showMessageDialog(menupanel, "Sauvegarde importée", "Information", JOptionPane.INFORMATION_MESSAGE);
        initpiece(donnee.getObjet());
        Allume_tous_objets();
        setBtpiece();
        s = new Simulateur(donnee);
        s.SetConsommationANePasDepasser(1000);
        AppelAutomatiqueRecuperationConsommation(10);}
        else {
            if(donnee.NomFichier!=null)
                jop.showMessageDialog(menupanel, "Erreur lors de l'importation de la sauvegarde", "Information", JOptionPane.WARNING_MESSAGE);
            donnee = null;
            }
        }
    }
	
	
	public void AppelAutomatiqueRecuperationConsommation(int s){
		Timer t = new Timer();
		t.schedule(new taskSauvegarde(this),1000,1000*s);		
	}
	
	/**
	 * Méthode pour afficher toutes les données de l'objet
	 * @param o : on vera toutes ces données actuelles
	 */
	public void affiche_infos_objet(Objet o) {
		
		infobjcontent.removeAll();		
		JLabel infobj = new JLabel("Information de l'objet");
		infobj.setBounds(12,10, 200, 15);
		infobjcontent.add(infobj);
			
		JLabel nom = new JLabel("Nom : "+o.getNom());
		nom.setBounds(12,40, 200, 15);
		infobjcontent.add(nom);
		
		JLabel adresse = new JLabel("Adresse MAC : "+o.getadresseMAC());
		adresse.setBounds(12,70, 300, 15);
		infobjcontent.add(adresse);
		
		JLabel conso = new JLabel("Consommation : "+o.getConsommation());
		conso.setBounds(12,100, 200, 15);
		infobjcontent.add(conso);
		
		JLabel piece = new JLabel("Pièce : "+ o.getPiece() );
		piece.setBounds(12,130, 200, 15);
		infobjcontent.add(piece);
		
		JLabel priorite= new JLabel("Niveau de priorité : "+o.getPriorite());
		priorite.setBounds(12,160, 200, 15);
		infobjcontent.add(priorite);
		
		JLabel etat= new JLabel("Allumé: "+ o.getSwitch());
		etat.setBounds(12,190, 200, 15);
		infobjcontent.add(etat);
		
		int hd=0,hf=0;
		Vector<preference> pref= new Vector<preference>();
        pref = donnee.getPreference();
        for(int i=0;i<pref.size();i++){
            if(pref.elementAt(i).getObjet().getNom()== o.getNom()){
            hd=pref.elementAt(i).getHeuredebut();
            hf=pref.elementAt(i).getHeureFin();
        }
        }
        if(hd != 0 && hf != 0) {
        JLabel heure_debut= new JLabel("Heure de debut : "+ hd);
        heure_debut.setBounds(12,220, 300, 15);
        infobjcontent.add(heure_debut);
        JLabel heure_fin= new JLabel("Heure de fin: "+ hf);
        heure_fin.setBounds(12,250, 300, 15);
        infobjcontent.add(heure_fin);}
           
         }

	/**
	 * Méthode pour récuperer les données dans la classe entresortie
	 */
	public entresortie getDonnee() {
		return donnee;
	}

	public void setDonnee(entresortie donnee) {
		//reimplementer
		this.donnee = donnee;
	}
	
	/**
	 * Méthode pour afficher la consommation globale de tous les objets du vecteur
	 */
	public void AfficheConsommationGlobale ( Vector<Objet> objet ){
		int consoG=0;
		for(int i=0;i<objet.size();i++){
			consoG+= objet.get(i).Consommation;
		}
		System.out.println("La consommation globale est" + consoG);			
	}

	/**
	 * Méthode pour échanger 2 string
	 */
	public void echanger(String a, String b){
		String temp;
		temp=a;
		a=b;
		b=temp;
	}
	
	/**
	 * Méthode pour partitionner
	 * @param O : Vecteur objet
	 * @param premier: le 1er élément du vecteur
	 * @param dernier: dernier élément du vecteur
	 * @param pivot
	 * @return une position du vecteur qui sera la position du pivot
	 */
	public int Partitionner_Objet(Vector <Objet> O, int premier , int dernier , int pivot){
		echanger(O.elementAt(pivot).getNom(),O.elementAt(dernier).getNom());
		int j=premier;   
	    for(int i=premier;i<dernier-1;i++)
	         {
	          if (  O.elementAt(i).getNom().compareTo(O.elementAt(dernier).getNom())  > 0  ) 
	              {
	              echanger(O.elementAt(i).getNom(),O.elementAt(j).getNom());
	              j++;
	              }
	          }
	     echanger(O.elementAt(dernier).getNom(),O.elementAt(j).getNom());
	     return j;
	}
	   
	/**
	 * Méthode de tri rapide
	 * Permet de trier le vecteur objet 
	 */
	public void tri_rapide_Objet(Vector <Objet> O, int premier , int dernier){
		if(premier < dernier){
			int pivot= 0; // par defaut
			pivot= Partitionner_Objet(O,premier,dernier,pivot);
			tri_rapide_Objet(O,premier,pivot-1);
			tri_rapide_Objet(O,pivot+1,dernier);}}
	
	/**
	 * Méthode pour partitionner
	 * @param O : Vecteur pièce
	 * @param premier: le 1er élément du vecteur
	 * @param dernier: dernier élément du vecteur
	 * @param pivot
	 * @return une position du vecteur qui sera la position du pivot
	 */
	public int Partitionner_Piece(Vector<Piece> O, int premier , int dernier , int pivot){
		echanger(O.elementAt(pivot).getNom(),O.elementAt(dernier).getNom());
		int j=premier;   
	    for(int i=premier;i<dernier-1;i++)
	         {
	          if (O.elementAt(i).getNom().compareTo(O.elementAt(dernier).getNom()) > 0 ) 
	              {
	              echanger(O.elementAt(i).getNom(),O.elementAt(j).getNom());
	              j++;
	              }
	          }
	         echanger(O.elementAt(dernier).getNom(), O.elementAt(j).getNom() );
	     return j;
	}
	
	/**
	 * Méthode de tri rapide
	 * Permet de trier le vecteur pièce
	 */
	public void tri_rapide_Piece(Vector <Piece> O, int premier , int dernier){
		if(premier < dernier){
			int pivot= 0; // par defaut
			pivot= Partitionner_Piece(O,premier,dernier,pivot);
			tri_rapide_Piece(O,premier,pivot-1);
			tri_rapide_Piece(O,pivot+1,dernier);}
	}
	
	/**
	 * Méthode qui initialise la pièce avec les objets dans le vecteur obj
	 * @param obj : vecteur d'Objet, tous les objets seront mis dans la pièce
	 */
	public void initpiece(Vector<Objet> obj) {
		for(Objet o : obj) {
			if( pieceExist(o.getPiece()) == -1 ) {
				piece.add(new Piece(o.getPiece(),0));
				piece.lastElement().getObjetPiece().add(o);
			}
			else if( pieceExist(o.getPiece()) != -1 ) {				
				piece.elementAt(pieceExist(o.getPiece())).getObjetPiece().add(o);
			}	
		}		
	}
	
	/**
	 * Méthode de vérification de l'existence d'une pièce
	 * @param nom de la piece
	 * @return -1 si elle existe pas sinon 1
	 */
	public int pieceExist(String nom) {
		if( piece.isEmpty())return -1;
		
		for(Piece p : piece) {
			if( p.getNom().equals(nom)) return piece.indexOf(p);
		}		
	return -1;
	}
	
	/**
	 * Méthode de vérification de l'adresse Mac
	 * Permet de trier le vecteur objet 
	 */
	public boolean contientAdrMAC(Vector<Objet> obj, String adr) {
		for(Objet o : obj  ) {
		if ( o.getadresseMAC().equals(adr) )
		return true ;}
		return false;
	}
	
	/**
	 * Méthode qui permet de simuler les conso jour, mois et semaine pour ensuite les sauvegarder
	 */
	public void lancer_simulateur() {
		//Met à jour les données
		s.recupereObjet();
		s.miseAJourObjet();
		s.calculConsommation();
		donnee.ConsoJour= s.consoJour();
		donnee.ConsoMois= s.ConsoMois();
		donnee.ConsoSemaine= s.ConsoSemaine();
		//Réinitialise la date pour le prochain passage.
		s.rechercheDateHeure();
		sauvegarder_fichier();
	}
	
	/**
	 * Méthode pour allumer ou éteindre tous les objets
	 */
	public void Allume_tous_objets() {
		for(Objet o: donnee.ListeObjet)
			if( o.Switch == false )o.AllumerEteindre();
	}
	
	/**
	 * Ajout de preferences sur un objet
	 */
	
	public void ajout_preference(Objet obj) {


        String[] items = {"Allumer","Eteindre"};
        JComboBox<String> combo = new JComboBox<String>(items);
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nom preference:"));
        panel.add(field1);
		//panel.add(new JLabel("0 : Allumer l'objet"));
		//panel.add(new JLabel("1 : Eteindre l'objet"));
        panel.add(new JLabel("Instruction:"));
        panel.add(combo);
        panel.add(new JLabel("Heure debut:"));
        panel.add(field2);
        panel.add(new JLabel("Heure fin:"));
        panel.add(field3);

        int heure_debut=0;
        int heure_fin=0;
        int ins=0;

        int result = JOptionPane.showConfirmDialog(null, panel, "Nouvel objet", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                heure_debut = Integer.parseInt(field2.getText());
                heure_fin = Integer.parseInt(field3.getText());
                //ins = Integer.parseInt((String) combo.getSelectedItem());
				if(combo.getSelectedItem() == "Allumer"){
					ins = 0;
				}
				else{
					ins = 1;
				}
            } catch (NumberFormatException nfe) {
                System.out.println(" L'heure not a number");
                JOptionPane.showMessageDialog(piecepanel, "Heure doit etre un nombre", "Information", JOptionPane.WARNING_MESSAGE);
            }
            if(heure_debut>24 || heure_debut<0 || heure_fin>24 || heure_fin<0){
				System.out.println(" L'heure out of border");
				JOptionPane.showMessageDialog(piecepanel, "Heure doit être inférieure à 24 et supérieure à 0", "Information", JOptionPane.WARNING_MESSAGE);
			}
			else {
            	if(heure_debut > heure_fin){
					System.out.println("Heure de debut supérieure à Heure de fin");
					JOptionPane.showMessageDialog(piecepanel, "Heure de fin doit etre supérieure à Heure de début", "Information", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if (field1.getText() != null && heure_debut != 0 && heure_fin != 0 && heure_debut != heure_fin) {
						donnee.getPreference().addElement(new preference(field1.getText(), obj, ins, heure_debut, heure_fin));
						JOptionPane.showMessageDialog(menupanel, "Preference ajouté", "Information", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
        }
        else {
            System.out.println("Cancelled");
        }
    }
}
