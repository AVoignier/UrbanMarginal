package controleur;

import vue.EntreeJeu;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import outils.connexion.AsyncResponse;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixJoueur;

public class Controle implements AsyncResponse , Global{

	private EntreeJeu frmEntreeJeu ;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	
	private ServeurSocket serverSocket;
	private ClientSocket clientSocket;
	
	private Jeu leJeu;

	private static final int PORT = 6666;
	
	/**
	 * Méthode de démarrage
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		new Controle();
	}
	
	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this);
		
		this.frmEntreeJeu.setVisible(true);
	}

	public void evenementEntreejeu(String info)
	{			
		
		if(info.equals("server"))
		{
			leJeu = new JeuServeur(this);
			this.frmArene = new Arene(this,"server");
			
			((JeuServeur)leJeu).constructionMurs();
			
			serverSocket = new ServeurSocket(this,PORT);
			frmEntreeJeu.setVisible(false);
			frmArene.setVisible(true);
		}
		else if(info.equals("Exit"))
		{
			System.exit(0);
		}
		else  // Si le joueur clic sur Connect
		{
			this.frmChoixJoueur = new ChoixJoueur(this);
			clientSocket = new ClientSocket(this, info, PORT);
		}
		
	}
	
	public void evenementChoixJoueur(String info) 
	{
		
		frmChoixJoueur.setVisible(false);
		frmArene.setVisible(true);
		
		( (JeuClient) leJeu).envoi(info);
		
		
	}
	
	public void evenementJeuServeur(String ordre, Object info) 
	{
		
		if (ordre.equals("ajout murs") ) 
		{
			frmArene.ajoutMurs(info);
		}
		else if (ordre.equals("ajout panel murs"))
		{
			leJeu.envoi( (Connection)info, frmArene.getjpnMurs() );
		}
		else if( ordre.equals("ajout panel jeu"))
		{
			leJeu.envoi( (Connection)info , frmArene.getJpnJeu() );
		}		
		else if( ordre.equals("ajout phrase") )
		{
			frmArene.ajoutTchat( (String)info );
			((JeuServeur)leJeu).envoi(info);
		}
		else if( ordre.equals("ajout jlabel jeu") )
		{
			frmArene.ajoutJLabelJeu( (JLabel)info );
		}
		
	}
	
	public void evenementJeuClient(String ordre, Object info )
	{
		
		if( ordre.equals("ajout panel murs") )
		{
			frmArene.setjpnMurs( (JPanel)info );
		}
		else if( ordre.equals("ajout panel jeu") )
		{
			frmArene.setJpnJeu( (JPanel)info );
		}
		else if( ordre.equals("modif tchat") )
		{
			frmArene.ajoutTchat( (String)info );
		}
		else if( ordre.equals("jouer son") )
		{
			frmArene.joueSon( (int)info );
		}
		
	}
	
	public void evenementArene(String ordre, Object info) 
	{
		if(ordre.equals(MOT_ECHANGE_MESSAGE))
		{
			((JeuClient)leJeu).envoi( MOT_ECHANGE_MESSAGE + "~" + ((String)info) );
		}
		else if( ordre.equals(MOT_ECHANGE_ACTION) )
		{
			((JeuClient)leJeu).envoi ( MOT_ECHANGE_ACTION + "~" + info );
		}
	}

	@Override
	public void reception(Connection connection, String ordre, Object info) {
		
		if(ordre.equals("connexion")) 
		{
			if( !(leJeu instanceof JeuServeur) )
			{
				leJeu = new JeuClient(this);
				this.frmArene = new Arene(this,"client");
				leJeu.connexion(connection);
				
				frmEntreeJeu.setVisible(false);
				frmChoixJoueur.setVisible(true);
			}
			else 
			{
				leJeu.connexion(connection);
			}
		}
		else if (ordre.equals("réception")) 
		{			
			leJeu.reception(connection, info);
		}
		else if (ordre.equals("deconnexion")) 
		{
			
		}
		
	}
	
	public void envoi( Connection connexion, Object message ) 
	{
		connexion.envoi(message);
	}
}
