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
	
	System.out.print("L'etat initiale:");s0.afficherEtat();System.out.print("\n");
	
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

public Automate automateReduit()
{
	ArrayList<Etat> etatFinaux=new ArrayList<Etat>();//liste des etats finaux de l'automate réduit 
	ArrayList<Etat> accessCoaccess=this.etatsAccessible();
	ArrayList<Etat> coAccessible =this.etatsCoAccessible();
	ArrayList<Character> alphabet=new ArrayList<Character>();
	accessCoaccess.retainAll(coAccessible);//liste des etats accessible et coaccessible
	
	etatFinaux.addAll(accessCoaccess);
	etatFinaux.retainAll(f);//faire l'intersction entre les etats finaux de l'automate et liste des etats accessible et coaccessible
	
	ArrayList<Instruction> instrucrtion=this.instructionAutRe(accessCoaccess);//liste des instruction de l'automate réduit 
	for (int i=0;i<instrucrtion.size();i++) //construire l'ensemble des alphabets
	{
		if(!alphabet.contains(instrucrtion.get(i).getLettre()))
			alphabet.add(instrucrtion.get(i).getLettre());
	}
	
	 Automate automateReduit =new Automate(alphabet , accessCoaccess, s0,etatFinaux ,instrucrtion);
	return  automateReduit;
						
}

public int chercherInstruction(Etat etat,char alp)
{
	boolean trouve=false;
	int indice=-1;
	while(!trouve && indice<i.size()-1)
	{
		indice++;
		if(i.get(indice).getSi().equals(etat) && i.get(indice).getLettre()==alp)
			trouve=true;
	}
	if (trouve) return indice;
	else return -1;
}

public ArrayList<Integer> chercherInstruction2(Etat etat,char alp)
{
	int indice=-1;
	ArrayList<Integer> indices=new ArrayList<Integer>();
	while(indice<i.size()-1)
	{
		indice++;
		if(i.get(indice).getSi().equals(etat) && i.get(indice).getLettre()==alp)
			indices.add(indice);		
	}	
	 return indices;
}



public boolean reconnaissanceMot(String mot)
{
	boolean trouve=true;
	int j=0;
	Etat etat=s0;
	for(int m=0;m<mot.length();m++)
	{
		char c=mot.charAt(m);
		if (!x.contains(c))
			return false ;
		j=chercherInstruction(etat,c);
		if (j!=-1)
		etat=i.get(j).getSj();
		else return trouve=false;					
	}
	return trouve;
}


// swap content of two lists
public static void swapList(ArrayList<Etat> list1, ArrayList<Etat> list2) {
    ArrayList<Etat> tmpList = new ArrayList<Etat>(list1);
    list1.clear();
    list1.addAll(list2);
    list2.clear();
    list2.addAll(tmpList);
}

public Automate automateComp() {
	Automate autom=this.automateDeterm();
	autom=autom.automateReduit();
	autom=autom.automateComplet();
    ArrayList<Etat> interm = new ArrayList<Etat>();
    ArrayList<Etat> finaux = new ArrayList<Etat>();
    finaux.addAll(autom.getF());
    for (int y = 0; y < s.size(); y++) {
        if (finaux.contains(autom.getS().get(y))) {
            finaux.remove(autom.getS().get(y));
        } else {
            interm.add(autom.getS().get(y));
        }
    }
    swapList(finaux, interm);
    Automate automateComp = new Automate(x, autom.getS(), s0, finaux, i);
    return automateComp;
}

public Automate automateMiroir() {
    Etat inter;
    ArrayList<Instruction> newInstr = new ArrayList<Instruction>();
    // si il n y a qu'un seul etat final
    if (f.size() == 1) {
        inter = s0;
        s0 = f.get(0);
        f.clear();
        f.add(inter);
    } else { // il y a plusieurs etat finaux
        x.add('*'); //mot vide (transition instantanee)
        Etat e = new Etat("p");
        s.add(e);
        for (int p = 0; p < f.size(); p++) {
            Instruction instruction = new Instruction(e, '*', f.get(p));
            newInstr.add(instruction);
        }
        f.clear();
        f.add(s0);
        s0 = e;
    }
    // inverser les transitions
    for (int q = 0; q < i.size(); q++) {
        Etat interm = i.get(q).getSi();
        i.get(q).setSi(i.get(q).getSj());
        i.get(q).setSj(interm);
    }
    i.addAll(newInstr);
    Automate automateMiroir = new Automate(x, s, s0, f, i);
    return automateMiroir;
}

//Automate complet

Automate automateComplet() {
    boolean noncomp = false;
    Etat e = new Etat("p");  //creer un etat sp tq (si,x,sp)
    for (int r = 0; r < s.size(); r++) {
        // System.out.println("r="+r);

        for (int k = 0; k < x.size(); k++) {
            //System.out.println("k="+k);
            int q = chercherInstruction(s.get(r), x.get(k));
            if (q == -1) {
                noncomp = true;
                Instruction inst1 = new Instruction(s.get(r), x.get(k), e);
                i.add(inst1);
            }
        }
    }
    if (noncomp) { // si l automate est non complet au debut
        for (int u = 0; u < x.size(); u++) {
            Instruction inst = new Instruction(e, x.get(u), e);
            i.add(inst);
        }
    }
    Automate automateComplet = new Automate(x, s, s0, f, i);
    return automateComplet;
}

