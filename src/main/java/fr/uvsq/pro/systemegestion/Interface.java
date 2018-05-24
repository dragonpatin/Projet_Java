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
 * Classe Interfce
 * Classe permettant l'afichage de l'interface graphique
 * Permet aussi de lancer le simulateur 
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
		
		PIECE.setBounds(12, 39, 162, 25);
		menupanel.add(PIECE);
		
		JLabel lblMenus = new JLabel("Menu");
		lblMenus.setBounds(12, 12, 70, 15);
		menupanel.add(lblMenus);
		
		JButton btsimu = new JButton("Lancer le simulateur");
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
		btsimu.setBounds(12, 115, 162, 25);
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
		btfav.setBounds(12, 77, 162, 25);
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
		btlistobj.setBounds(12, 153, 162, 25);
		menupanel.add(btlistobj);
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
		btnaddObjet.setBounds(353, 0, 117, 25);
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
		btsuppiece.setBounds(431, 666, 127, 25);
		objetpanel.add(btsuppiece);
		
		JButton btmodpiece = new JButton("modifer nom");
		btmodpiece.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = JOptionPane.showInputDialog(piecepanel, "Modifier le nom de la pièce", JOptionPane.QUESTION_MESSAGE);
				if(nom !=null) {
				pieceactuelle.changerNom(nom);
				btpiece.elementAt(piece.indexOf(pieceactuelle)).setText(nom);
				}
			}
		});
		btmodpiece.setBounds(301, 666, 117, 25);
		objetpanel.add(btmodpiece);
		frame.setResizable(false);
		
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
		btaddpiece.setBounds(344, 0, 117, 25);
		piecepanel.add(btaddpiece);
		piececontent.setBounds(-13, 25, 583, 672);
		piecepanel.add(piececontent);
		piececontent.setLayout(null);		
		

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
		
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 744, 594, -743); 
		frame.getContentPane().add(layeredPane);			
		
		favorispanel.setBounds(0, 0, 594, 690);
		frame.getContentPane().add(favorispanel);
		favorispanel.setLayout(null);
		
		simupanel.setBounds(0, 0, 594, 697);
		frame.getContentPane().add(simupanel);
		simupanel.setLayout(null);
		
		infobjpanel.setVisible(false);
		listobjpanel.setVisible(false);
		objetpanel.setVisible(false);
		piecepanel.setVisible(false);
		favorispanel.setVisible(false);
		simupanel.setVisible(false);	
		
		affiche_temperateurExt();	
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
	 * Ajoute les bouton pièce ainsi que leur action
	 */
	public void setBtpiece() {
		for(int i=0;i<piece.size();i++) {
			btpiece.add(new JButton( piece.elementAt(i).getNom() ));
			btpiece.elementAt(i).addActionListener(this);}	
	} 
	
	/**
	 * Ajout des pièce dans le vecteur btpièce
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
	 * Affichage des 3 boutons du simulateur 
	 */
	public void affiche_simulateur() {

		JButton consoj = new JButton("ConsoJour");
		consoj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(donnee != null )
					new traitement_donnees("Consommation du jour ",donnee.ConsoJour,5000);
				else JOptionPane.showMessageDialog(simupanel, "Aucune donnée n'a été importée !", "Information", JOptionPane.WARNING_MESSAGE);
		}});
		consoj.setBounds(12, 40, 160, 25);
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
		consos.setBounds(12,90, 160, 25);
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
		consom.setBounds(12,140, 160, 25);
		consom.setBackground(Color.BLACK);
		consom.setForeground(Color.LIGHT_GRAY);
		simupanel.add(consom);
	}

	/**
	 * Ajoute les bouton objet ainsi que leur action
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
	 * @param Vector d 'objet qu'on affiche
	 * @param  sur le panel JPanel pan
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
	 * Methode pour créer les objets à paritr de l'interface
	 */
	public void ajout_objet(Piece p) {

		String[] items = {"1","2","3"};
		JComboBox combo = new JComboBox(items);
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


	    /*String nom = JOptionPane.showInputDialog(piecepanel, "Nom objet :");
	    String test = JOptionPane.showInputDialog(piecepanel,"Conso test :");
	    int test2 = Integer.parseInt(test);
	    if( nom != null ) {
	    	donnee.getObjet().addElement(new Objet(nom,pieceactuelle.getNom(),test2,"null",0));
	    	ajoutObjetDansPiece(pieceactuelle,nom);}*/
	}
	
	/**
	 * Ajoute objet dans la piece indiquer en parametre
	 */
	public void ajoutObjetDansPiece(Piece p,String nom) {	    
	    if( nom != null ) {p.getObjetPiece().addElement(donnee.getObjet().lastElement());}
	}
	
	/**
	 * Methode pour ajouter les objets dans favoris depuis l'interface
	 * @param Objet o sera mis dans le vecteur favoris
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
	 * Methode pour supprimer un objet à paritr de l'interface
	 * @param l'Objet en parmetre sera supprimer 
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
	 * Methode pour supprimer une piece à paritr de l'interface
	 * @param la piece en parmetre sera supprimer 
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
	 * Methode pour sauvegarder les donnée actuel dans le fichier importé auparavant
	 */
	public void sauvegarder_fichier(){		
		System.out.println("Lancement sauvegarde");
		if( donnee != null )donnee.ecriture(donnee.NomFichier);	
		else JOptionPane.showMessageDialog(menupanel, "Aucun fichier importé", "Information", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Methode permettant d'importé un fichier donc permettra d'utiliser les donnée du fichier
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
		if(donnee!=null ) {		
		jop.showMessageDialog(menupanel, "Sauvegarde importée", "Information", JOptionPane.INFORMATION_MESSAGE);
		initpiece(donnee.getObjet());	
		Allume_tous_objets();
		setBtpiece();
		AppelAutomatiqueRecuperationConsommation(10);}
		else jop.showMessageDialog(menupanel, "Erreur lors de l'importation de la sauvegarde", "Information", JOptionPane.WARNING_MESSAGE);}
	}
	
	
	public void AppelAutomatiqueRecuperationConsommation(int s){
		Timer t = new Timer();
		t.schedule(new taskSauvegarde(this),1000,1000*s);		
	}
	
	/**
	 * Methode pour afficher toutes les données de l'objet
	 * @param Objet: on vera toutes ces donnée actuelles
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
	}
	
	/**
	 * Methode pour récuperer les donnée dans la classe entresortie
	 */
	public entresortie getDonnee() {
		return donnee;
	}

	public void setDonnee(entresortie donnee) {
		//reimplementer
		this.donnee = donnee;
	}
	
	/**
	 * Methode pour afficher la consommation globale de tous les objet du vecteur 
	 */
	public void AfficheConsommationGlobale ( Vector<Objet> objet ){
		int consoG=0;
		for(int i=0;i<objet.size();i++){
			consoG+= objet.get(i).Consommation;
		}
		System.out.println("La consommation globale est" + consoG);			
	}

	/**
	 * Methode pour echanger 2 string 
	 */
	public void echanger(String a, String b){
		String temp;
		temp=a;
		a=b;
		b=temp;
	}
	
	/**
	 * Methode pour partitionner
	 * @param Vecteur objet 
	 * @param int premier: le 1er element du vecteur
	 * @param int dernier: dernier element du vecteur
	 * @param int pivot
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
	 * Methode de tri rapide 
	 * Permet de trier le vecteur objet 
	 */
	public void tri_rapide_Objet(Vector <Objet> O, int premier , int dernier){
		if(premier < dernier){
			int pivot= 0; // par defaut
			pivot= Partitionner_Objet(O,premier,dernier,pivot);
			tri_rapide_Objet(O,premier,pivot-1);
			tri_rapide_Objet(O,pivot+1,dernier);}}
	
	/**
	 * Methode pour partitionner
	 * @param Vecteur piece
	 * @param int premier: le 1er element du vecteur
	 * @param int dernier: dernier element du vecteur
	 * @param int pivot
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
	 * Methode de tri rapide 
	 * Permet de trier le vecteur piece 
	 */
	public void tri_rapide_Piece(Vector <Piece> O, int premier , int dernier){
		if(premier < dernier){
			int pivot= 0; // par defaut
			pivot= Partitionner_Piece(O,premier,dernier,pivot);
			tri_rapide_Piece(O,premier,pivot-1);
			tri_rapide_Piece(O,pivot+1,dernier);}
	}
	
	/**
	 * Methode qui initialise la piece avec les objets dans le vecteur obj
	 * @param Vecteur obj: tous les objets sera mis dans la piece
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
	 * Methode de verification de l'existence d'une piece
	 * @param verification sur la piece en parametre
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
	 * Methode de verification de l'adresse Mac
	 * Permet de trier le vecteur objet 
	 */
	public boolean contientAdrMAC(Vector<Objet> obj, String adr) {
		for(Objet o : obj  ) {
		if ( o.getadresseMAC().equals(adr) )
		return true ;}
		return false;
	}
	
	/**
	 * Methode qui permet de simuler les conso jour, mois et semaine pour ensuite les sauvegarder 
	 */
	public void lancer_simulateur() {
		Simulateur s = new Simulateur(donnee);
		//Mais a jour les données
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
	 * Methode pour allumer ou eteindre tous les objet
	 */
	public void Allume_tous_objets() {
		for(Objet o: donnee.ListeObjet)
			o.AllumerEteindre();
	}
	
	/**
	 * Methode pour afficher la temperature exterieur
	 */
	public void affiche_temperateurExt(){
		Simulateur s = new Simulateur(donnee);
		JLabel ltempExt = new JLabel("TempsExterieur:" + s.getTemperatureExt()+10);
		ltempExt.setBounds(350, 12, 150, 15);
		menupanel.add(ltempExt);
	}
	
}
