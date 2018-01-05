import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Demineur  implements ActionListener, EcouteurJeu
{	
	private static final int LONGUEUR_FACILE = 8;
	private static final int HAUTEUR_FACILE = 8;
	private static final int NB_MINES_FACILE = 10;
	
	private static final int LONGUER_MOYEN = 16;
	private static final int HAUTEUR_MOYEN = 16;
	private static final int NB_MINES_MOYEN = 40;

	private static final int LONGEUR_DIFICILE = 32;
	private static final int HAUTEUR_DIFICILE = 16;
	private static final int NB_MINES_DIFICILE = 99;

	private int Nb_Mines;
	private int Hauteur;
	private int Largeur;
	private Champ c;
	private int nbdrapeaux;
	private int nbcasesdecouverte;
	private int points;
	
	private JFrame fenetre;
	private JPanel panneauPrincipal;
	private JPanel PanneauScore;
	private JLabel Score;
	private JButton boutonNouvellePartie;
	private JLabel nbMines;
	private JMenuBar barreMenus;
	private JMenu menuPartie;
	private JMenuItem NouvellePartie;
	private JMenuItem quitter;
	private JMenu menuNiveaux;
	private ButtonGroup niveaux;
	private JRadioButtonMenuItem niv1;
	private JRadioButtonMenuItem niv2;
	private JRadioButtonMenuItem niv3;
	private javax.swing.Timer t;
	
	private Demineur(int Nb_mines,int  largeur,int hauteur)
	{
		points = 0;
		Nb_Mines = Nb_mines;
		Hauteur = hauteur;
		Largeur = largeur;
		nbdrapeaux = Nb_Mines;
		nbcasesdecouverte = 0;
		c=new Champ(Nb_mines, largeur, hauteur,this);
		
		t = new javax.swing.Timer (1000, this);
		c.setMinesadjacentes( largeur,hauteur);
		
		c.afficher(); 
		IHM();
		/*
		FileOutputStream fic;
		try {
			fic = new FileOutputStream("fichierScore");
			ObjectOutputStream data = new ObjectOutputStream(fic);
			int [] s= new int[]{999,999,999};
			String [] ss= new String[]{"anonyme","anonyme","anonyme"};
			Score score1= new Score(s, ss);
			data.writeObject(score1);
			data.close();
			fic.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		FileInputStream fic2;
		
			try {
				fic2= new FileInputStream("fichierScore");
				ObjectInputStream data2 = new ObjectInputStream(fic2);
				Score O= (Score) data2.readObject();
				data2.close();
				fic2.close();
				O.afficher();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
	
		

		
	}
	public void IHM()
	{
		fenetre = new JFrame( " Démineur ");
		panneauPrincipal = new JPanel();
		panneauPrincipal.setLayout(new BorderLayout());
		fenetre.setContentPane(panneauPrincipal);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panneau Score dans le haut de la fenÃªtre
		PanneauScore = new JPanel();
		PanneauScore.setLayout (new FlowLayout() );
		Score = new JLabel("0");
		PanneauScore.add(Score);
		boutonNouvellePartie = new JButton ("Nouvelle Partie");
		boutonNouvellePartie.addActionListener(this);
		PanneauScore.add(boutonNouvellePartie);
		nbMines = new JLabel(String.valueOf(Nb_Mines));
		PanneauScore.add(nbMines);
		panneauPrincipal.add(PanneauScore, BorderLayout.NORTH);
		

		// Panneau Champ
				
		panneauPrincipal.add(c, BorderLayout.CENTER);
		//c.setLayout(new GridLayout(Largeur,Hauteur));
		fenetre.pack();
		fenetre.setVisible(true);
		
		// Menu
		barreMenus = new JMenuBar();
		fenetre.setJMenuBar(barreMenus);
		
		menuPartie = new JMenu("Partie");
		barreMenus.add(menuPartie);
		
		NouvellePartie = new JMenuItem (" Nouvelle Partie");
		menuPartie.add( NouvellePartie);
		 NouvellePartie.addActionListener(this);
		
		 quitter = new JMenuItem ("Quitter");
		menuPartie.add(quitter);
		quitter.addActionListener(this);
			
		menuNiveaux = new JMenu("Niveaux");
		barreMenus.add(menuNiveaux);

		// bouton radio
		niveaux = new ButtonGroup();
		
		niv1 = new JRadioButtonMenuItem("Niveau 1", true);
		menuNiveaux.add(niv1);
		niveaux.add(niv1);
		niv1.addActionListener(this);

		niv2 = new JRadioButtonMenuItem("Niveau 2", false);
		menuNiveaux.add(niv2);
		niveaux.add(niv2);
		niv2.addActionListener(this);

		niv3 = new JRadioButtonMenuItem("Niveau 3", false);
		menuNiveaux.add(niv3);
		niveaux.add(niv3);
		niv3.addActionListener(this);
		
		fenetre.pack();


	}
	public void drapeauPose()
	{
		nbdrapeaux--;
		nbMines.setText(String.valueOf(nbdrapeaux));
	}
	public void drapeauRetirer()
	{
		nbdrapeaux++;
		nbMines.setText(String.valueOf(nbdrapeaux));
	}

	public void partiePerdue()
	{
		t.stop();
		JOptionPane.showMessageDialog(fenetre,"Vous avez perdu!");
		
	}
	public void partieGagnee()
	{
		if(nbcasesdecouverte == Largeur*Hauteur-Nb_Mines )
		{
			t.stop();
			
		}
		
		
		
	}
	
	public void compteDecouverte()
	{
		nbcasesdecouverte++;
		//System.out.print(nbcasesdecouverte);
	}
	
	public void demarrePartie()
	{
		t.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{

		Object source = e.getSource(); 
		
		if(source== t)
		{
			points++;
			Score.setText(String.valueOf(points));
		}
		if (source == NouvellePartie || source == boutonNouvellePartie)
		{
			t.stop();
			c.nvChamp();
			nbdrapeaux=Nb_Mines;
			c.setMinesadjacentes( Largeur,Hauteur);
			c.afficher();
			nbcasesdecouverte = 0;
			points = 0;
			Score.setText(String.valueOf(points));
		}
		else if (source == quitter)
			System.exit(0);
		else if (source == niv1)
		{
			t.stop();
			panneauPrincipal.remove(c);
			Nb_Mines = NB_MINES_FACILE;
			Largeur = LONGUEUR_FACILE;
			Hauteur = HAUTEUR_FACILE;
			nbdrapeaux=Nb_Mines;
			nbMines.setText(String.valueOf(nbdrapeaux));
			c=new Champ(Nb_Mines, Largeur, Hauteur,this);
			c.setMinesadjacentes( Largeur,Hauteur);
			c.afficher();
			panneauPrincipal.add(c, BorderLayout.CENTER);
			nbMines.setText(String.valueOf(Nb_Mines));
			nbcasesdecouverte = 0;
			fenetre.pack();	
			points = 0;
			Score.setText(String.valueOf(points));
		}
		else if (source == niv2)
		{
			t.stop();
			panneauPrincipal.remove(c);
			Nb_Mines = NB_MINES_MOYEN;
			Largeur = LONGUER_MOYEN;
			Hauteur = HAUTEUR_MOYEN;
			nbdrapeaux=Nb_Mines;
			nbMines.setText(String.valueOf(nbdrapeaux));
			c = new Champ(Nb_Mines, Largeur, Hauteur,this);
			c.setMinesadjacentes( Largeur,Hauteur);
			c.afficher();
			nbcasesdecouverte = 0;
			panneauPrincipal.add(c, BorderLayout.CENTER);
			nbMines.setText(String.valueOf(Nb_Mines));
			fenetre.pack();	
			points = 0;
			Score.setText(String.valueOf(points));
		}
		else if (source == niv3)
		{
			t.stop();
			panneauPrincipal.remove(c);
			Nb_Mines = NB_MINES_DIFICILE;
			Largeur = LONGEUR_DIFICILE;
			Hauteur = HAUTEUR_DIFICILE;
			nbdrapeaux=Nb_Mines;
			nbMines.setText(String.valueOf(nbdrapeaux));
			c=new Champ(Nb_Mines, Largeur,  Hauteur,this);
			c.setMinesadjacentes( Largeur, Hauteur);
			c.afficher();
			nbcasesdecouverte = 0;
			panneauPrincipal.add(c, BorderLayout.CENTER);

			nbMines.setText(String.valueOf(Nb_Mines));
			fenetre.pack();	
			points = 0;
			Score.setText(String.valueOf(points));
			
		}
	}


	public static void main(String[] args)
	{
		int niveau;
		if (args.length == 0)
			niveau=0;
		else
			 niveau =Integer.parseInt(args[0]);
		Demineur d;
		 switch (niveau) {
            		case 1:  
				 d= new Demineur(NB_MINES_FACILE, LONGUEUR_FACILE, HAUTEUR_FACILE );
                     		break;
            		case 2: 
				 d= new Demineur(NB_MINES_MOYEN ,LONGUER_MOYEN , HAUTEUR_MOYEN);
                     		break;
            		case 3:  
				 d= new Demineur(NB_MINES_DIFICILE, LONGEUR_DIFICILE , HAUTEUR_DIFICILE);
				break;
			case 0:  
				 d= new Demineur(NB_MINES_FACILE , LONGUEUR_FACILE , HAUTEUR_FACILE);
                     		break;
		}
	}

}
