
public class Etat {
String nomEtat;

public Etat(String nom)
{
	this.nomEtat=nom;
}

public String getNomEtat() {
	return nomEtat;
}

public void setNomEtat(String nomEtat) {
	this.nomEtat = nomEtat;
}

public void afficherEtat()
{
	System.out.print("S"+nomEtat);
}

public boolean equals(Object o)
{
	Etat etat=(Etat) o;
	if (this.nomEtat.equals(etat.getNomEtat()))
	return true ;
	else return false ;
}
}


