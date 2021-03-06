package controleur;

public interface Global {
	
	//Chemin d'accès au media
	static final String CHEMIN_SEPARATOR = "/";
	
	static final String CHEMIN_BOULES = CHEMIN_SEPARATOR + "boules" + CHEMIN_SEPARATOR;
	static final String CHEMIN_FONDS = CHEMIN_SEPARATOR + "fonds" + CHEMIN_SEPARATOR;
	static final String CHEMIN_MURS = CHEMIN_SEPARATOR + "murs" + CHEMIN_SEPARATOR;
	static final String CHEMIN_PERSONNAGES = CHEMIN_SEPARATOR + "personnages" + CHEMIN_SEPARATOR;
	static final String CHEMIN_SONS = "sons" + CHEMIN_SEPARATOR;
	
	static final String[] SONS_ARENE = { CHEMIN_SONS+"fight.wav" , CHEMIN_SONS+"hurt.wav" , CHEMIN_SONS+"death.wav" };
	static final int FIGHT = 0;
	static final int HURT = 1;
	static final int DEATH = 2;
	
	
	static final char SYMBOLE_SEPARATEUR = '~';
	static final String MOT_ECHANGE_CREATION_PERSO = "pseudo";
	static final String MOT_ECHANGE_MESSAGE = "tchat";
	static final String MOT_ECHANGE_ACTION = "action";
	static final int NB_ETAPE_MARCHE = 4;
	static final int NB_ETAPE_MORT = 2;
	static final int NB_ETAPE_TOUCHE = 2;
	
	static final int DEGAT_BOULE = 2;
	static final int SOIN_BOULE = 1;
	
	static final int NB_MURS = 20;
	static final int DEPLACEMENT = 10;
	static final int DEPLACEMENT_BOULE = 1;
	
	static final int LARGEUR_ARENE = 800;
	static final int HAUTEUR_ARENE = 600;
	
	static final int TAILLE_MUR_ARENE = 30;
	static final int TAILLE_MUR = 35;
	static final int TAILLE_PERSO = 35;
	static final int TAILLE_BOULE = 17;
}
