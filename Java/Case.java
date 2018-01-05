import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class Case extends JPanel 
{
	private boolean presenceMine;
	private int nbMinesVoisines;
	private boolean caseDecouverte;
	private boolean drapeau;
	private ImageIcon mine= new ImageIcon("mine.jpg");
	private EcouteurJeu ec;
	private int x,y;

	public Case(boolean Mine,EcouteurJeu e,int i, int j)
	{
		x=i;
		y=j;
		caseDecouverte=false;
		presenceMine = Mine;
		drapeau=false;
		setPreferredSize ( new Dimension(22,22));
		setBorder (BorderFactory.createRaisedBevelBorder());
		ec=e;
	}
	public boolean getPresenceMine()
	{
		return presenceMine;
	}
	
	public void setPresenceMine(boolean mine )
	{
		presenceMine=mine;
	}

	public int getNbMinesVoisines()
	{
		return nbMinesVoisines;
	}
	public void setNbMinesVoisines(int nbvoisins)
	{
		nbMinesVoisines = nbvoisins;
	}
	public boolean getcaseDecouverte()
	{
		return caseDecouverte;
	}
	public boolean getdrapeau()
	{
		return drapeau;
	}
	public int getXtab()
	{
		return x;
	}
	public int getYtab()
	{
		return y;
	}

	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		if (caseDecouverte)
		{
			if(getPresenceMine())
			{
				
				mine.paintIcon(this,g,2,2);
			}
			else
			{
				g.drawString(String.valueOf(getNbMinesVoisines()), 7,15);
			}
		}
		else
		{
			if(drapeau)
			{
				g.drawLine(9,9,9,20);
				g.drawPolygon(new int[] {9,9,15}, new int[] {3,10,6}, 3);
				Color rouge=Color.RED;
				g.setColor(rouge);
				g.fillPolygon(new int[] {9,9,15}, new int[] {3,10,6}, 3);
			}
		}
	}
	public void afficherDrapeau()
	{
		drapeau=true;
		ec.drapeauPose();
		repaint();
	}
	public void cacherDrapeau()
	{
		drapeau=false;
		ec.drapeauRetirer();
		repaint();
	}
	public void perdu()
	{
		ec.partiePerdue();
	}
	
	public void gagnee()
	{
		ec.partieGagnee();
	}
	
	public void demarre()
	{
		ec.demarrePartie();
	}
	
	public void afficherCase()
	{
		setBorder (BorderFactory.createLoweredBevelBorder());
		caseDecouverte=true;
		ec.compteDecouverte();
		
		repaint();
	}
	public void couvrirCase()
	{
		setBorder (BorderFactory.createRaisedBevelBorder());
		caseDecouverte=false;
		repaint();
	}	



}
