package modele;

import javax.swing.JPanel;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 *
 */
public class JeuClient extends Jeu {
	
	Connection connexion;
	Boolean mursOK;
	
	/**
	 * Controleur
	 */
	public JeuClient(Controle controle) {
		this.controle = controle;
		this.mursOK = false;
	}
	
	@Override
	public void connexion(Connection connexion) {
		
		this.connexion = connexion;
		
	}

	@Override
	public void reception(Connection connexion, Object info) {
				
		//Test sur le type de Object
		if( info instanceof JPanel ) 
		{
			if( mursOK )
			{
				controle.evenementJeuClient("ajout panel jeu", info);
			}			
			else
			{
				mursOK = true;
				controle.evenementJeuClient("ajout panel murs", info);
			}
		}
		else if (info instanceof String )
		{
			controle.evenementJeuClient("modif tchat", info);
		}
		else if( info instanceof Integer )
		{
			controle.evenementJeuClient("jouer son", info);
		}
		
	}
	
	@Override
	public void deconnexion() {
	}

	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à l'envoi dans la classe Jeu
	 */
	public void envoi(String message) {
		super.envoi(connexion, message);
	}

}
