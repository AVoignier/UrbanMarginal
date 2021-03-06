package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JLabel;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté serveur
 *
 */
public class JeuServeur extends Jeu implements Global {

	/**
	 * Collection de murs
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	/**
	 * Collection de joueurs
	 */
	//private ArrayList<Joueur> lesJoueurs = new ArrayList<Joueur>() ;
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>();
	
	/**
	 * Constructeur
	 */
	public JeuServeur( Controle controle ) {
		this.controle = controle;
	}
	
	@Override
	public void connexion(Connection connexion) {
		lesJoueurs.put(connexion,  new Joueur(this) );
	}

	@Override
	public void reception(Connection connexion, Object message) {
		
		String info = ((String)message);
		
		//Le contenu du message est séparé par le symbole ~
		ArrayList<String> mots = new ArrayList<String>();
		mots.add("");
		
		int idMot = 0;
		
		for( int i=0; i<info.length();i++ ) 
		{
			//Si on atteint le caractère de séparation, on passe au mot suivant
			if( info.charAt(i) == SYMBOLE_SEPARATEUR ) 
			{
				idMot++;
				mots.add("");
			}
			//Sinon, on ajoute la lettre au mot en cours
			else 
			{
				String motTemp =  mots.get(idMot).concat( String.valueOf( info.charAt(i) ) ) ;
				mots.set(idMot, motTemp);
			}
		}
		
		//pseudo
		if( mots.get(0).equals( MOT_ECHANGE_CREATION_PERSO ) )
		{			
			controle.evenementJeuServeur("ajout panel murs", connexion );
			lesJoueurs.get(connexion).initPerso( mots.get(1) , Integer.parseInt( mots.get(2) ), lesMurs, lesJoueurs.values() );
			
			//Phrase ajout joueur
			String phraseAjoutJoueur = "*** " + lesJoueurs.get(connexion).getPseudo() + " vient de se connecter ***";
			controle.evenementJeuServeur("ajout phrase", phraseAjoutJoueur );
			
		}
		//tchat
		else if( mots.get(0).equals( MOT_ECHANGE_MESSAGE ) )
		{
			//Création du message a afficher
			String messageAEnvoyer = lesJoueurs.get(connexion).getPseudo();
			messageAEnvoyer += " > ";
			
			for(int i = 1 ; i<mots.size();i++)
			{
				if(i>1)
				{
					messageAEnvoyer+="~";
				}
				
				messageAEnvoyer += mots.get(i);
			}
			System.out.println(messageAEnvoyer);
			
			controle.evenementJeuServeur("ajout phrase", messageAEnvoyer);
			
		}
		else if( mots.get(0).equals( MOT_ECHANGE_ACTION )  ) 
		{
			lesJoueurs.get(connexion).action( Integer.parseInt( mots.get(1) ) , lesJoueurs.values(), lesMurs ); ;
		}
		else 
		{
			System.out.print("message non reconnu : " + mots.get(0) );
		}
		
	}
	
	@Override
	public void deconnexion() {
	}

	/**
	 * Envoi d'une information vers tous les clients
	 * fais appel plusieurs fois à l'envoi de la classe Jeu
	 */
	public void envoi( Object info ) {
		
		Enumeration<Connection> enuConnections  = lesJoueurs.keys();

		while( enuConnections.hasMoreElements() )
		{
			super.envoi( enuConnections.nextElement() , info);
		}
		
		
		
	}
	
	public void envoiJeuATous()
	{
		Collection<Connection> lesConnections =  lesJoueurs.keySet();
		
		for( Connection connection : lesConnections )
		{
			controle.evenementJeuServeur("ajout panel jeu", connection);
		}
		
	}
	

	/**
	 * Génération des murs
	 */
	public void constructionMurs()
	{
		for(int i=0;i<NB_MURS;i++)
		{
			lesMurs.add( new Mur() );
			controle.evenementJeuServeur("ajout murs", lesMurs.get(i).getJLabel()  );
		}
	}
	
	public void ajoutJLabelJeuArene( JLabel jLabel )
	{
		controle.evenementJeuServeur("ajout jlabel jeu", jLabel);
	}
	
	public Collection<Joueur> getJoueurs()
	{
		return lesJoueurs.values();
	}
	
}
