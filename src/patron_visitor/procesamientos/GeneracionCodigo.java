package patron_visitor.procesamientos;

import java.util.Stack;

import maquinaP.MaquinaP;
import patron_visitor.asint.NodoAST;
import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;


public class GeneracionCodigo extends ProcesamientoPorDefecto{

	private MaquinaP p;
	private Stack<DecProc> procs;
	
	public GeneracionCodigo(MaquinaP p) {
		this.p = p;
		this.procs = new Stack<DecProc>();
	}
	
	public static Tipo getReference(Tipo tipo) {
	    if (tipo instanceof Ref) {
	        if (tipo.getVinculo() instanceof Ref) return getReference((Tipo)tipo.vinculo);
	        else return getReference(((DecTipo)(tipo.getVinculo())).getTipo());
	    }
	    else return tipo;
	}
	
	public Tipo refType(Exp exp) {
        if (exp.getTipoNodo() instanceof Ref)
        {
            if (exp.getVinculo().getTipoNodo() instanceof Ref)
            	return refType((Exp) exp.getVinculo());
            else if (exp.getVinculo() instanceof DecTipo)
            	return ((DecTipo) exp.getVinculo()).getTipo();
        }
        else if (exp instanceof Indireccion)
		{
			return refType(((Indireccion) exp).getExp());
		}
        
        return exp.getTipoNodo();
	}
	
	public boolean esDesignador(NodoAST nodo) {
		if(nodo instanceof Id || nodo instanceof Index || nodo instanceof Indireccion || nodo instanceof AccesoRegistro) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addProcedimientos(Decs decs){
		if (decs instanceof Decs_muchas) {
            Decs_muchas aux = (Decs_muchas) decs;
            if (aux.dec() instanceof DecProc)
                procs.push((DecProc) aux.dec());
            addProcedimientos(aux.decs());
        }
		else if (decs instanceof Decs_una){
			Decs_una aux = (Decs_una) decs;
			if(aux.dec() instanceof DecProc) {
				procs.push((DecProc) aux.dec());
			}
		}
	}
		
	// Programa

	public void procesa(Prog_con_decs prog) {
		prog.getInstrucciones().procesa(this);
		p.ponInstruccion(p.stop());
		prog.getDeclaraciones().procesa(this);
		while(procs.size() != 0) {
			DecProc proc = procs.pop();
			proc.procesa(this);
		}		
	}
	
	public void procesa(Prog_sin_decs prog) {
		prog.getInstrucciones().procesa(this);
		p.ponInstruccion(p.stop());			
	}
	

	// Declaraciones
	
	public void procesa(Decs_una decs) {
		decs.dec().procesa(this);
	}

	public void procesa(Decs_muchas decs) {
		decs.decs().procesa(this);
		decs.dec().procesa(this);
	}
	
	@Override
	public void procesa(DecTipo dec) {
	}

	@Override
	public void procesa(DecVar dec) {
	}
	
	@Override
	public void procesa(DecProc dec) {		
		
        dec.getIns().procesa(this);
        p.ponInstruccion(p.desactiva(dec.getNivel(), dec.getTam()));
        p.ponInstruccion(p.irInd());
        addProcedimientos(dec.getDecs());
	}
	
	public void procesa(Pr_muchos pr) {
		pr.getPr().procesa(this);
		pr.getPreales().procesa(this);
	}
	
	public void procesa(Pr_uno pr) {
		pr.getPr().procesa(this);
	}

	// Instrucciones

	
	@Override
	public void procesa(Invoc_proc ins) {
		
		p.ponInstruccion(p.activa(ins.getVinculo().getNivel(), ins.getVinculo().getTam(), ins.getSig()));
		
		
        if (!(((DecProc)ins.getVinculo()).getPforms() instanceof Pf_vacio && ins.getPreales() instanceof Pr_vacio))
        {
        	if (((DecProc)ins.getVinculo()).getPforms() instanceof Pf_muchos && ins.getPreales() instanceof Pr_muchos) {
        		
        		//Comprobar
            }
        }
		
		p.ponInstruccion(p.desapilad(ins.getVinculo().getNivel()));
		p.ponInstruccion(p.irA(ins.getVinculo().getIni()));
	}

	@Override
	public void procesa(Delete ins) {
		ins.getExp().procesa(this);
		p.ponInstruccion(p.apilaInd());
		p.ponInstruccion(p.dealloc(((Pointer) (refType(ins.getExp()))).getTipoNodo().getTam()));
	}

	@Override
	public void procesa(New ins) {
		ins.getExp().procesa(this);
		p.ponInstruccion(p.dealloc(((Pointer) (refType(ins.getExp()))).getTipoNodo().getTam()));
		p.ponInstruccion(p.desapilaInd());
	}

	@Override
	public void procesa(NewLine nl) {
		p.ponInstruccion(p.apilaString("\n"));
		p.ponInstruccion(p.write());
		
	}
	
	public void procesa(Seq ins) {
		ins.getIns().procesa(this);
		addProcedimientos(ins.getDecs());
	}

	@Override
	public void procesa(Write ins) {
		ins.getExp().procesa(this);
		if (esDesignador(ins.getExp())) {p.ponInstruccion(p.apilaInd());}
        p.ponInstruccion(p.write());
	}

	@Override
	public void procesa(Read ins) {
		ins.getExp().procesa(this);
		if (ins.getExp().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.read());
		} else if (ins.getExp().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			p.ponInstruccion(p.readR());
		} else if (ins.getExp().getTipoNodo().getEnumTipo() == EnumTipo.STRING) {
			p.ponInstruccion(p.readS());
		}
		p.ponInstruccion(p.desapilaInd());
	}
	
