package patron_visitor.tiny;

import patron_visitor.c_ast_ascendente.AnalizadorLexico;
import patron_visitor.c_ast_ascendente.ConstructorAST;
import patron_visitor.asint.TinyASint.*;
import patron_visitor.c_ast_ascendente.ClaseLexica;
import patron_visitor.c_ast_ascendente.GestionErrores;
import patron_visitor.c_ast_ascendente.UnidadLexica;
import patron_visitor.asint.TinyASint;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.String;


import patron_visitor.procesamientos.Evaluacion;
import patron_visitor.procesamientos.Impresion;
import patron_visitor.procesamientos.Vinculacion;

public class Main {
   public static void main(String[] args) throws Exception {
     if (args[0].equals("-lex")) {  
         // ejecuta_lexico(args[1]);
     }
     else {
    	 
    	 
         Prog prog = null;
         /*
         if (args[0].equals("-asc"))
            prog = ejecuta_ascendente(args[1]);
         else if (args[0].equals("-desc"))
            prog = ejecuta_descendente(args[1]);
         else 
            prog = ejecuta_descendente_manual(args[1]);
         */
         
         // Progama que asigna a "suma" el resultado de sumar 3 y 5
         prog = new Prog_sin_decs(new Ins_una(new Asignacion(new Id(new StringLocalizado("suma")), 
        		 new Suma(new NumEnt(new StringLocalizado("3")), new NumEnt((new StringLocalizado("5")))))));
        // prog.procesa(new Impresion());
         // prog.procesa(new Evaluacion());
         prog.procesa(new Vinculacion());
     }
   }


/*
private static void ejecuta_lexico(String in) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(in));
     AnalizadorLexico alex = new AnalizadorLexico(input);
     GestionErrores errores = new GestionErrores();
     UnidadLexica t = (UnidadLexica) alex.next_token();
     while (t.clase() != ClaseLexica.EOF) {
         System.out.println(t);
         t = (UnidadLexica) alex.next_token();   
     }
   }
   private static Prog ejecuta_ascendente(String in) throws Exception {       
     Reader input = new InputStreamReader(new FileInputStream(in));
     AnalizadorLexico alex = new AnalizadorLexico(input);
     ConstructorAST constructorast = new ConstructorAST(alex);
     return (Prog)constructorast.parse().value;
  }
   private static Prog ejecuta_descendente(String in) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(in));
     c_ast_descendente.ConstructorAST constructorast = new c_ast_descendente.ConstructorAST(input);
     return constructorast.Init();
   }
   private static Prog ejecuta_descendente_manual(String in) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(in));
     c_ast_descendente_manual.ConstructorAST constructorast = new c_ast_descendente_manual.ConstructorAST(input);
     return constructorast.Init();
   }
  */
   
   
}   
   
