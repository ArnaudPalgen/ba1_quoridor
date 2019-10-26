/**
 * 
 */
package game;


import players.EasyAI;
import players.Player;
import players.RandomAI;

/**
 * Fournit des statistiques sur un nombre partie déterminé entre deux intelligence artificielles
 * @author Arnaud.P
 *
 */
public class MainStat {
	private Game jeu;
	private Player joueur1,joueur2;
	private Player[] joueurs;
	private double [] tempsPartie;//regroupe le temps de chaque partie en millisecondes
	private int[] statJ1;
	private int[] statJ2;
	/*
	 * dans les stats par joueur: 
	 * nombre de parties gagnées en tant que j1,[0]
	 * nombre de parties gagnées en tant que j2, [1]
	 * nombre de parties gagnées, [2]
	 * nombre de murs placés,[3]
	 * nombre de déplacements, [4]
	 * nombre de coups[5]
	 */
	
	/**
	 * @param args
	 * @author Arnaud.P
	 */
	public static void main(String[] args) {
		int nbrPartie=Integer.parseInt(args[0]);
		String typePlayer1=args[1];
		String typePlayer2=args[2];
		
		MainStat stat=new MainStat(nbrPartie,typePlayer1,typePlayer2);
		stat.makeStat(nbrPartie);
		stat.printStat();

	}
	
	/**
	 * @author Arnaud.P
	 * @param nbrPartie
	 * @param typePlayer1
	 * @param typePlayer2
	 */
	public MainStat(int nbrPartie,String typePlayer1,String typePlayer2) {
		typePlayer1=typePlayer1.toUpperCase();
		typePlayer2=typePlayer2.toUpperCase();
		
		if(typePlayer1.equals("ALEATOIRE")){
			joueur1=new RandomAI("IA ALEATOIRE");
		}else{
			joueur1=new EasyAI("IA FACILE");
		}
		
		if(typePlayer2.equals("ALEATOIRE")){
			joueur2=new RandomAI("IA ALEATOIRE");
		}else{
			joueur2=new EasyAI("IA FACILE");
		}
		
		joueurs=new Player[2];
		joueurs[0]=joueur1; 
		joueurs[1]=joueur2;
		jeu=new Game(joueurs, null, null);
		
		statJ1=new int[8];
		for (int i = 0; i < statJ1.length; i++) {
			statJ1[i]=0;
		}
		
		statJ2=new int[8];
		for (int i = 0; i < statJ2.length; i++) {
			statJ2[i]=0;
		}
		tempsPartie=new double[nbrPartie];
	
	}
	
	/**
	 * Remplis les tableaux de statistiques
	 * @author Arnaud.P
	 * @param nbrParties
	 */
	private void makeStat(int nbrParties){
		int indexTemps=0;//position à laquelle ajouter un temps de partie dans le tableaux tempsPartie
		while(nbrParties >0){//tant qu'il reste des parties à faire

			boolean gagne=false;
			Player joueurAjouer=null;
			long beforeTime=System.currentTimeMillis();
			while(!gagne){//tant qu'aucun joueur n'a gagné on joue
				joueurAjouer=jeu.whichPlayer();
				
				int value=joueurAjouer.play(jeu.getGameboard(),joueurs, null, null);
				gagne=jeu.win(joueurAjouer);
				
				while(gagne==false &&value!=GameBoard.OK){
					value=joueurAjouer.play(jeu.getGameboard(),joueurs, null, null);
					gagne=jeu.win(joueurAjouer);
					

				}
				
			}

			long afterTime=System.currentTimeMillis();
			tempsPartie[indexTemps]=afterTime-beforeTime;
			indexTemps++;
			
			if(joueurAjouer==joueur1){
				if(joueur1.getNbrJoueur()==1){// signifie que c'est lui qui a commence la partie
					statJ1[0]+=1;//on incremente le nombre de parties gagnées en tant que J1
				}else{
					statJ1[1]+=1;//on incremente le nombre de parties gagnées en tant que J2
				}
				statJ1[2]+=1;// on incremente le nombre de parties totales
			}
			
			if(joueurAjouer==joueur2){
				if(joueur2.getNbrJoueur()==1){// signifie que c'est lui qui a commence la partie
					statJ2[0]+=1;//on incremente le nombre de parties gagnées en tant que J1
				}else{
					statJ2[1]+=1;//on incremente le nombre de parties gagnées en tant que J2
				}
				statJ2[2]+=1;// on incremente le nombre de parties totales
			}
			
			statJ1[3]+=Player.getNbrMurDepart()-joueur1.getNombreMur();
			statJ1[4]+=joueur1.getNbrDeplacement();
			
			statJ2[3]+=Player.getNbrMurDepart()-joueur2.getNombreMur();
			statJ2[4]+=joueur2.getNbrDeplacement();
			resetGame();
			nbrParties--;
		}
		statJ1[5]+=statJ1[3]+statJ1[4];
		statJ2[5]+=statJ2[3]+statJ2[4];
	}
	
