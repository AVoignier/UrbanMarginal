package vue;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import java.awt.Canvas;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class Arene extends JFrame implements Global {

	private Controle controle;
	private JPanel contentPane;
	private JPanel jpnMurs;
	private JPanel jpnJeu;
	private JTextField txfSaisie;
	private JTextArea txtChat;
	private boolean client;

	private Son[] sons = new Son[ SONS_ARENE.length ];
	
	/**
	 * Create the frame.
	 */
	public Arene( Controle controle, String client )
	{
		
		this.controle = controle;
		
		if(client.equals("client"))
			this.client = true;
		else
			this.client = false;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 800);
		
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		jpnJeu = new JPanel();
		jpnJeu.setBounds(0,0,800,600 );
		jpnJeu.setOpaque(false);
		jpnJeu.setLayout(null);
		contentPane.add(jpnJeu);
		
		jpnMurs = new JPanel();
		jpnMurs.setBounds(0,0,815,600 );
		jpnMurs.setOpaque(false);
		jpnMurs.setLayout(null);
		contentPane.add(jpnMurs);
		
		JLabel lblFond = new JLabel("");
		lblFond.setIcon(new ImageIcon(Arene.class.getResource("/fonds/fondarene.jpg")));
		lblFond.setBounds(0, 0, LARGEUR_ARENE, HAUTEUR_ARENE);
		lblFond.setBounds(0, 0, 799, 600);
			
		contentPane.add(lblFond);
		
		txtChat = new JTextArea();
		txtChat.setBounds(10, 644, 779, 106);
		contentPane.add(txtChat);
		
		
		//Si il s'agit d'une arene client
		if(this.client)
		{
			txfSaisie = new JTextField();
			txfSaisie.setBounds(10, 611, 779, 20);
			contentPane.add(txfSaisie);
			txfSaisie.setColumns(10);
			
			//Initialisation des sons de l'arene
			for(int i=0;i<SONS_ARENE.length;i++)
			{
				sons[i] = new Son( getClass().getClassLoader().getResource( SONS_ARENE[i] ) );
			}
			
			txfSaisie.addKeyListener( new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					
					//Si la touche Enter est pressé
					if( e.getKeyCode() == KeyEvent.VK_ENTER ) 
					{
						//Si le champ txfSaisie n'est pas vide
						if( txfSaisie.getText().length() > 0 )
						{
							controle.evenementArene( MOT_ECHANGE_MESSAGE , txfSaisie.getText() );
							txfSaisie.setText("");
						}
						contentPane.requestFocus();
					}
					
				}
					
			});
			
			//Gestion des controle du personnage
			contentPane.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					toucheClavier(e);
				}
			});
			
			txtChat.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					toucheClavier(e);
					
				}
			});
			
		}
		
	}
	
	public void toucheClavier( KeyEvent e )
	{
		int touche = -1;
		
		if( e.getKeyCode() == KeyEvent.VK_UP )
			touche = KeyEvent.VK_UP;
		else if( e.getKeyCode() == KeyEvent.VK_DOWN )
			touche = KeyEvent.VK_DOWN;
		else if( e.getKeyCode() == KeyEvent.VK_LEFT )
			touche = KeyEvent.VK_LEFT;
		else if( e.getKeyCode() == KeyEvent.VK_RIGHT )
			touche = KeyEvent.VK_RIGHT;
		else if( e.getKeyCode() == KeyEvent.VK_SPACE)
			touche = KeyEvent.VK_SPACE;
		
		controle.evenementArene(MOT_ECHANGE_ACTION, touche);
	}
	
	public void ajoutMurs( Object murs ) 
	{
		jpnMurs.add( (JLabel)murs );
		jpnMurs.repaint();
		repaint();
	}
	
	public void ajoutJLabelJeu( JLabel jLabel)
	{
		jpnJeu.add(jLabel);
		jpnJeu.repaint();
	}
	
	public JPanel getjpnMurs()
	{
		return jpnMurs;
	}
	
	public void setjpnMurs( JPanel jpn )
	{
		jpn.setBounds(0,0,LARGEUR_ARENE,HAUTEUR_ARENE );
		jpn.setOpaque(false);
		jpn.setLayout(null);
				
		jpnMurs.add(jpn);
		repaint();
	}
	
	public JPanel getJpnJeu()
	{
		return jpnJeu;
	}
	
	public void setJpnJeu( JPanel jpn )
	{		
		jpnJeu.removeAll();
		jpnJeu.add(jpn);
		jpn.repaint();
		
		contentPane.requestFocus();
	}
	
	public String getTexteTxtChat()
	{
		return txtChat.getText() ;
	}
	
	public void setTxtArena( String texteTextArea )
	{
		txtChat.setText(texteTextArea);
	}
	
	public void ajoutTchat( String phrase )
	{
		String tchatTotal = txtChat.getText();
		tchatTotal += phrase + "\n\n";
		txtChat.setText(tchatTotal);
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}
	
	public void joueSon(int i)
	{
		sons[i].play();
	}
	
}
