package patron_visitor.procesamientos;

import java.util.Stack;

import patron_visitor.asint.NodoAST;
import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;

public class Etiquetado extends ProcesamientoPorDefecto {
	
	public int etq = 0;
	private Stack<DecProc> procs;

	
	public static Tipo getReference(Tipo tipo) {
	    if (tipo instanceof Ref) {
	        if (tipo.getVinculo() instanceof Ref) return getReference((Tipo)tipo.vinculo);
	        else return getReference(((DecTipo)(tipo.getVinculo())).getTipo());
	    }
	    else return tipo;
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
	
	
	
	public void procesa(Prog_con_decs prog) {
		prog.getInstrucciones().procesa(this);
		this.etq++;
		addProcedimientos(prog.getDeclaraciones());
		while(procs.size() != 0) {
			DecProc proc = procs.pop();
			proc.procesa(this);
		}
	}
	
	public void procesa(Prog_sin_decs prog) {
		prog.getInstrucciones().procesa(this);
		this.etq++;
	}
	
	public void procesa(Decs_muchas decs) {
		decs.decs().procesa(this);
		decs.dec().procesa(this);
	}
	
	public void procesa(Decs_una decs) {
		decs.dec().procesa(this);
	}
	
	public void procesa(Decs_vacia decs) {}
	
	public void procesa(DecVar dec) {}
	
	public void procesa(DecTipo dec) {}
	
	public void procesa(DecProc dec) {
		dec.setIni(this.etq);
		dec.getIns().procesa(this);
		this.etq += 2;
		addProcedimientos(dec.getDecs());
		dec.setSig(this.etq);
	}
	
	public void procesa(Pf_muchos pf) {
		pf.getPforms().procesa(this);
		pf.getPf().procesa(this);
	}
	
	public void procesa(Pf_uno pf) {
		pf.getPf().procesa(this);
	}
	
	public void procesa(Pr pr) {
		pr.getExp().procesa(this);
	}
	
	public void procesa(Pr_muchos pr) {
		pr.getPr().procesa(this);
		pr.getPreales().procesa(this);	
	}
	
	public void procesa(Pr_uno pr) {
		pr.getPr().procesa(this);
	}
	
	
	// Instrucciones
	
	public void procesa(Asignacion ins) {
		ins.setIni(this.etq);
		ins.getFirst().procesa(this);
		ins.getSecond().procesa(this);
		
		if (esDesignador(ins.getFirst())) {
			if (ins.getFirst().getTipoNodo().getEnumTipo() == EnumTipo.REAL &&
				ins.getSecond().getTipoNodo().getEnumTipo() == EnumTipo.INT) {
				this.etq += 3;
			}
			else {
				this.etq += 3;
			}
		} else {
			
			this.etq += 1;
			
		}
		ins.setSig(this.etq);
		
	}
	
	public void procesa(If_then ins) {
		 ins.setIni(this.etq);
         ins.getExp().procesa(this);
         if (esDesignador(ins.getExp())) {
             this.etq +=1;
         }
         this.etq += 1;
         ins.getIns().procesa(this);
         ins.setSig(this.etq);
	}
	
	public void procesa(If_then_else ins) {
		ins.setIni(this.etq);
        ins.getExp().procesa(this);
        if (esDesignador(ins.getExp())) {
            this.etq +=1;
        }
        this.etq += 1;
        ins.getIns1().procesa(this);
        this.etq += 1;
        ins.getIns2().procesa(this);
        ins.setSig(this.etq);
	}
	
	public void procesa(While ins) {
		ins.setIni(this.etq);
        ins.getExp().procesa(this);
        if (esDesignador(ins.getExp())) {
            this.etq +=1;
        }
        this.etq += 1;
        ins.getIns().procesa(this);
        this.etq += 1;
        ins.setSig(this.etq);
	}
	
	public void procesa(Read ins) {
		ins.setIni(this.etq);
        ins.getExp().procesa(this);
        this.etq += 2;
        ins.setSig(this.etq);
	}
	
	public void procesa(Write ins) {
		ins.setIni(this.etq);
        ins.getExp().procesa(this);
        if(esDesignador(ins.getExp())) {
        	this.etq += 1;
        }
        this.etq += 1;
        ins.setSig(this.etq);
	}
	
	public void procesa(NewLine ins) {
		ins.setIni(this.etq);
        this.etq += 2;
        ins.setSig(this.etq);
	}
	
	public void procesa(New ins) {
		ins.getExp().procesa(this);
        this.etq += 2;
	}
	
	public void procesa(Delete ins) {
		ins.getExp().procesa(this);
        this.etq += 2;
	}
	
	public void procesa(Seq ins) {
		ins.setIni(this.etq);
        ins.getIns().procesa(this);
        ins.setSig(this.etq);
	}
	
	public void procesa(Invoc_proc ins) {
		ins.setIni(this.etq);
        this.etq++;
        this.etiqueta_params(((DecProc)ins.getVinculo()).getPforms(), ins.getPreales());
        this.etq += 2;
        ins.setSig(this.etq);
	}
	
	
	// comprobar si funciona
	 private void etiqueta_params(Pforms pforms, PReales preales) {
         if (pforms instanceof Pf_vacio && preales instanceof Pr_vacio)
             return;

         if (pforms instanceof Pf_muchos && preales instanceof Pr_muchos) {
             etiqueta_params(((Pf_muchos) pforms).getPforms(), ((Pr_muchos) preales).getPreales());
             inspaso(((Pr_muchos) preales).getPr(), ((Pf_muchos) pforms).getPf());
         }
     }

	 // ver como hacer esto !
     private void inspaso(Pr pr, Pf pf) {
    	 /*
         this.etq += 3;
         pr.getExp().procesa(this);
         this.etq++;
         */
     }
     
     public void procesa(Ins_muchas ins) {
    	 ins.setIni(this.etq);
    	 ins.ins().procesa(this);
    	 ins.in().procesa(this);
    	 ins.setSig(this.etq);
     }
     
     public void procesa(Ins_una ins) {
    	 ins.setIni(this.etq);
    	 ins.in().procesa(this);
    	 ins.setSig(this.etq);
     }
     
     public void procesa(Id exp) {
    	 if (exp.getVinculo().getNivel() == 0)
             this.etq++;
         else {
             this.etq += 3;
             if (exp.getVinculo() instanceof Pf_ref) this.etq++;
         }
     }
     
     public void procesa(NumEnt exp) {
    	 this.etq += 1;
     }
     
     public void procesa(NumReal exp) {
    	 this.etq += 1;
     }
     
     public void procesa(TrueExp exp) {
    	 this.etq += 1;
     }
     
     public void procesa(FalseExp exp) {
    	 this.etq += 1;
     }
     
     public void procesa(StringExp exp) {
    	 this.etq += 1;
     }
     
     public void procesa(NullExp exp) {
    	 this.etq += 1;
     }
     
     public void procesa(Not exp) {
    	 exp.getExp().procesa(this);
         if (esDesignador(exp.getExp())){
        	 this.etq += 1;
         }
         this.etq += 1;
     }
     
     public void procesa(MenosUnario exp) {
    	 exp.getExp().procesa(this);
         if (esDesignador(exp.getExp())){
        	 this.etq += 1;
         }
         this.etq += 1;
     }
	
     
     public void procesa(Igual exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }
         
         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }
         this.etq += 1;
     }
     
     public void procesa(Distinto exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }
         
         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }
         this.etq += 1;
     }
     
     public void procesa(Mayor exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }
         
         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }
         this.etq += 1;
     }
     
     public void procesa(Menor exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }
         
         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }
         this.etq += 1;
     }
     
     public void procesa(MayorIgual exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }
         
         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }
         
         this.etq += 1;
     }
     
     public void procesa(MenorIgual exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }
         
         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }
         
         this.etq += 1;
     }
     
     public void procesa(Suma exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }

         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }           
         this.etq += 1;
     }
     
     public void procesa(Resta exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }

         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }           
         this.etq += 1;
     }
     
     public void procesa(Mul exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }

         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }           
         this.etq += 1;
     }
     
     public void procesa(Div exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         if ((exp.getFirst().getTipoNodo()) instanceof Int && exp.getSecond().getTipoNodo() instanceof Real) {
        	 this.etq += 1;
         }

         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         if ((exp.getFirst().getTipoNodo()) instanceof Real && exp.getSecond().getTipoNodo() instanceof Int) {
        	 this.etq += 1;
         }           
         this.etq += 1;
     }
     
     public void procesa(Mod exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         this.etq += 1;
     }
     
     public void procesa(And exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         this.etq += 1;
     }
     
     public void procesa(Or exp) {
    	 exp.getFirst().procesa(this);
         if (esDesignador(exp.getFirst())) {
        	 this.etq += 1;
         }
         exp.getSecond().procesa(this);
         if (esDesignador(exp.getSecond())) {
        	 this.etq += 1;
         }
         this.etq += 1;
     }
     
     public void procesa(Index ins) {
    	 ins.getFirst().procesa(this);
    	 ins.getSecond().procesa(this);

         if (esDesignador(ins.getSecond())) {
        	 this.etq += 1;
         }
         this.etq += 3;
     }
     
     public void procesa(Indireccion ins) {
    	 ins.getExp().procesa(this);
    	 this.etq += 1;
     }
     
     public void procesa(AccesoRegistro ins) {
    	 ins.getExp().procesa(this);
    	 this.etq += 2;
     }
     
     
     

}