//chaine existe ou pas
public boolean chaineExist (ArrayList <Etat> e,String chaine){

    boolean stop = false;
    int k =0;
    int d =0;
    while(k<e.size() && !stop ){
        if (e.get(k).getNomEtat().length() == chaine.length()){
            for (int i=0;i<chaine.length();i++){
                if(e.get(k).getNomEtat().contains(Character.toString(chaine.charAt(i)))) {
                    d++;
                }
            }
            if (d==chaine.length()){
                stop =true; }
        }
        k++;
    }
    return stop;
}
//passer d'un automate non deterministe a un automate deterministe
public Automate automateDeterm() {
       ArrayList<Etat> snew = new ArrayList<Etat>();
       ArrayList <Etat> fnew = new ArrayList <Etat> ();
       ArrayList<Integer> q = new ArrayList<Integer>();
       ArrayList<Instruction> inew = new ArrayList<Instruction>(); //la liste des instruction auto deter
   String etatconcat = "";
   //commencer par letat initial
      snew.add(s0);
   for (int k = 0; k < x.size(); k++) {
       q = chercherInstruction2(s0, x.get(k));
     
       if (q.size() > 1) {   //occurence etat/lettre plus d'une fois
           for (int j = 0; j < q.size(); j++) {
               etatconcat = etatconcat + i.get(q.get(j)).getSj().nomEtat;
           }
          // System.out.println("etat concat"+etatconcat);
           Etat enew = new Etat(etatconcat);
           Instruction instr = new Instruction(s0, x.get(k), enew);
           inew.add(instr);//ajouter une instruction
                  if (!snew.contains(enew)) {
               snew.add(enew);}//ajouter etat
       } else if (q.size() == 1) {
           if (!snew.contains(i.get(q.get(0)).getSj())){
           snew.add(i.get(q.get(0)).getSj());}
           inew.add(i.get(q.get(0)));
       }
   }
   etatconcat ="";
   q.clear();
   /**________________________________________________________________________________________________________________**/
      for (int l=1;l<snew.size();l++){//on comence a partir de 1 car letat initial est deja traitee
        
          if(snew.get(l).getNomEtat().length()>1) { //etat composee de plusieurs etats .par exp :s1s2
              for (int k = 0; k < x.size(); k++) {
               
              for (int y = 0; y < snew.get(l).getNomEtat().length(); y++) {
                  System.out.println("y"+y);
                  System.out.println("s12="+snew.get(l).getNomEtat());
                  Etat cherch = new Etat(snew.get(l).getNomEtat().substring(y,y+1));
                  q = chercherInstruction2(cherch, x.get(k));
                  System.out.println("cherch "+snew.get(l).getNomEtat().substring(y,y+1));
                  System.out.println("x"+x.get(k));
              
                  if (q.size() > 1) {   //occurence etat/lettre plus d'une fois
                      for (int j = 0; j < q.size(); j++) {
                          if (!etatconcat.contains(i.get(q.get(j)).getSj().nomEtat)) {                           
                              etatconcat = etatconcat + i.get(q.get(j)).getSj().nomEtat;                            
                          }
                      }
                  } else if (q.size() == 1) {
                      if (!etatconcat.contains(i.get(q.get(0)).getSj().getNomEtat())) {
                          etatconcat = etatconcat + i.get(q.get(0)).getSj().getNomEtat();
                      }
                  }
              }
            
                  Etat neuEtat = new Etat(etatconcat);
     
              if (etatconcat!=""){
                 
                  if(!snew.contains(neuEtat)) {snew.add(neuEtat);} //!chaineExist(snew,etatconcat)
              }
              if(etatconcat!=""){
                  Instruction instruct = new Instruction(snew.get(l),x.get(k),neuEtat);
                  inew.add(instruct);
              }
                  etatconcat="";
          }
          }else { //etat simple : s1 par exp
              for (int k = 0; k < x.size(); k++) {
                  q = chercherInstruction2(snew.get(l), x.get(k));
                  if (q.size() > 1) {   //occurence etat/lettre plus d'une fois
                      for (int j = 0; j < q.size(); j++) {
                          etatconcat = etatconcat + i.get(q.get(j)).getSj().nomEtat;
                      }
                      Etat enew = new Etat(etatconcat);
                      Instruction instr = new Instruction(snew.get(l), x.get(k), enew);
                      inew.add(instr);//ajouter une instruction
                      if (chaineExist(snew,enew.getNomEtat())) { //!snew.contains(enew)
                          snew.add(enew);} //ajouter etat
                  } else if (q.size() == 1) {
                      if (!snew.contains(i.get(q.get(0)).getSj())){
                          snew.add(i.get(q.get(0)).getSj());}
                      inew.add(i.get(q.get(0)));
                  }
              }
          }
      }
      //etats fineaux
      for (int w=0;w<f.size();w++){
          for (int t=0;t<snew.size();t++){
              if(snew.get(t).getNomEtat().contains(f.get(w).getNomEtat())){
                  if (!fnew.contains(snew.get(t))){
                  fnew.add(snew.get(t));}
              }
          }}
      Automate automateDeterm = new Automate(x, snew,s0,fnew, inew);
      return automateDeterm;
  }

}


