package controleur;

import vue.EntreeJeu;
import outils.connexion.AsyncResponse;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixJoueur;

public class Controle implements AsyncResponse {

	private EntreeJeu frmEntreeJeu ;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	
	private ServeurSocket serverSocket;
	private ClientSocket clientSocket;
	String typeJeu;

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
		this.frmChoixJoueur = new ChoixJoueur(this);
		this.frmArene = new Arene();
		
		this.frmEntreeJeu.setVisible(true);
	}

	public void evenementEntreejeu(String info)
	{	
		
		System.out.println(info);
		
		if(info == "server")
		{
			typeJeu = "server";
			serverSocket = new ServeurSocket(this,PORT);
			
			frmEntreeJeu.setVisible(false);
			frmArene.setVisible(true);
		}
		else if(info == "Exit")
		{
			System.exit(0);
		}
		else 
		{
			typeJeu = "client";
			clientSocket = new ClientSocket(this, info, PORT);
		}
		
	}
	
	public void evenementChoixJoueur(String info, int choixPerso) 
	{
		System.out.println(info + " s'est connecté avec le personnage " + choixPerso);
		frmChoixJoueur.setVisible(false);
		frmArene.setVisible(true);
		
		
	}

	@Override
	public void reception(Connection connection, String ordre, Object info) {
		
		if(ordre == "connexion") 
		{
			if( typeJeu == "client") 
			{
				frmEntreeJeu.setVisible(false);
				frmChoixJoueur.setVisible(true);
			}
		}
		else if (info == "reception") 
		{
			
		}
		else if (info == "deconnexion") 
		{
			
		}
		
	}
	
}
