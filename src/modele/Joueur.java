package modele;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Gestion des joueurs
 *
 */
public class Joueur extends Objet {

	/**
	 * vie de départ pour tous les joueurs
	 */
	private static final int MAXVIE = 10 ;
	/**
	 * gain de points de vie lors d'une attaque
	 */
	private static final int GAIN = 1 ; 
	/**
	 * perte de points de vie lors d'une attaque
	 */
	private static final int PERTE = 2 ; 
	
	/**
	 * pseudo saisi
	 */
	private String pseudo ;
	/**
	 * n° correspondant au personnage (avatar) pour le fichier correspondant
	 */
	private int numPerso ; 
	/**
	 * instance de JeuServeur pour communiquer avec lui
	 */
	private JeuServeur jeuServeur ;
	/**
	 * numéro d'étape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape ;
	/**
	 * la boule du joueur
	 */
	private Boule boule ;
	/**
	 * vie restante du joueur
	 */
	private int vie ; 
	/**
	 * tourné vers la gauche (0) ou vers la droite (1)
	 */
	private int orientation ;
	
	private JLabel message;
	
	/**
	 * Constructeur
	 */
	public Joueur( JeuServeur jeuserveur ) {
		this.jeuServeur = jeuserveur;
		this.vie = MAXVIE;
		this.orientation = 1;
		this.etape = 1;
		
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position, affichage, création de la boule)
	 */
	public void initPerso(String pseudo, int numPerso, Collection<Mur> murs, Collection<Joueur> joueurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		
		this.jLabel = new JLabel();	
		this.message = new JLabel();
		
		premierePosition(murs, joueurs);
		
		this.boule = new Boule(jeuServeur);
		
		this.message.setHorizontalAlignment( JLabel.CENTER );
		this.message.setFont( new Font( Font.DIALOG , Font.PLAIN , 8) );
		this.message.setBounds( jLabel.getX()-10 , jLabel.getY()+ jLabel.getHeight()  , jLabel.getWidth()+20 , 15);
		this.message.setText(pseudo + ":" + vie);
		
		jeuServeur.ajoutJLabelJeuArene(jLabel);
		jeuServeur.ajoutJLabelJeuArene(message);
		jeuServeur.ajoutJLabelJeuArene(boule.getJLabel());
		
		affiche("marche", etape);
		
		System.out.println( "Joueur " + pseudo + " - num perso " + numPerso + " crée" );
		
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur)
	 */
	private void premierePosition( Collection murs, Collection joueurs  ) {

		Random rd = new Random();
		jLabel.setBounds(0 , 0 , TAILLE_PERSO , TAILLE_PERSO );
		do
		{
			posX = rd.nextInt(LARGEUR_ARENE - 2*TAILLE_MUR_ARENE - TAILLE_PERSO)+ TAILLE_MUR_ARENE;
			posY = rd.nextInt(HAUTEUR_ARENE - 2*TAILLE_MUR_ARENE - TAILLE_PERSO)+ TAILLE_MUR_ARENE;
		}while( toucheCollectionObjet(joueurs)!=null || toucheCollectionObjet(murs)!=null );
		
		jLabel.setBounds(posX , posY , TAILLE_PERSO , TAILLE_PERSO );
				
	}
	
	/**
	 * Affiche le personnage et son message
	 */
	public void affiche( String etat, int etape ) {
		this.etape = etape;
		String nomPerso = CHEMIN_PERSONNAGES + "perso" + numPerso + etat + etape + "d" + orientation + ".gif" ;
		jLabel.setIcon( new ImageIcon( getClass().getResource(nomPerso) ) );
		jLabel.setBounds(posX , posY , TAILLE_PERSO , TAILLE_PERSO );
		this.message.setText(pseudo + ":" + vie);
		this.message.setBounds( jLabel.getX()-10 , jLabel.getY()+ jLabel.getHeight()  , jLabel.getWidth()+20 , 15);
		jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 */
	public void action(int touche, Collection joueurs, Collection murs ) {
		
		//Si le joueur est encore en vie
		if( !estMort() )
		{
		
			//Si c'est une touche de deplacement
			if( touche == KeyEvent.VK_UP || touche == KeyEvent.VK_DOWN || touche == KeyEvent.VK_LEFT || touche == KeyEvent.VK_RIGHT)
			{
				deplace(touche,joueurs,murs);
			}
			else if(touche ==  KeyEvent.VK_SPACE )
			{
				//Si la boule est visible (=déjà tirée)
				if( !boule.getJLabel().isVisible())
				{
					boule.tireBoule(this, murs);
				}
			}
			
			affiche("marche", this.etape);
		}
	}

	/**
	 * Gère le déplacement du personnage
	 */
	private void deplace(int touche, Collection<Objet> joueurs , Collection<Objet> murs )
	{
		int posXIni = posX;
		int posYIni = posY;
		
		//Changement de l'étape de marche
		etape++;
		if(etape > NB_ETAPE_MARCHE)
			etape = 1;
		
		switch (touche) {
			case KeyEvent.VK_UP:
				posY -= DEPLACEMENT;
				
				//Si il sort des limites
				if( posY < TAILLE_MUR_ARENE )
					posY = TAILLE_MUR_ARENE;
				
			break;
			
			case KeyEvent.VK_DOWN:
				posY += DEPLACEMENT;
				
				//Si il sort des limites
				if( posY > HAUTEUR_ARENE - 2*TAILLE_MUR_ARENE )
					posY = HAUTEUR_ARENE - 2*TAILLE_MUR_ARENE;				
				
			break;
			
			case KeyEvent.VK_LEFT:
				//Orientation du personnage
				orientation = 0;
				
				posX -= DEPLACEMENT;
				
				if( posX < TAILLE_MUR_ARENE )
					posX = TAILLE_MUR_ARENE;
				
			break;
				
			case KeyEvent.VK_RIGHT:
				//Orientation du personnage
				orientation = 1;
				
				posX += DEPLACEMENT;
				
				if( posX > LARGEUR_ARENE-2*TAILLE_MUR_ARENE )
					posX = LARGEUR_ARENE-2*TAILLE_MUR_ARENE;
				
			break;
		}
		
		if( toucheCollectionObjet(joueurs)!=null || toucheCollectionObjet(murs)!=null )
		{
			posX = posXIni;
			posY = posYIni;
		}
		
		
	}
	
	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie()
	{
		this.vie += SOIN_BOULE;
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie()
	{
		this.vie -= DEGAT_BOULE;
		if(this.vie<0)
			this.vie = 0;
	}
	
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return vie==0;
	}
	
	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
	}
	
	public String getPseudo() 
	{
		return pseudo;
	}
	
	public int getOrientation()
	{
		return this.orientation;
	}
	
}