	@Override
	public void procesa(If_then ins) {
		ins.getExp().procesa(this);
		if (esDesignador(ins.getExp()))   {
			p.ponInstruccion(p.apilaInd());
		}
		p.ponInstruccion(p.irF(ins.getSig()));
		ins.getIns().procesa(this);
	}


	@Override
	public void procesa(If_then_else ins) {
		ins.getExp().procesa(this);
		if (esDesignador(ins.getExp()))   {
			p.ponInstruccion(p.apilaInd());
		}
		p.ponInstruccion(p.irF(ins.getIni()));
		ins.getIns1().procesa(this);
		p.ponInstruccion(p.irA(ins.getSig()));
		ins.getIns2().procesa(this);
	}


	
	@Override
	public void procesa(While ins) {
		ins.getExp().procesa(this);
		if (esDesignador(ins.getExp()))   {
			p.ponInstruccion(p.apilaInd());
		}
		p.ponInstruccion(p.irF(ins.getSig()));
		ins.getIns().procesa(this);
		p.ponInstruccion(p.irA(ins.getIni()));
	}

	@Override
	public void procesa(Asignacion ins) {
		// Falta añadir opcion para la asignacion de arrays
		ins.getFirst().procesa(this);
		ins.getSecond().procesa(this);
		
		if (esDesignador(ins.getFirst())) {
			if (ins.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				ins.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
				p.ponInstruccion(p.desapilaInd());
				p.ponInstruccion(p.parseaIR());
				p.ponInstruccion(p.apilaInd());
			}
			else {
				p.ponInstruccion(p.mueve(ins.getSecond().getTipoNodo().getTam()));
			}
		} else {
			if (ins.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
					ins.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
				
				p.ponInstruccion(p.parseaIR());
                p.ponInstruccion(p.desapilaInd());
				
			} else {
				p.ponInstruccion(p.desapilaInd());
			}
		}
		

    }
	

	public void procesa(Ins_muchas ins) {
		ins.ins().procesa(this);
		ins.in().procesa(this);
	}
	
	@Override
	public void procesa(Ins_vacia ins) {
	}
	
	
	// EXPRESIONES 
	
	public void procesa(Id exp) {
		if (exp.getVinculo().getNivel() == 0) {
			p.ponInstruccion(p.apilaInt(exp.getDir()));
		} else {
			p.ponInstruccion(p.apilad(exp.getNivel()));
			p.ponInstruccion(p.apilaInt(exp.getDir()));
			p.ponInstruccion(p.suma());
			
			if (exp.getVinculo() instanceof Pf_ref) {
				p.ponInstruccion(p.apilaInd());
			}
		}
	}
	
	public void procesa(TrueExp exp) {
		p.ponInstruccion(p.apilaBool(true));
	}

	public void procesa(FalseExp exp) {
		p.ponInstruccion(p.apilaBool(false));
	}
	
	public void procesa(NumEnt exp) {
		p.ponInstruccion(p.apilaInt(Integer.parseInt(exp.num().toString())));
	}

	public void procesa(NumReal exp) {
		p.ponInstruccion(p.apilaReal(Double.parseDouble(exp.num().toString())));
	}
	
	@Override
	public void procesa(NullExp exp) {
		p.ponInstruccion(p.apilaInt(-1));
	}

	@Override
	public void procesa(StringExp exp) {
		p.ponInstruccion(p.apilaString(exp.num()));
	}
	
	public void procesa(Suma exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
		
		
        if (exp.getTipoNodo().getEnumTipo() == EnumTipo.REAL)
        	 p.ponInstruccion(p.sumaR());
        else if (exp.getTipoNodo().getEnumTipo() == EnumTipo.INT)
        	p.ponInstruccion(p.suma());
		
	}

