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
        int j=0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir l'ensemble des lettres :");
		while (j==0) {
		String str = sc.nextLine();
		if(!str.equals("")) {
		char c = str.charAt(0);
		x.add(c);
		}else j++;
		}
		j=0;
		System.out.println("Veuillez saisir l'ensemble des états  :");
		while (j==0) {
		String str = sc.nextLine();
		if(!str.equals("")) {
		char c = str.charAt(0);
		Etat etat=new Etat(c);
		s.add(etat);
		}else j++;
		}
		System.out.println("Veuillez saisir l'états initiale  :");
		String str = sc.nextLine();
		char c = str.charAt(0);
		s0=new Etat(c);
		
		j=0;
		System.out.println("Veuillez saisir l'ensemble des états finaux :");
		while (j==0)  {
		String str1 = sc.nextLine();
		if(!str1.equals("")) {
		char c1 = str1.charAt(0);
		Etat etat=new Etat(c1);
		f.add(etat);
		}else j++;
		}
		
		j=0;
		System.out.println("Veuillez saisir l'ensemble des instructions  :");
		while (j==0)  {
		System.out.println("si :");
		String si = sc.nextLine();
		char c1 = si.charAt(0);
		Etat etat1=new Etat(c1);
		System.out.println("l :");
		String l = sc.nextLine();
		char c2 = l.charAt(0);
		System.out.println("sj :");
		String sj = sc.nextLine();
		char c3 = sj.charAt(0);
		Etat etat2=new Etat(c3);
		Instruction instruction=new Instruction(etat1,c2,etat2);
		i.add(instruction);
		System.out.println("Veuillez cintunier ?0/1 :");
		String str1 = sc.nextLine();
		if(!str1.equals("0")) j++;
		
		
		}
		
			
			a=new  Automate(x, s,s0, f,i);
			a.afficherAutomate();
			ArrayList<Etat> etatA=new ArrayList<Etat>();
			ArrayList<Etat> etatCo=new ArrayList<Etat>();
			ArrayList<Instruction> instr=new ArrayList<Instruction>();
			etatA=a.etatsAccessible();
			etatCo=a.etatsCoAccessible();
			System.out.println("les etats accessivles ");

			for(int m=0;m<etatA.size();m++)
			{
				etatA.get(m).afficherEtat();

			}
			System.out.println("\nles etats COaccessivles ");
			for(int m=0;m<etatCo.size();m++)
			{
				etatCo.get(m).afficherEtat();

			}
			
		   instr= a.instructionAutRe(etatA);
			System.out.println("\nles instruction ");
			for(int m=0;m<instr.size();m++)
			{
				instr.get(m).afficherInstruction();

			}
			
			Automate reduit=a.automateReduit();
			reduit.afficherAutomate();
			
			
	}

}
