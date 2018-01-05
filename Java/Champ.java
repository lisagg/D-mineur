import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class Champ extends JPanel implements MouseListener
{
	private int Nb_mines, largeur, hauteur;
	private Case[][] Cases;
	private boolean partiefinie;
	private boolean premierclick;
	public Champ( int nb_m, int l, int h, EcouteurJeu e)
	{
		Nb_mines=nb_m;
		largeur=l;
		hauteur=h;
		premierclick = true;
		Cases=new Case[largeur][hauteur];
		partiefinie=false;
		for (int i=0; i<largeur; i++)
		{
			for (int j=0; j<hauteur; j++)
			{
				
				Cases[i][j]=new Case(false,e,i,j); //donne la valeur a case[i][j]
				Cases[i][j].addMouseListener(this);
			}
		}
		nvChamp();
		setLayout(new GridLayout(largeur,hauteur));
		//afficher();
	}
	
	public void nvChamp()
	{
		
		for (int i=0; i<largeur; i++)
		{
			for (int j=0; j<hauteur; j++)
			{
				//Cases[i][j].setText(" - ");
				Cases[i][j].setPresenceMine(false);
				if(Cases[i][j].getcaseDecouverte() || partiefinie)
					Cases[i][j].addMouseListener(this);
				Cases[i][j].couvrirCase();
				
				if (Cases[i][j].getdrapeau())
					Cases[i][j].cacherDrapeau();
			}
		}
		premierclick = true;
		partiefinie=false;
		int k=0;
		while (k<Nb_mines)
		{
			Random x=new Random();
			int a =x.nextInt(largeur);
			Random y=new Random();
			int b=y.nextInt(hauteur);
			if (Cases[a][b].getPresenceMine()!=true)
			{
				Cases[a][b].setPresenceMine(true);
				k++;
			}

		}
	}
	
	public int voisinCase(int hauteur, int largeur, int i, int j)
	{
		int voisins =0;
		for(int x=i-1 ; x<i+2; x++)
		{
			for( int y=j-1; y<j+2 ;y++)
			{
				if(y<hauteur && y>=0 && x>=0 && x<largeur)
				{
					if(Cases[x][y].getPresenceMine())
						voisins++;
				}
			}

		}
		return voisins;
	}	



	public void setMinesadjacentes(int l, int h)
	{
		
		for (int i=0; i< largeur; i++)
		{
			for(int j=0; j< hauteur; j++)
			{
				Cases[i][j].setNbMinesVoisines(voisinCase(h,l,i,j));
			}	
		}
	}

	

	public void afficher()
	{
		//setLayout(new GridLayout(largeur,hauteur));
		for (int i=0; i<largeur; i++)
		{
			for (int j=0; j<hauteur; j++)
			{
				add(Cases[i][j]);
				if (Cases[i][j].getPresenceMine()==true)
				{
					System.out.print("M");
				}
				else
				{
					System.out.print(Cases[i][j].getNbMinesVoisines());
				}
			}
			System.out.print("\n");
		}
	}
	public void decouvrirCases(Case cas)
	{
		cas.afficherCase();
		/*System.out.print(cas.getPresenceMine());
		System.out.print("\n");
		System.out.print(cas.getNbMinesVoisines());
		System.out.print("\n");*/
		if(!cas.getPresenceMine() && cas.getNbMinesVoisines()==0)
			{
				for(int x=cas.getXtab()-1 ; x<cas.getXtab()+2; x++)
				{
					for( int y=cas.getYtab()-1; y<cas.getYtab()+2 ;y++)
					{
						if(y<hauteur && y>=0 && x>=0 && x<largeur && !Cases[x][y].getcaseDecouverte())
						{
							decouvrirCases(Cases[x][y]);
						}
					}

				}	
			}
			
	}	

	public void mouseClicked(MouseEvent e) 
	{
		Case CaseClicked = (Case) e.getSource();
		if(premierclick)
		{
			CaseClicked.demarre();
			premierclick= false;
		}
		if(SwingUtilities.isLeftMouseButton(e))
		{	
			if(CaseClicked.getdrapeau())
				CaseClicked.cacherDrapeau();
			
			decouvrirCases(CaseClicked);
			CaseClicked.removeMouseListener(this);
			if(CaseClicked.getPresenceMine())
			{	
				CaseClicked.perdu();
				for(int i=0; i<largeur ; i++)
				{
					for(int j=0; j<hauteur ; j++)
					{
						Cases[i][j].removeMouseListener(this);
					}
				}
				partiefinie=true;
			}
			else
			{
				CaseClicked.gagnee();
			}
			
		}
		else
		{
			if(!CaseClicked.getcaseDecouverte())
			{
				if(!CaseClicked.getdrapeau())
					CaseClicked. afficherDrapeau();
				else
					CaseClicked. cacherDrapeau();
			}
		}
   		
   	}
	public void mousePressed(MouseEvent e) {}

   	public void mouseReleased(MouseEvent e) {}

    	public void mouseEntered(MouseEvent e) {}

   	public void mouseExited(MouseEvent e) {}

	
	
}

