
import java.awt.*; 
import java.lang.Integer;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class Interface implements ActionListener {

	private JFrame frame;
	private JPanel piecepanel = new JPanel();
	private JPanel menupanel = new JPanel();
	private JPanel objetpanel = new JPanel();
	private JPanel objetcontent = new JPanel();
	private JPanel infobjcontent = new JPanel();
	private JPanel infobjpanel = new JPanel();
	private Vector<JButton> btpiece = new Vector<JButton>();
	private Vector<JButton> btobjet = new Vector<JButton>();	
	private JButton btonoff;
	private Vector<Piece> piece = new Vector<Piece>(); 
	private Piece pieceactuelle;	
	private Objet objetactuel;
		
	public static void main(String[] args) {
					Interface window = new Interface();
					window.frame.setVisible(true);
	
	}


	public Interface() {
		init_vect_piece(piece,3);	
		setBtpiece();
		//setBtobjet();
		initialiseUI();
	}


	private void initialiseUI() {
		frame = new JFrame();
		frame.setBounds(100, 100,600,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		infobjpanel.setBounds(0, 0, 582, 814);
		frame.getContentPane().add(infobjpanel);
		infobjpanel.setLayout(null);
		

		
		JButton retour = new JButton("Retour");
		retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infobjpanel.setVisible(false);
				objetpanel.setVisible(true);
			}
		});
		retour.setBounds(453, 27, 117, 25);
		infobjpanel.add(retour);
		
		
		infobjcontent.setBounds(12, 64, 484, 517);
		infobjpanel.add(infobjcontent);
		infobjcontent.setLayout(null);
		
		JButton btnSupprimerObjet = new JButton("supprimer objet");
		btnSupprimerObjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supprime_objet(objetactuel);
				infobjpanel.setVisible(false);
				objetpanel.setVisible(true);
				affiche_objet(pieceactuelle);
			}
		});
		btnSupprimerObjet.setBounds(415, 689, 155, 25);
		infobjpanel.add(btnSupprimerObjet);
		
		JButton btmodifnom = new JButton("modifier");
		btmodifnom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = JOptionPane.showInputDialog(piecepanel, "Changer le nom de l'objet", JOptionPane.QUESTION_MESSAGE);
				
				if( nom != null) {
				objetactuel.ModifieNomObjet(nom);
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
				objetactuel.ModifieAdresseMAC(adr);
				infobjcontent.revalidate();
				infobjcontent.repaint();
				affiche_infos_objet(objetactuel);
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
				String prt = JOptionPane.showInputDialog(piecepanel, "Changer priorite", JOptionPane.QUESTION_MESSAGE);
				if( prt != null) {
				objetactuel.ModifiePriorite(Integer.parseInt(prt));
				infobjcontent.revalidate();
				infobjcontent.repaint();
				affiche_infos_objet(objetactuel);
				}
				
			}
		});
		btmodifpriorite.setFont(new Font("Dialog", Font.BOLD, 10));
		btmodifpriorite.setBounds(500, 220, 82, 20);
		infobjpanel.add(btmodifpriorite);
		
		btonoff = new JButton("off"); 
		btonoff.setBounds(500, 252, 82, 20);
		infobjpanel.add(btonoff);
		
		
		btonoff.addActionListener(this);
		btonoff.setFont(new Font("Dialog", Font.BOLD, 10));
		infobjpanel.setVisible(false);
		
		menupanel.setBounds(12, 25, 570, 710);
		frame.getContentPane().add(menupanel);
		menupanel.setLayout(null);
		
		piecepanel.setBounds(12, 25, 570, 710);
		frame.getContentPane().add(piecepanel);
		piecepanel.setLayout(null);
		
		objetpanel.setBounds(12, 25, 570, 710);
		frame.getContentPane().add(objetpanel);
		objetpanel.setLayout(null);
		
		piecepanel.setVisible(false);
		objetpanel.setVisible(false);
		

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		mnFichier.setForeground(Color.GRAY);
		menuBar.add(mnFichier);
		
		JMenuItem mntmSauvegarder = new JMenuItem("Sauvegarder");
		mntmSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sauvegarder_fichier();
			}
		});
		mntmSauvegarder.setForeground(Color.GRAY);
		mnFichier.add(mntmSauvegarder);
		
		JMenuItem mntmImporterFichier = new JMenuItem("Charger sauvegarde");
		mntmImporterFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charger_sauvegarde();
			}
		});
		mntmImporterFichier.setForeground(Color.GRAY);
		mnFichier.add(mntmImporterFichier);
		frame.getContentPane().setLayout(null);
		
		
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 744, 594, -743);
		frame.getContentPane().add(layeredPane);
				
		JButton PIECE = new JButton("Afficher pieces");
		PIECE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piecepanel.setVisible(true);
				menupanel.setVisible(false);
				objetpanel.setVisible(false);
				
				affiche_piece();
			}
		});
		PIECE.setBounds(12, 39, 162, 25);
		menupanel.add(PIECE);
		
		JLabel lblMenus = new JLabel("Menus");
		lblMenus.setBounds(12, 12, 70, 15);
		menupanel.add(lblMenus);
		
		JButton btnNewButton = new JButton("Lancer simulateur");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Lancement simulateur");
			}
		});
		btnNewButton.setBounds(12, 76, 162, 25);
		menupanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Objet favoris");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(12, 113, 162, 25);
		menupanel.add(btnNewButton_1);
		
		
		objetcontent.setBackground(SystemColor.control);
		objetcontent.setBounds(0, 104, 570, 710);
		objetpanel.add(objetcontent);
		objetcontent.setLayout(null);
			
		JLabel lblPiece = new JLabel("Piece");
		lblPiece.setBounds(10, 12, 70, 15);
		piecepanel.add(lblPiece);
			
		objetpanel.add(objetcontent);
		
		JLabel lblChambre = new JLabel("Objet");
		lblChambre.setBounds(12, 12, 70, 15);
		objetpanel.add(lblChambre);
		
		JButton btnaddObjet = new JButton("ajout objet ");
		btnaddObjet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajout_objet(pieceactuelle);
				objetpanel.revalidate();
				objetpanel.repaint();	
				System.out.println("ajout objet");
				affiche_objet(pieceactuelle);
						
			}
		});
		btnaddObjet.setBounds(250, 39, 117, 25);
		objetpanel.add(btnaddObjet);
		
		JButton btrtchambre = new JButton("retour");
		btrtchambre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piecepanel.setVisible(true);
				menupanel.setVisible(false);
				objetpanel.setVisible(false);
			}
		});
		
		btrtchambre.setBounds(417, 39, 117, 25);
		objetpanel.add(btrtchambre);
		
		JButton btretpiece = new JButton("retour");
		btretpiece.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				piecepanel.setVisible(false);
				menupanel.setVisible(true);
				objetpanel.setVisible(false);
			}
		});
		btretpiece.setBounds(417, 39, 117, 25);
		piecepanel.add(btretpiece);
		
		JButton btaddpiece = new JButton("ajout piece");
		btaddpiece.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				ajout_piece();
				piecepanel.revalidate();
				piecepanel.repaint();
				affiche_piece();
				System.out.println("ajout piece");
			}
		});
		btaddpiece.setBounds(250, 39, 117, 25);
		piecepanel.add(btaddpiece);
			
	}



	public void actionPerformed(ActionEvent e) {
		
		//action sur les boutons de pieces 
		for(int i=0 ; i< btpiece.size() ; i++) {
			if(e.getSource() ==  btpiece.elementAt(i) ) {						
				System.out.println( piece.elementAt(i).getNom());
				piecepanel.setVisible(false);
				menupanel.setVisible(false);
				objetpanel.setVisible(true);				
				affiche_objet( piece.elementAt(i));
			    pieceactuelle= piece.elementAt(i);			
				}			
		}		
		//action sur les boutons dobjets
		for( int i=0 ; i< btobjet.size() ; i++) {
			if(e.getSource() ==  btobjet.elementAt(i) ) {
				objetactuel =  pieceactuelle.getObjetPiece().elementAt(i);
				System.out.println(objetactuel.getNom() );
				objetpanel.setVisible(false);
				infobjpanel.setVisible(true);
				affiche_infos_objet(objetactuel);
			}	
			if(e.getSource() ==  btonoff) {
				
				objetactuel.AllumerEteindre();
				
				if( objetactuel.getSwitch() == false ) {
					btonoff.setText("off");	
					btonoff.setBackground(new Color(192, 192, 192));				
				}
				
				if( objetactuel.getSwitch() == true ) {
					btonoff.setText("on");	
					btonoff.setBackground(new Color(0, 255, 127));
				}		
				objetcontent.revalidate();
				objetcontent.repaint();
				infobjpanel.revalidate();
				infobjpanel.repaint();

				affiche_infos_objet(objetactuel);
				System.out.println(objetactuel.getSwitch());
			}
	
			
		}
	}


	public void setBtpiece() {
		for(int i=0;i<piece.size();i++) {
			btpiece.add(new JButton( piece.elementAt(i).getNom() ));
			btpiece.elementAt(i).addActionListener(this);
	}
		
	} 
	
	public void ajout_piece(){ 	
	    String nom = JOptionPane.showInputDialog(piecepanel, "Nom de la piece", JOptionPane.QUESTION_MESSAGE);
	    
	    if( nom != null ) {  
	    piece.add(new Piece(nom,0,null));	 
	    btpiece.addElement(new JButton(piece.lastElement().getNom()));
	    btpiece.lastElement().addActionListener(this);
	    }
	       
	}
	
	


	public void affiche_piece(){
		int x=15,y=100;
		
			
			for(int i=0;i<btpiece.size();i++){
				
				btpiece.elementAt(i).setBounds(x, y, 180, 115);
				btpiece.elementAt(i).setBackground(Color.BLACK);
				btpiece.elementAt(i).setForeground(Color.LIGHT_GRAY);
				piecepanel.add(btpiece.elementAt(i));
				
				if(x >=395 ) {x=15;y+=125;}
				else x+=190;
				
			}	
	}


	public void setBtobjet(Piece p) {
		btobjet.removeAllElements();	
		for(int i=0; i<p.getObjetPiece().size() ;i++){
			JButton tmp= new JButton(p.getObjetPiece().elementAt(i).getNom() );
			tmp.addActionListener(this);
			btobjet.addElement(tmp);	
		}	
	}


	public void affiche_objet(Piece p){
		int y=10;
		int h=80;
		objetcontent.removeAll();
		
		
		
		if ( p.getObjetPiece().isEmpty()  == false ) {
			System.out.println("Piece non vide");
					
			setBtobjet(p);			
			for(int i=0;i<btobjet.size();i++) {	
				btobjet.elementAt(i).setBounds(20, y+10, 150, 50);
				objetcontent.add(btobjet.elementAt(i));
								
				Canvas canvas = new Canvas();
				if(i%2==0)canvas.setBackground(Color.LIGHT_GRAY);
				else canvas.setBackground(Color.white);		
				canvas.setBounds(12, y, 540, h);
				objetcontent.add(canvas);			
				y += (h+10) ;					
			}
		}
		else System.out.println("Piece vide");
	}
	

	public Vector<JButton> getBtobjet() {
		return btobjet;
		
	}
	
	public void ajout_objet(Piece p) {
	    String nom = JOptionPane.showInputDialog(piecepanel, "Nom objet :");

		
	    
	    if( nom != null ) {  	    	
	    	p.getObjetPiece().addElement(new Objet(nom,pieceactuelle.getNom(),0,"null",0));
	    }
	      
	}
		
	public void supprime_objet(Objet o) {
		pieceactuelle.getObjetPiece().remove(o);
	}

	public void sauvegarder_fichier(){
		System.out.println("Lancer sauvegarde");
		
	}
	
	public void charger_sauvegarde(){
		System.out.println("Charger sauvegarde");
	
	}

	public void init_vect_piece(Vector<Piece> vectpiece,int n) {
		
		for(int i = 0 ; i < n; i++) {
			vectpiece.add(new Piece("piece"+i,0,null)); 
			vectpiece.elementAt(i).setObjetpiece(i+1);
		}   
		}
	
	public void affiche_infos_objet(Objet o) {
		infobjcontent.removeAll();
		JLabel infobj = new JLabel("Information objet");
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
		
		JLabel piece = new JLabel("Piece : "+pieceactuelle.getNom());
		piece.setBounds(12,130, 200, 15);
		infobjcontent.add(piece);
		
		JLabel priorite= new JLabel("Niveau de priorite : "+o.getPriorite());
		priorite.setBounds(12,160, 200, 15);
		infobjcontent.add(priorite);
		
		JLabel etat= new JLabel("Allumé: "+objetactuel.getSwitch());
		etat.setBounds(12,190, 200, 15);
		infobjcontent.add(etat);
		
	}
	    
	public void AfficheObjet(Vector <Objet> objet){
		for(int i=0;i<objet.size();i++)
		System.out.println(objet.get(i).NomObjet);			
	}
	    
	public void AffichePiece(Vector <Piece> piece){
		for(int i=0;i<piece.size();i++)
		System.out.println(piece.get(i).getNom());
	}
	    
	public void AfficheConsommationObjet(Objet o){
		System.out.println("La consommation de cette objet est" + o.Consommation);
	}
	    
	public void AfficheConsommationGlobale ( Vector <Objet> objet ){
		int consoG=0;
		for(int i=0;i<objet.size();i++){
			consoG+= objet.get(i).Consommation;
		}
		System.out.println("La consommation globale est" + consoG);			
	}

	   

	public void AjoutObjetDansPiece (){

	}
	    
	    
	public void echanger(String a, String b){
		String temp;
		temp=a;
		a=b;
		b=temp;
	}

	public int Partitionner_Objet(Vector <Objet> O, int premier , int dernier , int pivot){
		echanger(O.elementAt(pivot).getNom(),O.elementAt(dernier).getNom());
		int j=premier;   
	    for(int i=premier;i<dernier-1;i++)
	         {
	          if (O.elementAt(i).getNom().compareTo(O.elementAt(dernier).getNom())) //je ne comprend l erreur 
	              {
	              echanger(O.elementAt(i).getNom(),O.elementAt(j).getNom());
	              j++;
	              }
	          }
	     echanger(O.elementAt(dernier).getNom(),O.elementAt(j).getNom());
	     return j;
	}
	    
	public void tri_rapide_Objet(Vector <Objet> O, int premier , int dernier){
		if(premier < dernier){
			int pivot= 0; // par defaut
			pivot= Partitionner_Objet(O,premier,dernier,pivot);
			tri_rapide_Objet(O,premier,pivot-1);
			tri_rapide_Objet(O,pivot+1,dernier);
		
		}
	}
	
	public int Partitionner_Piece(Vector <Piece> O, int premier , int dernier , int pivot){
		echanger(O.elementAt(pivot).getNom(),O.elementAt(dernier).getNom());
		int j=premier;   
	    for(int i=premier;i<dernier-1;i++)
	         {
	          if (O.elementAt(i).getNom().compareTo(O.elementAt(dernier).getNom())) //je comprend pas l erreur 
	              {
	              echanger(O.elementAt(i).getNom(),O.elementAt(j).getNom());
	              j++;
	              }
	          }O.
	         echanger(O.elementAt(dernier).getNom(),O.elementAt(j).getNom());
	     return j;
	}
	    
	public void tri_rapide_Piece(Vector <Piece> O, int premier , int dernier){
		if(premier < dernier){
			int pivot= 0; // par defaut
			pivot= Partitionner_Piece(O,premier,dernier,pivot);
			tri_rapide_Piece(O,premier,pivot-1);
			tri_rapide_Piece(O,pivot+1,dernier);
		
		}
	}
	
	    
	    
	    
	public boolean test_nom_Objet ( String nomObjet, Vector <Objet> O, int n){
		for(int i=0;i<n-1;i++){
			if(nomObjet == O.get(i).getNom()) return true;
		}
		return false;
	}
	    
	public boolean test_nom_Piece( String nomPiece, Vector <Piece> O, int n){
			for(int i=0;i<n-1;i++){
			if(nomPiece == O.get(i).getNom()) return true;
		}
		return false;
	}
	    
	    
	public void AppelAutomatiqueRecuperationConsommation( entresortie donnee, int temps){

	}
	  
	public void lancerSimulateur(Simulateur sim){

	}
}


