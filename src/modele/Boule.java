package modele;

import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Gestion de la boule
 *
 */
public class Boule extends Objet implements Runnable {

	/**
	 * instance de JeuServeur pour la communication
	 */
	private JeuServeur jeuServeur ;
	
	/**
	 * Constructeur
	 */
	
	private Collection murs;
	private Joueur attaquant;
	
	public Boule(JeuServeur jeuServeur)
	{
		this.jeuServeur = jeuServeur;
		
		jLabel = new JLabel();
		jLabel.setBounds(0, 0, TAILLE_BOULE, TAILLE_BOULE);
		
		jLabel.setIcon( new ImageIcon( getClass().getResource(CHEMIN_BOULES + "boule.gif") ) );
		jLabel.setVisible(false);
	}
	
	/**
	 * Tire d'une boule
	 */
	public void tireBoule( Joueur attaquant, Collection<Mur> murs)
	{
		this.murs = murs;
		this.attaquant = attaquant;
		
		//Apparition de la boule a la bonne position
		if( attaquant.getOrientation() == 0 ) 
			posX = attaquant.getPosX()-TAILLE_BOULE-1;
		else
			posX = attaquant.getPosX()+TAILLE_PERSO+1;	
		posY = attaquant.getPosY()+TAILLE_PERSO/2-TAILLE_BOULE/2;
			
		jLabel.setVisible(true);
		
		new Thread(this).start();
	}

	@Override
	public void run() {
		//jouer le son de tir
		jeuServeur.envoi(FIGHT);
		
		this.attaquant.affiche("marche", 1);
		jLabel.setVisible(true);
		Joueur victime = null;
		
		int pas = DEPLACEMENT_BOULE;
		if(attaquant.getOrientation() == 0)
			pas *=-1;
		
		do
		{
			posX += pas;
			jLabel.setBounds(posX, posY, TAILLE_BOULE, TAILLE_BOULE);
			jeuServeur.envoiJeuATous();
			Collection joueurs = jeuServeur.getJoueurs();
			victime = (Joueur)super.toucheCollectionObjet(joueurs);
			
		}while( victime==null && super.toucheCollectionObjet(murs)==null && dansArene()  );
		posX = 0;
		posY = 0;
		
		if( victime != null )
		{
			victime.perteVie();
			attaquant.gainVie();
			
			int etape = 1;
			if(victime.estMort())
			{
				//Jouer le son de mort
				jeuServeur.envoi(DEATH);
				
				while(etape<=NB_ETAPE_MORT)
				{
					victime.affiche("mort", etape);
					etape++;
					pause(80, 0);
				}
			}
			else
			{
				//jouer le son de bléssé
				jeuServeur.envoi(HURT);
				while(etape<=NB_ETAPE_TOUCHE)
				{
					victime.affiche("touche", etape);
					etape++;
					pause(80, 0);
				}
				victime.affiche("marche", 1);
			}
			
		}
		jLabel.setVisible(false);
		jeuServeur.envoiJeuATous();
		
		
	}
	
	public boolean dansArene()
	{
		//gere uniquement la verticale
		if( posX<TAILLE_MUR_ARENE || posX>LARGEUR_ARENE-TAILLE_MUR_ARENE )
			return false;
		return true;
	}
	
	public void pause(long ms,int ns )
	{
		try 
		{
			Thread.sleep(ms, ns);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
