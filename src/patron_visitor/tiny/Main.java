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
	   /*
     if (args[0].equals("-lex")) {  
         // ejecuta_lexico(args[1]);
     }
     else {
    	 
    	 
         Prog_con_decs prog = null;
         prog = new Prog_con_decs(new Decs_muchas(new Decs_vacia(), new DecVar(new Int(), new SL("suma"))), 
        		 new Ins_una(new Asignacion(new Id(new SL("suma")), 
						 new Suma(new NumEnt(new SL("3")), new NumEnt((new SL("5")))))));
		*/
	   
	   Prog_con_decs prog = (Prog_con_decs)codigoEjemplo();
         /*
         if (args[0].equals("-asc"))
            prog = ejecuta_ascendente(args[1]);
         else if (args[0].equals("-desc"))
            prog = ejecuta_descendente(args[1]);
         else 
            prog = ejecuta_descendente_manual(args[1]);
         */
         
         // Progama que asigna a "suma" el resultado de sumar 3 y 5
         int x = 30;
        		
        // prog.procesa(new Impresion());
         // prog.procesa(new Evaluacion());
         Vinculacion vin = new Vinculacion();
         vin.procesa(prog);
        //  prog.procesa(new Vinculacion());
         int y = 43;
     }
   
   public static Prog codigoEjemplo() {
	   Dec dectype1 = new DecTipo(new SL("tArbol"), new Pointer(new Ref(new SL("tNodo"))));
	   Dec dectype2 = new DecTipo(new SL("tNodo"), new RecordTipo(
	   		new Campos_muchos(new Campos_muchos(new Campos_uno(
	   				new Campo(new SL("nombre"), new StringTipo())),
	   				new Campo(new SL("izq"), new Ref(new SL("tArbol")))),
	   				new Campo(new SL("der"), new Ref(new SL("tArbol"))))));
	   Dec dectype3 = new DecTipo(new SL("tListaNombres"), new RecordTipo(
	   		new Campos_muchos(new Campos_uno(
	   				new Campo(new SL("nombres"), new Array(50, new StringTipo()))),
	   				new Campo(new SL("cont"), new Int()))));

	   Dec decvar1 = new DecVar(new Ref(new SL("tListaNombres")), new SL("nombres"));
	   Dec decvar2 = new DecVar(new Ref(new SL("tArbol")), new SL("arbol"));	

	   Dec decproc1 = new DecProc(new SL("lee_nombres"),
	   		new Pf_muchos(new Pf_vacio(), new Pf_ref(new SL("nombres"), new Ref(new SL("tListaNombres")))),
	   		new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
	   				new Write(new StringExp(new SL("Introduce el número a ordenar")))),
	   				new NewLine()),
	   				new Read(new AccesoRegistro(new Id(new SL("nombres")), new SL("cont")))),
	   				new While(new Or(new Menor(new AccesoRegistro(new Id(new SL("nombres")), new SL("cont")), new NumEnt(new SL("10"))),
	   													 new Mayor(new AccesoRegistro(new Id(new SL("nombres")), new SL("cont")), new NumEnt(new SL("50")))),
	   						new Ins_muchas(new Ins_muchas(new Ins_una(
	   								new Write(new StringExp(new SL("Introduce el número a ordenar")))),
	   								new NewLine()),
	   								new Read(new AccesoRegistro(new Id(new SL("nombres")), new SL("cont")))))),
	   				new Asignacion(new Id(new SL("i")), new NumEnt(new SL("0")))),
	   		new Write(new StringExp(new SL("Introduce un nombre en cada línea: ")))),
	   		new NewLine()),
	   		
	   		new While(new Menor(new Id(new SL("i")), new AccesoRegistro(new Id(new SL("nombres")), new SL("cont"))), new Ins_muchas(new Ins_una(
	   				new Read(new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
	   				new Asignacion(new Id(new SL("i")), new Suma(new Id(new SL("i")), new NumEnt(new SL("1"))))))),
	   		
	   		new Decs_muchas(new Decs_vacia(), new DecVar(new Int(), new SL("i"))));
	   
	   
	   Dec decproc2 = new DecProc(new SL("construye_arbol"),
				new Pf_vacio(),
				new Ins_muchas(new Ins_muchas(new Ins_una(
						new Asignacion(new Id(new SL("arbol")), new NullExp())),
						new Asignacion(new Id(new SL("i")), new NumEnt(new SL("0")))),
						new While(
								new Menor(new Id(new SL("i")), new AccesoRegistro(new Id(new SL("nombres")), new SL("cont"))),
								new Ins_muchas(new Ins_una(
										new Invoc_proc(new SL("inserta_nombre"), new Pr_muchos(new Pr_vacio(), new Pr(new Id(new SL("arbol")))))),
										new Asignacion(new Id(new SL("i")), new Suma(new Id(new SL("i")), new NumEnt(new SL("1"))))))),
				
				new Decs_muchas(new Decs_muchas(new Decs_vacia(),
						new DecVar(new Int(), new SL("i"))),
						new DecProc(new SL("inserta_nombre"),
								new Pf_muchos(new Pf_vacio(),new Pf_valor(new SL("arbol"), new Ref(new SL("tArbol")))),
								new Ins_una(
										new If_then_else(
												new Igual(new Id(new SL("arbol")), new NullExp()),
												new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
														new New(new Id(new SL("arbol")))),
														new Asignacion(
																new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("nombre")),
																new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
														new Asignacion(
																new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("izq")), new NullExp())),
														new Asignacion(
																new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("der")), new NullExp())),
												
												new Ins_una(new Seq(
														new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_vacia(),
																new DecVar(new Ref(new SL("tArbol")), new SL("padre"))),
																new DecVar(new Ref(new SL("tArbol")), new SL("act"))),
																new DecVar(new Bool(), new SL("fin"))),
														new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
																new Asignacion(new Id(new SL("fin")), new FalseExp())),
																new Asignacion(new Id(new SL("padre")), new NullExp())),
																new Asignacion(new Id(new SL("act")), new Id(new SL("arbol")))),
																new While(
																		new Not(new Id(new SL("fin"))),
																		new Ins_muchas(new Ins_muchas(new Ins_una(
																				new Asignacion(new Id(new SL("padre")), new Id(new SL("act")))),
																				new If_then_else(
																						new Menor(
																								new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("nombre")),
																								new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																						new Ins_una(new Asignacion(
																								new Id(new SL("act")),
																								new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("der")))),
																						new Ins_una(new If_then(
																								new Mayor(new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("nombre")),
																										new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																								new Ins_una(new Asignacion(
		    																							new Id(new SL("act")),
		    																					new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("der")))))))),
																				new If_then_else(
																						new Igual(new Id(new SL("act")), new NullExp()),
																						new Ins_una(new Asignacion(new Id(new SL("fin")), new TrueExp())),
																						new Ins_una(new If_then(
																								new Igual(
																										new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("nombre")),
																										new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																								new Ins_una(new Asignacion(new Id(new SL("fin")), new TrueExp())))))))),
																new If_then(
																		new Igual(new Id(new SL("act")), new NullExp()),
																		new Ins_una(new If_then_else(
																				new Menor(
																						new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("nombre")),
																						new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																				new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
																						new New(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der")))),
																						new Asignacion(
																								new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der"))), new SL("nombre")),
																								new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
																						new Asignacion(
																								new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der"))), new SL("izq")),
																								new NullExp())),
																						new Asignacion(
																								new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der"))), new SL("der")),
																								new NullExp())),
																				new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
																						new New(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq")))),
																						new Asignacion(
																								new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq"))), new SL("nombre")),
																								new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
																						new Asignacion(
																								new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq"))), new SL("izq")),
																								new NullExp())),
																						new Asignacion(
																								new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq"))), new SL("der")),
																								new NullExp())))))))))),
								new Decs_vacia())));


	   
	   
	   Dec decproc3 = new DecProc(new SL("escribe_nombres"),
				new Pf_muchos(new Pf_vacio(), new Pf_ref(new SL("arbol"), new Ref(new SL("tArbol")))),
				new Ins_una(
						new If_then(
								new Distinto(new Id(new SL("arbol")), new NullExp()),
								new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
										new Invoc_proc(new SL("escribe_nombres"), new Pr_muchos(new Pr_vacio(), new Pr(new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("izq")))))),
										new Write(new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("nombre")))),
										new NewLine()),
										new Invoc_proc(new SL("escribe_nombres"), new Pr_muchos(new Pr_vacio(), new Pr(new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("der")))))))),
				new Decs_vacia());


		Instrucciones ins1 = new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
				new Invoc_proc(new SL("lee_nombres"), new Pr_muchos(new Pr_vacio(), new Pr(new Id(new SL("nombres")))))),
				new Invoc_proc(new SL("construye_arbol"), new Pr_vacio())),
				new Write(new StringExp(new SL("Listado de nombres ordenado")))),
				new NewLine()),
				new Write(new StringExp(new SL("--------------------")))),
				new NewLine()),
				new Invoc_proc(new SL("escribe_nombres"), new Pr_muchos(new Pr_vacio(), new Pr(new Id(new SL("arbol"))))));   	


		Decs declarationSection = new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_vacia(),
				dectype1),
				dectype2),
				dectype3),
				decvar1),
				decvar2),
				decproc1),
				decproc2),
				decproc3);
		Instrucciones instructionSection = ins1;

		Prog prog = new Prog_con_decs(declarationSection, instructionSection);
		return prog;

   }
}






	
/*
Dec decproc2 = new DecProc(new SL("construye_arbol"),
		new Pf_vacio(),
		new Ins_muchas(new Ins_muchas(new Ins_una(
				new Asignacion(new Id(new SL("arbol")), new NullExp())),
				new Asignacion(new Id(new SL("i")), new NumEnt(new SL("0")))),
				new While(
						new Menor(new Id(new SL("i")), new AccesoRegistro(new Id(new SL("nombres")), new SL("cont"))),
						new Ins_muchas(new Ins_una(
								new Invoc_proc(new SL("inserta_nombre"), new Pr_muchos(new Pr_vacio(), new Pr(new Id(new SL("arbol"))))),
								new Asignacion(new Id(new SL("i")), new Suma(new Id(new SL("i")), new NumEnt(new SL("1"))))))),
		
		new Decs_muchas(new Decs_muchas(new Decs_vacia(),
				new DecVar(new Int(), new SL("i"))),
				new DecProc(new SL("inserta_nombre"),
						new Pf_muchos(new Pf_vacio(), new Pf_valor(new SL("arbol"), new Ref(new SL("tArbol")))),
						new Ins_una(
								new If_then_else(
										new Igual(new Id(new SL("arbol")), new NullExp()),
										new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
												new New(new Id(new SL("arbol")))),
												new Asignacion(
														new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("nombre")),
														new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
												new Asignacion(
														new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("izq")), new NullExp())),
												new Asignacion(
														new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("der")), new NullExp())),
										
										new Ins_una(new Seq(
												new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_vacia(),
														new DecVar(new Ref(new SL("tArbol")), new SL("padre"))),
														new DecVar(new Ref(new SL("tArbol")), new SL("act"))),
														new DecVar(new Bool(), new SL("fin"))),
												new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
														new Asignacion(new Id(new SL("fin")), new FalseExp())),
														new Asignacion(new Id(new SL("padre")), new NullExp())),
														new Asignacion(new Id(new SL("act")), new Id(new SL("arbol")))),
														new While(
																new Not(new Id(new SL("fin"))),
																new Ins_muchas(new Ins_muchas(new Ins_una(
																		new Asignacion(new Id(new SL("padre")), new Id(new SL("act")))),
																		new If_then_else(
																				new Menor(
																						new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("nombre")),
																						new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																				new Ins_una(new Asignacion(
																						new Id(new SL("act")),
																						new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("der")))),
																				new Ins_una(new If_then(
																						new Mayor(new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("nombre")),
																								new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																						new Ins_una(new Asignacion(
    																							new Id(new SL("act")),
    																					new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("der")))))))),
																		new If_then_else(
																				new Igual(new Id(new SL("act")), new Null()),
																				new Ins_una(new Asignacion(new Id(new SL("fin")), new TrueExp())),
																				new Ins_una(new If_then(
																						new Igual(
																								new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("nombre")),
																								new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																						new Ins_una(new Asignacion(new Id(new SL("fin")), new TrueExp())))))))),
														new If_then(
																new Igual(new Id(new SL("act")), new NullExp()),
																new Ins_una(new If_then_else(
																		new Menor(
																				new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("nombre")),
																				new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																		new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
																				new New(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der")))),
																				new Asignacion(
																						new AccesoRegistro (new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der"))), new SL("nombre")),
																						new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der"))), new SL("izq")),
																						new NullExp())),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der"))), new SL("der")),
																						new NullExp())),
																		new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
																				new New(new AccesoRegistro (new Indireccion(new Id(new SL("padre"))), new SL("izq")))),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq"))), new SL("nombre")),
																						new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq"))), new SL("izq")),
																						new NullExp())),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq"))), new SL("der")),
																						new NullExp())))))))))),
						new Decs_vacia())));


Dec decproc2 = new DecProc(new SL("construye_arbol"),
		new Pf_vacio(),
		new Ins_muchas(new Ins_muchas(new Ins_una(
				new Asignacion(new Id(new SL("arbol")), new NullExp())),
				new Asignacion(new Id(new SL("i")), new NumEnt(new SL("0")))),
				new While(
						new Menor(new Id(new SL("i")), new AccesoRegistro(new Id(new SL("nombres")), new SL("cont"))),
						new Ins_muchas(new Ins_una(
								new Invoc_proc(new SL("inserta_nombre"), new Pr_muchos(new Pr_vacio(), new Pr(new Id(new SL("arbol")))))),
								new Asignacion(new Id(new SL("i")), new Suma(new Id(new SL("i")), new NumEnt(new SL("1"))))))),
		
		new Decs_muchas(new Decs_muchas(new Decs_vacia(),
				new DecVar(new Int(), new SL("i"))),
				new DecProc(new SL("inserta_nombre"),
						new Pf_muchos(new Pf_vacio(),new Pf_valor(new SL("arbol"), new Ref(new SL("tArbol")))),
						new Ins_una(
								new If_then_else(
										new Igual(new Id(new SL("arbol")), new NullExp()),
										new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
												new New(new Id(new SL("arbol")))),
												new Asignacion(
														new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("nombre")),
														new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
												new Asignacion(
														new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("izq")), new NullExp())),
												new Asignacion(
														new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("der")), new NullExp())),
										
										new Ins_una(new Seq(
												new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_vacia(),
														new DecVar(new Ref(new SL("tArbol")), new SL("padre"))),
														new DecVar(new Ref(new SL("tArbol")), new SL("act"))),
														new DecVar(new Bool(), new SL("fin"))),
												new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
														new Asignacion(new Id(new SL("fin")), new FalseExp())),
														new Asignacion(new Id(new SL("padre")), new NullExp())),
														new Asignacion(new Id(new SL("act")), new Id(new SL("arbol")))),
														new While(
																new Not(new Id(new SL("fin"))),
																new Ins_muchas(new Ins_muchas(new Ins_una(
																		new Asignacion(new Id(new SL("padre")), new Id(new SL("act")))),
																		new If_then_else(
																				new Menor(
																						new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("nombre")),
																						new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																				new Ins_una(new Asignacion(
																						new Id(new SL("act")),
																						new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("der")))),
																				new Ins_una(new If_then(
																						new Mayor(new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("nombre")),
																								new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																						new Ins_una(new Asignacion(
    																							new Id(new SL("act")),
    																					new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("der")))))))),
																		new If_then_else(
																				new Igual(new Id(new SL("act")), new NullExp()),
																				new Ins_una(new Asignacion(new Id(new SL("fin")), new TrueExp())),
																				new Ins_una(new If_then(
																						new Igual(
																								new AccesoRegistro(new Indireccion(new Id(new SL("act"))), new SL("nombre")),
																								new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																						new Ins_una(new Asignacion(new Id(new SL("fin")), new TrueExp())))))))),
														new If_then(
																new Igual(new Id(new SL("act")), new NullExp()),
																new Ins_una(new If_then_else(
																		new Menor(
																				new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("nombre")),
																				new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i")))),
																		new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
																				new New(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der")))),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der"))), new SL("nombre")),
																						new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der"))), new SL("izq")),
																						new NullExp())),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("der"))), new SL("der")),
																						new NullExp())),
																		new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
																				new New(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq")))),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq"))), new SL("nombre")),
																						new Index(new AccesoRegistro(new Id(new SL("nombres")), new SL("nombres")), new Id(new SL("i"))))),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq"))), new SL("izq")),
																						new NullExp())),
																				new Asignacion(
																						new AccesoRegistro(new Indireccion(new AccesoRegistro(new Indireccion(new Id(new SL("padre"))), new SL("izq"))), new SL("der")),
																						new NullExp())))))))))),
						new Decs_vacia())));




*/

