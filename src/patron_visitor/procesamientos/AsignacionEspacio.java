

public class AsignacionEspacio extends ProcesamientoPorDefecto {
	private int dir = 0;
	private int nivel = 0;
	
	// Programa

	public void procesa(Programa prog) {
		if (prog.declaraciones() != null) {
			prog.declaraciones().procesa(this);
		}
		prog.instrucciones().procesa(this);
		prog.tam_datos = dir;
	}
	
		// Declaraciones

	public void procesa(Decs_muchas decs) {
		decs.declaraciones().procesa(this);
		decs.declaracion().procesa(this);
	}

	public void procesa(Decs_una decs) {
		decs.declaracion().procesa(this);
	}

	@Override
	public void procesa(DecProc dec) {
		int dir_ant = dir;
		nivel++;
		dir = 0;
		dec.pforms().procesa(this);
		dec.bloque().procesa(this);
		dec.nivel = nivel;
		dec.tam_datos = dir;
		dir = dir_ant;
		nivel--;
	}

	@Override
	public void procesa(DecTipo dec) {
		dec.val().procesa(this);
		dec.tam_datos = dec.val().tam_datos;
	}

	@Override
	public void procesa(DecVar dec) {
		dec.dir = dir;
		dec.nivel = nivel;
		dec.val().procesa(this);
		dec.tam_datos = dec.val().tam_datos;
		dec.tam_basico = dec.val().tam_basico;
		dir += dec.tam_datos;
	}
	
	// Instrucciones

	public void procesa(Insts_muchas insts) {
		insts.instrucciones().procesa(this);
		insts.instruccion().procesa(this);
	}

	public void procesa(Insts_una insts) {
		insts.instruccion().procesa(this);
	}

	@Override
	public void procesa(Lista_inst_empty lista_inst_empty) {
	}
	
		@Override
	public void procesa(Lista_inst_una lista_inst_una) {
		lista_inst_una.instruccion().procesa(this);
	}

	@Override
	public void procesa(Lista_inst_muchas lista_inst_muchas) {
		lista_inst_muchas.instrucciones().procesa(this);
		lista_inst_muchas.instruccion().procesa(this);
	}
	
		// Expresiones

	@Override
	public void procesa(Lista_exp_empty lista_exp_empty) {
		System.out.println();
	}

	@Override
	public void procesa(Exp_muchas exp_muchas) {
		exp_muchas.expresiones().procesa(this);
		exp_muchas.expresion().procesa(this);
	}

	@Override
	public void procesa(Exp_una exp_una) {
		exp_una.expresion().procesa(this);
	}
	
	// Operadores

	public void procesa(Suma exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}
	
		public void procesa(LitEnt exp) {
	}
	
	public void procesa(LitReal exp) {
	}
	
	
	// Tipo
	
		@Override
	public void procesa(Int int1) {
		int1.tam_datos = 1;
	}
	
	@Override
	public void procesa(Real real) {
		real.tam_datos = 1;
	}
