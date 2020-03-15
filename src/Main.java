import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Automate a;
		ArrayList<Character> x=new ArrayList<Character>();
		ArrayList<Etat> s=new ArrayList<Etat>();
		Etat s0;
		ArrayList<Etat> f=new ArrayList<Etat>();
		ArrayList<Instruction> i=new ArrayList<Instruction>();
		ArrayList<Etat> etatA=new ArrayList<Etat>();
        int j=0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir l'ensemble des lettres :(tappez entrer pour sortir!!)");
		while (j==0) {
		String str = sc.nextLine();
		if(!str.equals("")) {
		char c = str.charAt(0);
		if(!x.contains(c))
		x.add(c);
		}else j++;
		}
		j=0;
		System.out.println("Veuillez saisir l'ensemble des états (tappez entrer pour sortir!!) :");
		while (j==0) {
		String str = sc.nextLine();
		if(!str.equals("")) {
		Etat etat=new Etat(str);
		if(!s.contains(etat))
		s.add(etat);
		}else j++;
		}
		System.out.println("Veuillez saisir l'états initiale  :");
		String str = sc.nextLine();
		s0=new Etat(str);
		
		j=0;
		System.out.println("Veuillez saisir l'ensemble des états finaux (tappez entrer pour sortir!!):");
		while (j==0)  {
		String str1 = sc.nextLine();
		if(!str1.equals("")) {
		char c1 = str1.charAt(0);
		Etat etat=new Etat(str1);
		if(!f.contains(etat))
		f.add(etat);
		}else j++;
		}
		
		j=0;
		System.out.println("Veuillez saisir l'ensemble des instructions  :");
		while (j==0)  {
		System.out.println("si :");
		String si = sc.nextLine();
		
		Etat etat1=new Etat(si);
		System.out.println("l :");
		String l = sc.nextLine();
		char c2 = l.charAt(0);
		System.out.println("sj :");
		String sj = sc.nextLine();
		String c3 = sj;
		Etat etat2=new Etat(c3);
		Instruction instruction=new Instruction(etat1,c2,etat2);
		i.add(instruction);
		System.out.println("Veuillez continuer ? tappez 0 si oui :");
		String str1 = sc.nextLine();
		if(!str1.equals("0")) j++;
		
		
		}
		
			
			a=new  Automate(x, s,s0, f,i);
			a.afficherAutomate();
			System.out.println("les actions possibles sur l'automate");
			System.out.println("1-Le passage d'un automate non déterministe a un automate deterministe\n"
					+ "2-La réduction d’un automat\n"
					+ "3-Le complément d’un automate\r\n" + 
					"4-Le miroir d’un automate\r\n" + 
					"5-La reconnaissance de mots dans un automate déterministe"
					+ "0-exit");
			System.out.println("Veuillez saisir le numéro d'action :\n");			
			int action = sc.nextInt();
			while (action!=0) {
			
			switch(action) {
			case 1:
				Automate determinite=a.automateDeterm();
				determinite.afficherAutomate();
				break;
			case 2:					
					Automate reduit=a.automateReduit();
					reduit.afficherAutomate();
					break;
			case 3:
				
				Automate autocomplet=a.automateComp();
				autocomplet.afficherAutomate();
				break;
			case 4:
				Automate autoMiroir=a.automateMiroir();
				autoMiroir.afficherAutomate();
				break;
			case 5:
				
				System.out.println("Veuillez saisir le mot\n :");
				Scanner sc2 = new Scanner(System.in);
				String mot = sc2.nextLine();
			
				System.out.println("Resultat="+a.reconnaissanceMot(mot));
				break;
			}
			System.out.println("Veuillez saisir le numéro d'action :\n");			
			 action = sc.nextInt();
			}
		
			
	}

}
