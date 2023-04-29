package semops;

import asint.TinyASint;

public class SemOps extends TinyASint {
   public Exp exp(String op, Exp arg0, Exp arg1) {
       switch(op) {
           case "+": return suma(arg0,arg1);
           case "-": return resta(arg0,arg1);
           case "*": return mul(arg0,arg1);
           case "/": return div(arg0,arg1);
           case "%": return mod(arg0, arg1);
           case ">": return mayor(arg0, arg1);
           case "<": return menor(arg0, arg1);
           case "and": return and(arg0, arg1); 
           case "or": return or(arg0,arg1);
           case "not": return not(arg0, arg1); // ¿arg1? ¿habria que hacerlo con un argumento?
           case "<=": return menorIgual(arg0, arg1);
           case ">=": return mayorIgual(arg0, arg1);
           case "==": return igual(arg0, arg1);
           case "!=": return distinto(arg0, arg1);
           
       }
       throw new UnsupportedOperationException("exp "+op);
   }  
   
   public Exp exp(String op, Exp arg0) {
       switch(op) {
           case "not": return not(arg0);
           case "-": return menorUnario(arg0);
           
       }
       throw new UnsupportedOperationException("exp "+op);
   }  
   
   
   public Prog prog(Exp exp, Decs decs) {
       if (decs == null) return prog_sin_decs(exp);
       else return prog_con_decs(exp,decs);
   }     
}
