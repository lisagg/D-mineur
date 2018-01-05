import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Score implements Serializable {

	private int[] scores;
	private String[] noms;
	
	public Score(int[] score, String[] nom)
	{
		scores = new int[3];
		noms = new String[3];
		for(int i=0; i<3 ; i++)
		{
			scores[i] =score[i];
			noms[i] = nom[i];
		}
	}
	
	public void afficher()
	{
		for(int i=0; i<3 ; i++)
		{
			System.out.print(scores[i]); ;
			System.out.print(noms[i]); 
			System.out.print("\n"); 
		}
	}
	
	
}