	public void procesa(Resta exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
		
        if (exp.getTipoNodo().getEnumTipo() == EnumTipo.REAL)
        	 p.ponInstruccion(p.restaR());
        else if (exp.getTipoNodo().getEnumTipo() == EnumTipo.INT)
        	p.ponInstruccion(p.resta());
	}

	
	public void procesa(And exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst())) {
			p.ponInstruccion(p.apilaInd());
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond())) {
			p.ponInstruccion(p.apilaInd());
		}
		p.ponInstruccion(p.and());
	}

	public void procesa(Or exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst())) {
			p.ponInstruccion(p.apilaInd());
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond())) {
			p.ponInstruccion(p.apilaInd());
		}
		p.ponInstruccion(p.or());
	}

	
	public void procesa(Mayor exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.mayor());
		} else if ((exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL)) {
			p.ponInstruccion(p.mayor());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.BOOL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.BOOL) {
			p.ponInstruccion(p.mayor());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.STRING &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.STRING) {
			p.ponInstruccion(p.mayor());
		}
	}

	public void procesa(Menor exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.menor());
		} else if ((exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL)) {
			p.ponInstruccion(p.menorR());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.BOOL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.BOOL) {
			p.ponInstruccion(p.menorB());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.STRING &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.STRING) {
			p.ponInstruccion(p.menorS());
		}
	}

	public void procesa(MenorIgual exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.menorIgual());
		} else if ((exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL)) {
			p.ponInstruccion(p.menorIgualR());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.BOOL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.BOOL) {
			p.ponInstruccion(p.menorIgualB());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.STRING &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.STRING) {
			p.ponInstruccion(p.menorIgualS());
		}
	}

	public void procesa(MayorIgual exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.mayorIgual());
		} else if ((exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL)) {
			p.ponInstruccion(p.mayorIgualR());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.BOOL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.BOOL) {
			p.ponInstruccion(p.mayorIgualB());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.STRING &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.STRING) {
			p.ponInstruccion(p.mayorIgualS());
		}
	}

	public void procesa(Igual exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.igual());
		} else if ((exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL)) {
			p.ponInstruccion(p.igualR());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.BOOL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.BOOL) {
			p.ponInstruccion(p.igualB());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.STRING &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.STRING) {
			p.ponInstruccion(p.igualS());
		}
	}

	public void procesa(Distinto exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.igual());
		} else if ((exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) || 
		(exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL)) {
			p.ponInstruccion(p.igualR());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.BOOL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.BOOL) {
			p.ponInstruccion(p.igualB());
		} else if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.STRING &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.STRING) {
			p.ponInstruccion(p.igualS());
		}
		p.ponInstruccion(p.not());
	}

	// Nivel 3
	public void procesa(Mul exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
	
        if (exp.getTipoNodo().getEnumTipo() == EnumTipo.REAL)
        	 p.ponInstruccion(p.mulR());
        else if (exp.getTipoNodo().getEnumTipo() == EnumTipo.INT)
        	p.ponInstruccion(p.mul());
	}

	public void procesa(Div exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.INT &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.REAL) {
			
			p.ponInstruccion(p.parseaIR());
			
		}
		
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond()))
			p.ponInstruccion(p.apilaInd());
		
		if (exp.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				exp.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
			p.ponInstruccion(p.parseaIR());
			
		}
		
        if (exp.getTipoNodo().getEnumTipo() == EnumTipo.REAL)
        	 p.ponInstruccion(p.divR());
        else if (exp.getTipoNodo().getEnumTipo() == EnumTipo.INT)
        	p.ponInstruccion(p.div());
	}

	@Override
	public void procesa(Mod exp) {
		exp.getFirst().procesa(this);
		if (esDesignador(exp.getFirst())) {
			p.ponInstruccion(p.apilaInd());
		}
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond())) {
			p.ponInstruccion(p.apilaInd());
		}
		
		p.ponInstruccion(p.mod());
	}

	// Nivel 4
	public void procesa(MenosUnario exp) {
		
		exp.getExp().procesa(this);
		if (esDesignador(exp.getExp())) {
			p.ponInstruccion(p.apilaInd());
		}
		if (exp.getExp().getTipoNodo().getEnumTipo() == EnumTipo.INT)
        	p.ponInstruccion(p.menosUnario());
		if (exp.getExp().getTipoNodo().getEnumTipo() == EnumTipo.REAL)
        	p.ponInstruccion(p.menosUnarioR());
	}

	public void procesa(Not exp) {
		exp.getExp().procesa(this);
		if (esDesignador(exp.getExp())) {
			p.ponInstruccion(p.apilaInd());
		}
		p.ponInstruccion(p.not());
	}
	
	@Override
	public void procesa(Index exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
		if (esDesignador(exp.getSecond())) {
			p.ponInstruccion(p.apilaInd());
		}
		p.ponInstruccion(p.apilaInt((((Array) (exp.getTipoNodo())).getTipoElems()).getTam()));
		p.ponInstruccion(p.mul());
		p.ponInstruccion(p.suma());
	}

	@Override
	public void procesa(AccesoRegistro exp) {
		exp.getExp().procesa(this);
		//p.ponInstruccion(p.apilaInt((Record) refType(exp.getExp())).desplazamiento(exp.getId())); //Considerar el desplazamiento del campo
		p.ponInstruccion(p.suma());
	}

	@Override
	public void procesa(Indireccion exp) {
		exp.getExp().procesa(this);
		p.ponInstruccion(p.apilaInd());
	}	
}