package c_ast_ascendente;

import asint.TinyASint.SL;
import java_cup.runtime.Symbol;

public class UnidadLexica extends Symbol {
   public UnidadLexica(int fila, int col, int clase, String lexema) {
     super(clase,null);
     value = new SL(lexema,fila,col);
   }
   public int clase () {return sym;}
   public SL lexema() {return (SL)value;}
   public String toString() {
       SL lexema = (SL)value;
       return "[clase="+clase2string(sym)+",lexema="+lexema+",fila="+lexema.fila()+",col="+lexema.col()+"]";
   }
   private String clase2string(int clase) {
       switch(clase) {
         case ClaseLexica.NUM: return "NUM";
         case ClaseLexica.COMA: return "COMA";
         case ClaseLexica.MENOS: return "MENOS";
         case ClaseLexica.PAP: return "PAP";
         case ClaseLexica.POR: return "POR";
         case ClaseLexica.EOF: return "EOF";
         case ClaseLexica.MAS: return "MAS";
         case ClaseLexica.ID: return "ID";
         case ClaseLexica.DIV: return "DIV";
         case ClaseLexica.IGUAL: return "IGUAL";
         case ClaseLexica.EVALUA: return "EVALUA";
         case ClaseLexica.PCIERRE: return "PCIERRE";
         case ClaseLexica.DONDE: return "DONDE";
         default: return "?";               
       }
    }
}
