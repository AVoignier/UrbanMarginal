package modele;

import java.util.Collection;

import javax.swing.JLabel;

import controleur.Global;

/**
 * Informations communes � tous les objets (joueurs, murs, boules)
 * permet de m�moriser la position de l'objet et de g�rer les  collisions
 *
 */
public abstract class Objet implements Global {

	/**
	 * position X de l'objet
	 */
	protected Integer posX ;
	/**
	 * position Y de l'objet
	 */
	protected Integer posY ;
	
	protected JLabel jLabel;
	
	/**
	 * contr�le si l'objet actuel touche l'objet pass� en param�tre
	 * @param objet contient l'objet � contr�ler
	 * @return true si les 2 objets se touchent
	 */
	public Boolean toucheObjet (Objet objet) {
				
		//Si les object sont sur la m�me ligne
		if( posX+jLabel.getWidth() > objet.posX   &&   posX < objet.posX + objet.getJLabel().getWidth() )
		{
			if( posY+jLabel.getHeight() > objet.posY && posY < objet.posY + objet.getJLabel().getHeight() )
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	public Objet toucheCollectionObjet( Collection<Objet> collection )
	{
		
		for( Objet objet:collection )
		{
			if( ! this.equals(objet) )
			{
				if( toucheObjet(objet) )
					return objet;
			}
		}
		
		return null;
	}
	
	public JLabel getJLabel()
	{
		return jLabel;
	}
	
	public int getPosX()
	{
		return this.posX;
	}
	
	public int getPosY()
	{
		return this.posY;
	}
	
}
