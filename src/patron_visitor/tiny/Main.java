//Práctica realizada por Pablo Magno Pezo Ortiz, Daniel Cobos Peñas y Pablo Lozano Martín

package patron_visitor.tiny;


import patron_visitor.asint.TinyASint.*;
import java.lang.String;

import maquinaP.MaquinaP;
import patron_visitor.procesamientos.AsignacionEspacio;
import patron_visitor.procesamientos.ComprobacionTipos;
import patron_visitor.procesamientos.Etiquetado;
import patron_visitor.procesamientos.GeneracionCodigo;
import patron_visitor.procesamientos.Vinculacion;

public class Main {
   public static void main(String[] args) throws Exception {
         
         Prog_con_decs programaSimple = new Prog_con_decs(
        		 new Decs_muchas(new Decs_vacia(), new DecVar(new Int(), new SL("x"))),
        		 new Ins_muchas(new Ins_vacia(), new Asignacion(new Id(new SL("x")), new NumEnt(new SL("5")))));
         
         Prog_con_decs prog = (Prog_con_decs)codigoEjemplo();

         Vinculacion vin = new Vinculacion();
         vin.procesa(prog);
         
         ComprobacionTipos com = new ComprobacionTipos();
         com.procesa(prog);

//         AsignacionEspacio asig = new AsignacionEspacio();
//         asig.procesa(prog);
//         
//         MaquinaP p = new MaquinaP(100, 100, 100, 10);
//         GeneracionCodigo gen = new GeneracionCodigo(p);
//         gen.procesa(prog);
//         
//         Etiquetado et = new Etiquetado();
//         et.procesa(prog);
         
         System.out.println("Finaliza");
     }
   
   public static Prog codigoEjemploSimple() {
	   Dec dectype1 = new DecTipo(new SL("tArbol"), new Pointer(new Ref(new SL("tNodo"))));
	   Dec dectype2 = new DecTipo(new SL("tNodo"), new RecordTipo(
	   		new Campos_muchos(new Campos_muchos(new Campos_uno(
	   				new Campo(new SL("nombre"), new StringTipo())),
	   				new Campo(new SL("izq"), new Ref(new SL("tArbol")))),
	   				new Campo(new SL("der"), new Ref(new SL("tArbol"))))));
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
				new Decs_muchas(null, null));
	   
	   
	   	Instrucciones instructionSection = new Ins_una(new Invoc_proc(new SL("construye_arbol"), new Pr_vacio()));   	

		Decs declarationSection = new Decs_muchas(new Decs_muchas(new Decs_muchas(new Decs_vacia(),
				dectype1),
				dectype2),
				decproc2);
	   
	   Prog prog = new Prog_con_decs(declarationSection, instructionSection);
		return prog;
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
	   				new Campo(new SL("nombres"), new Array(new SL("50"), new StringTipo()))),
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