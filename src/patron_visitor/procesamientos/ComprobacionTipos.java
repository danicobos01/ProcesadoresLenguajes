


public class ComprobacionTipos extends ProcesamientoPorDefecto {
	
	public enum tNodo{
		LIT_ENT, LIT_REAL;
	}
	
	private boolean compatibleNumero(tNodo t0, tNodo t1) {
		if (t0 == tNodo.LIT_ENT && t1 == tNodo.LIT_ENT) {
			return true;
		} else if (t0 == tNodo.LIT_REAL && t1 == tNodo.LIT_REAL) {
			return true;
		}
		
		return false;
	}
	
// Programa

	public void procesa(Programa prog) {
		if(prog.declaraciones() != null) {
			prog.declaraciones().procesa(this);
		}
		prog.instrucciones().procesa(this);
		prog.setTipo(prog.instrucciones().getTipo());
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
		dec.pforms().procesa(this);
		dec.bloque().procesa(this);
	}
	
	@Override
	public void procesa(DecTipo dec) {
		dec.val().procesa(this);
	}

	@Override
	public void procesa(DecVar dec) {
		dec.val().procesa(this);
		dec.setTipo(dec.val().getTipo());
	}
	
	// Instrucciones
	
	public void procesa(Insts_muchas insts) {
		insts.instrucciones().procesa(this);
		insts.instruccion().procesa(this);
		if (insts.instrucciones().getTipo() == tNodo.OK
			&& insts.instruccion().getTipo() == tNodo.OK) {
			insts.setTipo(tNodo.OK);
		} else {
			printError(insts);
		}
	}

	public void procesa(Insts_una insts) {
		insts.instruccion().procesa(this);
		insts.setTipo(insts.instruccion().getTipo());
	}
	
	@Override
	public void procesa(Lista_inst_empty lista_inst_empty) {
		lista_inst_empty.setTipo(tNodo.OK);
	}

	@Override
	public void procesa(Lista_inst_una lista_inst_una) {
		lista_inst_una.instruccion().procesa(this);
		lista_inst_una.setTipo(lista_inst_una.instruccion().getTipo());
	}

	@Override
	public void procesa(Lista_inst_muchas lista_inst_muchas) {
		lista_inst_muchas.instrucciones().procesa(this);
		lista_inst_muchas.instruccion().procesa(this);
		if (lista_inst_muchas.instrucciones().getTipo() == tNodo.OK
			&& lista_inst_muchas.instruccion().getTipo() == tNodo.OK) {
			lista_inst_muchas.setTipo(tNodo.OK);
			
		} else {
			printError(lista_inst_muchas);
		}
	}
	
	// Expresiones
	
	@Override
	public void procesa(Lista_exp_empty lista_exp_empty) {
		lista_exp_empty.setTipo(tNodo.OK);
	}

	@Override
	public void procesa(Exp_muchas exp_muchas) {
		exp_muchas.expresiones().procesa(this);
		exp_muchas.expresion().procesa(this);
		if (exp_muchas.expresiones().getTipo() == tNodo.OK) {
			exp_muchas.setTipo(exp_muchas.expresion().getTipo());
		} else {
			printError(exp_muchas);
		}
	}

	@Override
	public void procesa(Exp_una exp_una) {
		exp_una.expresion().procesa(this);
		exp_una.setTipo(exp_una.expresion().getTipo());
	}

	// Operadores
	
	public void procesa(Suma exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT && exp.arg1().getTipo() == tNodo.LIT_ENT) {
			exp.setTipo(tNodo.LIT_ENT);
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL && exp.arg1().getTipo() == tNodo.LIT_REAL) {
			exp.setTipo(tNodo.LIT_REAL);
		} else {
			printError(exp);
		}
	}
	
		public void procesa(LitEnt exp) {
		exp.setTipo(tNodo.LIT_ENT);
	}
	
	public void procesa(LitReal exp) {
		exp.setTipo(tNodo.LIT_REAL);
	}
	
	//Tipo
	
	@Override
	public void procesa(Int int1) {
		int1.setTipo(tNodo.LIT_ENT);
	}

	@Override
	public void procesa(Real real) {
		real.setTipo(tNodo.LIT_REAL);
	}