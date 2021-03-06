package modele;

import java.awt.Color;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

public class Mur extends Objet implements Global{

	/**
	 * Constructeur
	 */
	public Mur()
	{
		Random rd = new Random();
		
		posX = rd.nextInt( LARGEUR_ARENE - 2*TAILLE_MUR_ARENE - TAILLE_MUR ) + TAILLE_MUR_ARENE;
		posY = rd.nextInt( HAUTEUR_ARENE - 2*TAILLE_MUR_ARENE - TAILLE_MUR) + TAILLE_MUR_ARENE;
		
		jLabel = new JLabel();
		jLabel.setBounds(posX , posY , TAILLE_MUR, TAILLE_MUR);
		
		jLabel.setIcon(new ImageIcon( getClass().getResource( CHEMIN_MURS+"mur.gif" ) ) );
		
	}
	
}
