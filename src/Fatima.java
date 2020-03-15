
package com.company;

import jdk.internal.org.objectweb.asm.tree.InnerClassNode;

import java.util.ArrayList;
import java.util.Set;

public class Automate {
    ArrayList<Character> x = new ArrayList<Character>(); //ensemble des alphabets
    ArrayList<Etat> s = new ArrayList<Etat>(); //ensemble des etats
    Etat s0;//etat initiale
    ArrayList<Etat> f = new ArrayList<Etat>();//ensemble des etats finaux
    ArrayList<Instruction> i = new ArrayList<Instruction>();//ensemble des instructions

    public Automate(ArrayList<Character> x, ArrayList<Etat> s, Etat s0, ArrayList<Etat> f, ArrayList<Instruction> i) {
        this.x = x;
        this.s = s;
        this.s0 = s0;
        this.f = f;
        this.i = i;
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
        ArrayList<Etat> interm = new ArrayList<Etat>();
        for (int y = 0; y < s.size(); y++) {
            if (f.contains(s.get(y))) {
                f.remove(s.get(y));
            } else {
                interm.add(s.get(y));
            }
        }
        swapList(f, interm);
        Automate automateComp = new Automate(x, s, s0, f, i);
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
    public int chercherInstruction(Etat etat, char alp) {
        boolean trouve = false;
        int indice = -1;
        while (!trouve && indice < i.size() - 1) {
            indice++;
            if (i.get(indice).getSi().equals(etat) && i.get(indice).getLettre() == alp)
                trouve = true;
        }
        if (trouve) return indice;
        else return -1;
    }

    public ArrayList<Integer> chercherInstruction2(Etat etat,char alp)
    {
       // System.out.println("etat"+etat.getNomEtat());
       // System.out.println("alp"+alp);
        int indice=-1;
        ArrayList<Integer> indices = new ArrayList<Integer>();
        while(indice<i.size()-1)
        {
             indice++;
            if(i.get(indice).getSi().equals(etat) && i.get(indice).getLettre()==alp){

                indices.add(indice); }
        }
        return indices;
    }

    Automate automateComplet() {
        boolean noncomp = false;
        Etat e = new Etat("p");  //creer un etat sp tq (si,x,sp)
        for (int r = 0; r < s.size(); r++) {
            // System.out.println("r="+r);

            for (int k = 0; k < x.size(); k++) {
                //System.out.println("k="+k);
                int q = chercherInstruction(s.get(r), x.get(k));
                System.out.println("q=" + q);
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
    public boolean chaineExist (ArrayList <Etat> e,String s){

        boolean stop = false;
        int k =0;
        int d =0;
        while(k<e.size() && !stop ){
            if (e.get(k).getNomEtat().length() == s.length()){
                for (int i=0;i<s.length();i++){
                    if(e.get(k).getNomEtat().contains(s.substring(i,i+1))){
                        d++;
                    }
                }
                if (d==s.length()){
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
        // System.out.println("S0"+s0.getNomEtat());
        // System.out.println("x"+x.get(k));
        // System.out.println("q.size"+q.size());
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
     /**______________________________________________________________________________________________________________**/
        for (int l=1;l<snew.size();l++){//on comence a partir de 1 car letat initial est deja traitee
           // System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhetatconcat "+etatconcat2);

          //  System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhetatconcat "+etatconcat2);
          //  System.out.println("HERE is snew"+snew.get(l).getNomEtat());
            if(snew.get(l).getNomEtat().length()>1) { //etat composee de plusieurs etats .par exp :s1s2
                for (int k = 0; k < x.size(); k++) {
                    //  System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhs,x "+snew.get(l).getNomEtat()+x.get(k));
                for (int y = 0; y < snew.get(l).getNomEtat().length(); y++) {
                    System.out.println("y"+y);
                    System.out.println("s12="+snew.get(l).getNomEtat());
                    Etat cherch = new Etat(snew.get(l).getNomEtat().substring(y,y+1));
                    q = chercherInstruction2(cherch, x.get(k));
                    System.out.println("cherch "+snew.get(l).getNomEtat().substring(y,y+1));
                    System.out.println("x"+x.get(k));
                   // System.out.println("q1.size"+q1.size());
                    if (q.size() > 1) {   //occurence etat/lettre plus d'une fois
                        for (int j = 0; j < q.size(); j++) {
                            if (!etatconcat.contains(i.get(q.get(j)).getSj().nomEtat)) {
                                System.out.println("iiiiiiiiiiiiiiiiiiiiiietatconcat  "+etatconcat);
                                etatconcat = etatconcat + i.get(q.get(j)).getSj().nomEtat;
                                System.out.println("iiiiiiiiiiiiiiiiiiiiiiietatconcat  "+etatconcat);
                            }
                        }
                    } else if (q.size() == 1) {
                        if (!etatconcat.contains(i.get(q.get(0)).getSj().getNomEtat())) {
                            etatconcat = etatconcat + i.get(q.get(0)).getSj().getNomEtat();
                        }
                    }
                }
               // System.out.println("etatconcat2"+etatconcat);
                    Etat neuEtat = new Etat(etatconcat);
         // System.out.println("chaine existe"+chaineExist(snew,etatconcat2));
                if (etatconcat!=""){
                    System.out.println("chaine existe"+chaineExist(snew,etatconcat));
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
                        if (!snew.contains(enew)) {
                            snew.add(enew);}//ajouter etat

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