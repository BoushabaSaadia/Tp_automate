import java.util.ArrayList;
import java.util.Set;

public class Automate {
ArrayList<Character> x=new ArrayList<Character>(); //ensemble des alphabets 
ArrayList<Etat> s=new ArrayList<Etat>(); //ensemble des etats 
Etat s0;//etat initiale 
ArrayList<Etat> f=new ArrayList<Etat>();//ensemble des etats finaux
ArrayList<Instruction> i=new ArrayList<Instruction>();//ensemble des instructions 

public Automate(ArrayList<Character> x,ArrayList<Etat> s,Etat s0,ArrayList<Etat> f,ArrayList<Instruction> i)
{
	this.x=x;
	this.s=s;
	this.s0=s0;
	this.f=f;
	this.i=i;
}


public ArrayList<Character> getX() {
	return x;
}


public void setX(ArrayList<Character> x) {
	this.x = x;
}


public ArrayList<Etat> getS() {
	return s;
}


public void setS(ArrayList<Etat> s) {
	this.s = s;
}


public Etat getS0() {
	return s0;
}


public void setS0(Etat s0) {
	this.s0 = s0;
}


public ArrayList<Etat> getF() {
	return f;
}


public void setF(ArrayList<Etat> f) {
	this.f = f;
}


public ArrayList<Instruction> getI() {
	return i;
}


public void setI(ArrayList<Instruction> i) {
	this.i = i;
}


public void afficherAutomate()
{
	System.out.print("Ensemble des alphabets :{");
	for(int i=0;i<x.size();i++) {
		System.out.print(x.get(i)+",");
	}
	System.out.print("}\n");
	
	System.out.print("Ensemble des etats :{");
	for(int i=0;i<s.size();i++) {
		s.get(i).afficherEtat();
		System.out.print(",");
	}
	System.out.print("}\n");
	
	System.out.print("L'etat initiale :");s0.afficherEtat();
	
	System.out.print("Ensemble des etats finaux :{");
	for(int i=0;i<f.size();i++) {
		f.get(i).afficherEtat();
		System.out.print(",");
	}
	System.out.print("}\n");
	
	
	System.out.print("Ensemble des instructions  :{");
	for(int j=0;j<i.size();j++) {
		i.get(j).afficherInstruction();
		System.out.print(",");
	}
	System.out.print("}\n");
}

public ArrayList<Etat> etatsAccessible(){
ArrayList<Etat> etatsA=new ArrayList<Etat>();
boolean exist=true;
etatsA.add(s0);

while(exist)
{
	exist=false;
for (int j=0;j<i.size();j++)	
{	
	if (etatsA.contains(i.get(j).getSi()) &&  !etatsA.contains(i.get(j).getSj()) )
	{
	exist=	true;
	etatsA.add(i.get(j).getSj());
	}
}
}	
	return etatsA;
	
}

public ArrayList<Etat> etatsCoAccessible() {
	ArrayList<Etat> etatCo=new ArrayList<Etat>();
	etatCo.addAll(f);
	boolean exist=true;
	while (exist) {
		exist=false;
		for (int j=0;j<i.size();j++) {
			if (etatCo.contains(i.get(j).getSj()) && !etatCo.contains(i.get(j).getSi())) {
				exist=true;
				etatCo.add(i.get(j).getSi());
			}
		}
	}
	return etatCo;
}

public ArrayList<Instruction> instructionAutRe(ArrayList<Etat> listEt)
{
	ArrayList<Instruction> instr=new ArrayList<Instruction>();
	for(int j=0;j<i.size();j++) {
		if(listEt.contains(i.get(j).getSi()) && listEt.contains(i.get(j).getSj()) )
			instr.add(i.get(j));
	}
	return instr;
}
 














}


