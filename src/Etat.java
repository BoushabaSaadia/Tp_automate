
public class Etat {
char nomEtat;

public Etat(char nom)
{
	this.nomEtat=nom;
}

public char getNomEtat() {
	return nomEtat;
}

public void setNomEtat(char nomEtat) {
	this.nomEtat = nomEtat;
}

public void afficherEtat()
{
	System.out.print("S"+nomEtat);
}

public boolean equals(Object o)
{
	Etat etat=(Etat) o;
	if (this.nomEtat==etat.getNomEtat())
	return true ;
	else return false ;
}
}