	/**
	 * Rénitialise une partie quand elle est terminée pour pouvoir en recommencer une autre:
	 * on change les numéro des joueurs pour alterné celui qui commence et on rénitialise le nombre de déplacementss d'un joueur
	 * @author Arnaud.P
	 */
	private void resetGame(){	
		
		if(joueur1.getNbrJoueur()==1){
			joueur1.setNombreMur(Player.getNbrMurDepart());
			joueur1.setNbrDeplacement(0);
			joueur1.setNbrJoueur(2);
			joueur1.setPion(null);
		}
		else if(joueur1.getNbrJoueur()==2){
			joueur1.setNombreMur(Player.getNbrMurDepart());
			joueur1.setNbrDeplacement(0);
			joueur1.setNbrJoueur(1);
			joueur1.setPion(null);
		}
		
		if(joueur2.getNbrJoueur()==2){
			joueur2.setNombreMur(Player.getNbrMurDepart());
			joueur2.setNbrDeplacement(0);
			joueur2.setNbrJoueur(1);
			joueur2.setPion(null);
		}
		else if (joueur2.getNbrJoueur()==1){
			joueur2.setNombreMur(Player.getNbrMurDepart());
			joueur2.setNbrDeplacement(0);
			joueur2.setNbrJoueur(2);
			joueur2.setPion(null);

		}
		if(joueurs[0]==joueur1){
			joueurs[0]=joueur2;
			joueurs[1]=joueur1;
		}else{
			joueurs[1]=joueur2;
			joueurs[0]=joueur1;
		}

		
		jeu=new Game(joueurs,null ,null);

		
		
	}

	/**
	 * Affiche les statistiques dans la console
	 * @author Arnaud.P
	 */
	public void printStat(){
		double tempsMoyen;
		double tempsTotal=0;
		for (double temps : tempsPartie) {
			tempsTotal+=temps;
		}
		tempsMoyen=tempsTotal/tempsPartie.length;
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Durée moyenne d'une partie: "+tempsMoyen+" millisecondes\n");
		System.out.println("----------------------------------------------------------------------");
		
		System.out.println(joueur1.getNom()+":");
		System.out.println("Nombre de parties gagnées en tant que premier joueur: "+ statJ1[0]);
		System.out.println("Nombre de parties gagnées en tant que deuxième joueur: "+ statJ1[1]);
		System.out.println("Nombre de parties gagnées sur "+tempsPartie.length+" parties: "+statJ1[2]);
		System.out.println("Nombre de mur placés sur les "+tempsPartie.length+" parties: "+statJ1[3]);
		System.out.println("Nombre de déplacements effectués sur les "+tempsPartie.length+" parties: "+statJ1[4]);
		System.out.println("Nombre de coups jouées sur les "+tempsPartie.length+" parties: "+statJ1[5]);
		System.out.println("----------------------------------------------------------------------");
		
		System.out.println(joueur2.getNom()+":");
		System.out.println("Nombre de parties gagnées en tant que premier joueur: "+ statJ2[0]);
		System.out.println("Nombre de parties gagnées en tant que deuxième joueur: "+ statJ2[1]);
		System.out.println("Nombre de parties gagnées sur "+tempsPartie.length+" parties: "+statJ2[2]);
		System.out.println("Nombre de mur placés sur les "+tempsPartie.length+" parties: "+statJ2[3]);
		System.out.println("Nombre de déplacements effectués sur les "+tempsPartie.length+" parties: "+statJ2[4]);
		System.out.println("Nombre de coups jouées sur les "+tempsPartie.length+" parties: "+statJ2[5]);
		System.out.println("----------------------------------------------------------------------\n");
		
	}
}