/*
Dec decproc3 = new DecProc(new SL("escribe_nombres"),
		new Pf_muchos(new Pf_vacio(), new Pf_ref(new SL("arbol"), new Ref(new SL("tArbol")))),
		new Ins_una(
				new If_then(
						new Distinto(
								new Id(new SL("arbol")), 
								new NullExp()),
						new Ins_muchas(
								new Ins_muchas(
										new Ins_muchas(
												new Ins_una(
														new Invoc_proc(
																new SL("escribe_nombres"), 
																new Pr_muchos(
																		new Pr_vacio(), new Pr(
																				new AccesoRegistro(
																						new Indireccion(
																								new Id(new SL("arbol"))), 
																						new SL("izq")))))),
								new Write(new AccesoRegistro(new Indireccion(new Id(new SL("arbol")), new SL("nombre"))))),
								new NewLine()),
								new Invoc_proc(new SL("escribe_nombres"), new Pr_muchos(new Pr_vacio(), new Pr(new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("der"))))))),
		new Decs_vacia());
		

Dec decproc3 = new DecProc(new SL("escribe_nombres"),
		new Pf_muchos(new Pf_vacio(), new Pf_ref(new SL("arbol"), new Ref(new SL("tArbol")))),
		new Ins_una(
				new If_then(
						new Distinto(new Id(new SL("arbol")), new NullExp()),
						new Ins_muchas(new Ins_muchas(new Ins_muchas(new Ins_una(
								new Invoc_proc(new SL("escribe_nombres"), new Pr_muchos(new Pr_vacio(), new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("izq"))))),
								new Write(new AccesoRegistro(new Indireccion(new Id(new SL("arbol")), new SL("nombre")))),
								new NewLine()),
								new Invoc_proc(new SL("escribe_nombres"), new Pr_muchos(new Pr_vacio(), new AccesoRegistro(new Indireccion(new Id(new SL("arbol"))), new SL("der"))))))),
		new Decs_vacia());
		*/





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
   
   

   
