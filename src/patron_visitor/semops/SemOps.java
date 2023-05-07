package patron_visitor.semops;

import patron_visitor.asint.TinyASint;

public class SemOps extends TinyASint {
   public Exp exp(String op, Exp arg0, Exp arg1) {
       switch(op) {
           case "+": return new Suma(arg0,arg1);
           case "-": return new Resta(arg0,arg1);
           case "*": return new Mul(arg0,arg1);
           case "/": return new Div(arg0,arg1);
           case "%": return new Mod(arg0, arg1);
           case ">": return new Mayor(arg0, arg1);
           case "<": return new Menor(arg0, arg1);
           case "and": return new And(arg0, arg1); 
           case "or": return new Or(arg0,arg1);
           case "<=": return new MenorIgual(arg0, arg1);
           case ">=": return new MayorIgual(arg0, arg1);
           case "==": return new Igual(arg0, arg1);
           case "!=": return new Distinto(arg0, arg1);
           
       }
       throw new UnsupportedOperationException("exp "+op);
   }  
   
   public Exp exp(String op, Exp arg0) {
       switch(op) {
           case "not": return new Not(arg0);
           case "-": return new MenosUnario(arg0);
       }
       throw new UnsupportedOperationException("exp "+op);
   }  
   
   
   public Prog prog(Instrucciones ins, Decs decs) {
       if (decs == null) return new Prog_sin_decs(ins);
       else return new Prog_con_decs(ins,decs);
   }     
}
