
public class Instruction {
Etat si;
char lettre;
Etat sj;

public Instruction(Etat si,char lettre,Etat sj)
{
	this.lettre=lettre;
	this.si=si;
	this.sj=sj;
}


public Etat getSi() {
	return si;
}


public void setSi(Etat si) {
	this.si = si;
}


public char getLettre() {
	return lettre;
}


public void setLettre(char lettre) {
	this.lettre = lettre;
}


public Etat getSj() {
	return sj;
}


public void setSj(Etat sj) {
	this.sj = sj;
}


public void afficherInstruction()
{
	System.out.print("{ "); si.afficherEtat();
	System.out.print(",lettre,");
	 sj.afficherEtat();System.out.print("}");
}
}
