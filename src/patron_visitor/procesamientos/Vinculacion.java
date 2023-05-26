package patron_visitor.procesamientos;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import patron_visitor.asint.NodoAST;
import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;



public class Vinculacion extends ProcesamientoPorDefecto {

	public static Stack<Map<String, NodoAST>> ts;
	public static final ArrayList<String> errores = new ArrayList<String>();

	
	public Vinculacion() {
		ts = new Stack<Map<String, NodoAST>>();
		this.abreNivel();
	}
	
	public void abreNivel() {
		ts.push(new HashMap<String, NodoAST>());
	}
	
	public void cierraNivel() {
		ts.remove(ts.size() - 1);
	}
	
	public boolean existeId(String id) {
        for (int i = ts.size() - 1; i >= 0; i--) {
            if (ts.get(i).containsKey(id)) {
            	return true;
            }
        }
        return false;
    }
	
	public enum tipoError {
		idDuplicado, idNoDeclarado
	}
	
	public void add(SL id, NodoAST nodo) {
		if (ts.get(ts.size() - 1).containsKey(id.toString())) {
        	System.out.println("ERROR: " + id.toString() + " - nodo");
			errores.add(tipoError.idDuplicado + ": " + id.toString()); 
		}
        else {
        	ts.peek().put(id.toString(), nodo);
        	System.out.println("id.toString() - nodo");
        }     
	}
	
	public boolean hayErrores() {
		return errores.size() > 0;
	}
		
	public NodoAST valorDe(String id) {
		for (int i = ts.size() - 1; i >= 0; i--) {
	       if (ts.get(i).containsKey(id))
	    	   System.out.println(id);
	           return ts.get(i).get(id);
	       
	    }
	    return null;
	}
	
	
	
	// VINCULACION DE LAS DISTINTAS CLASES
	
	public void procesa(Prog_con_decs prog) {
		prog.getDeclaraciones().procesa(this);
		prog.getDeclaraciones().procesa(new Procesa2());
		prog.getInstrucciones().procesa(this);
	}
	
	
	public void procesa(Prog_sin_decs prog) {
		prog.getInstrucciones().procesa(this);
	}
	
	
	public void procesa(Decs_muchas decs) {
		decs.decs().procesa(this);
		decs.dec().procesa(this);
	}
	
	public void procesa(Decs_una dec) {
		dec.dec().procesa(this);
	}
	
	public void vincula1(Decs_vacia decs) {}
	
	public void procesa(DecTipo dec) {
		dec.getTipo().procesa(this);
		this.add(dec.id(), dec);
	}
	
	public void procesa(DecVar dec) {
		dec.getTipo().procesa(this);
		this.add(dec.id(), dec);
	}
	
	public void procesa(DecProc dec) {
		this.add(dec.id(), dec);
		this.abreNivel();
		dec.getPforms().procesa(this);
		dec.getDecs().procesa(this);
		dec.getDecs().procesa(new Procesa2()); // ojo
		dec.getIns().procesa(this);
		this.cierraNivel();
	}
	
	/*
	public void vincula2(Decs_muchas decs) {
		this.vincula2(decs.decs());
		this.vincula2(decs.dec());
	}
	
	public void vincula2(Decs_una decs) {
		this.vincula2(decs.dec());
	}
	
	public void vincula2(Decs_vacia decs) {}
	
	public void vincula2(DecTipo dec) {
		this.vincula2(dec.getTipo());
	}
	
	public void vincula2(DecVar dec) {
		this.vincula2(dec.getTipo());
	}
	
	public void vincula2(DecProc dec) {
		this.vincula2(dec.getPforms());
	}
	*/
	
	
	// Tipos
	
	
	public void procesa(Int int_) {}
	
	public void procesa(Real real) {}
	
	public void procesa(Bool bool) {}
	
	public void procesa(StringTipo st) {}
	
	public void procesa(Null null_) {}
	
	public void procesa(Array arr) {
		arr.getTipoElems().procesa(this);
	}
	
	public void procesa(RecordTipo rec) {
		rec.getCampos().procesa(this);
	}
	
	public void procesa(Pointer pointer) {
		if(pointer.getTipoApuntado() != EnumTipo.REF ) {
			pointer.getApuntado().procesa(this);
		}
	}
	
	public void procesa(Ref ref) {
		if(existeId(ref.getId().toString())) {
			
			ref.setVinculo(valorDe(ref.getId().toString()));
		}
		else {
        	System.out.println("ERROR: " + ref.getId().toString() + " - ref");
			errores.add(tipoError.idNoDeclarado + ": " + ref.getId().toString());
		}
	}
	
	
	public void procesa(Campo campo) {
		campo.getTipo().procesa(this);
	}
	
	public void procesa(Campos_muchos campos) {
		campos.getCampos().procesa(this);
		campos.getCampo().procesa(this);
	}
	
	public void procesa(Campos_uno campo) {
		campo.getCampo().procesa(this);
	}
	
	/*
	public void vincula2(Int int_) {}
	
	public void vincula2(Bool bool_) {}
	
	public void vincula2(Real real_) {}
	
	public void vincula2(StringTipo string_) {}
	
	public void vincula2(Null null_) {}
	
	public void vincula2(Array arr) {
		this.vincula2(arr.getTipo());
	}
	
	public void vincula2(RecordTipo rec) {
		this.vincula2(rec.getCampos());
	}
	
	public void vincula2(Pointer p) {
		if (p.getEnumTipo() == EnumTipo.REF) {
			Tipo apuntado = p.getApuntado();
			Ref r = (Ref)apuntado; 
			if(existeId(r.getId())) {
				p.setVinculo(valorDe(r.getId().toString()));
			}
			else {
				errores.add(tipoError.idNoDeclarado + ": " + r.getId().toString());
			}
		}
		else {
			this.vincula2(p.getTipo());
		}
	}
	
	public void vincula2(Ref ref) {}
	
	public void vincula2(Campo campo) {
		this.vincula2(campo.getTipo());
	}
	
	public void vincula2(Campos_muchos campos) {
		this.vincula2(campos.getCampos());
		this.vincula2(campos.getCampo());
	}
	
	public void vincula2(Campos_uno campo) {
		this.vincula2(campo.getCampo());
	}
	
	*/
	
	// Parametros
	
	public void procesa(Pf_valor pf) {
		pf.getTipo().procesa(this);
		this.add(pf.getId(), pf);
		
	}
	
	public void procesa(Pf_ref pf) {
		pf.getTipo().procesa(this);
		this.add(pf.getId(), pf);
	}
	
	public void procesa(Pf_muchos pf) {
		pf.getPforms().procesa(this);
		pf.getPf().procesa(this);
	}
	
	public void procesa(Pf_vacio pf) {}
	
	public void procesa(Pf_uno pf) {
		pf.getPf().procesa(this);
	}
	/*
	public void vincula2(Pf_valor pf) {
		this.vincula2(pf.getTipo());
	}
	
	public void vincula2(Pf_ref pf) {
		this.vincula2(pf.getTipo());
	}
	*/
	
	public void procesa(Pr pr) {
		pr.getExp().procesa(this);
	}
	public void procesa(Pr_muchos pr) {
		pr.getPreales().procesa(this);
		pr.getPr().procesa(this);
	}
	public void procesa(Pr_vacio pr) {}
	public void procesa(Pr_uno pr) {
		pr.getPr().procesa(this);
	}
	
	
	// Expresiones
	
	public void procesa(Id id) {
		int x = 30;
		if(existeId(id.id().toString())) {
			id.setVinculo(valorDe(id.id().toString())); // No sé si es getDec o no
		}
		else {
        	System.out.println("ERROR: Id");
			errores.add(tipoError.idNoDeclarado + ": " + id.toString());
		}
	}
	
	public void procesa (NumEnt n) {}
	public void procesa (NumReal n) {}
	public void procesa (TrueExp true_) {}
	public void procesa (FalseExp false_) {}
	public void procesa (StringExp str) {}
	public void procesa (NullExp n) {}
	
	public void procesa (Not exp) {
		exp.getExp().procesa(this);
	}
	
	public void procesa (MenosUnario exp) {
		exp.getExp().procesa(this);
	}
	
	public void procesa(Igual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Distinto exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Mayor exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(MayorIgual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Menor exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(MenorIgual exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(And exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Or exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Suma exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Resta exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Mul exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Div exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Mod exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Index exp) {
		exp.getFirst().procesa(this);
		exp.getSecond().procesa(this);
	}
	
	public void procesa(Indireccion exp) {
		exp.getExp().procesa(this);
	}
	
	public void procesa(AccesoRegistro exp) {
		exp.getExp().procesa(this);
	}
	
	
	
	// Instrucciones 
	
	public void procesa(Ins_una ins) {
		ins.in().procesa(this);
	}
	
	public void procesa(Ins_muchas ins) {
		ins.ins().procesa(this); 
		ins.in().procesa(this);
	}
	
	public void procesa(Ins_vacia ins) {}
		
	public void procesa(Asignacion asig) {
		asig.getFirst().procesa(this);
		asig.getSecond().procesa(this);
	}
	
	public void procesa(If_then ins) {
		ins.getExp().procesa(this);
		ins.getIns().procesa(this);
	}
	
	public void procesa(If_then_else ins) {
		ins.getExp().procesa(this);
		ins.getIns1().procesa(this);
		ins.getIns2().procesa(this);
	}
	
	public void procesa(While ins) {
		ins.getExp().procesa(this);
		ins.getIns().procesa(this);
	}
	
	public void procesa(Read ins) {
		ins.getExp().procesa(this);
	}
	
	public void procesa(Write ins) {
		ins.getExp().procesa(this);
	}
	
	public void procesa(New ins) {
		ins.getExp().procesa(this);
	}
	
	public void procesa(NewLine ins) {}
	
	public void procesa(Delete ins) {
		ins.getExp().procesa(this);
	}
	
	public void procesa(Seq seq) {
		this.abreNivel();
		seq.getDecs().procesa(this);
		seq.getDecs().procesa(new Procesa2());
		seq.getIns().procesa(this);
		this.cierraNivel();
	}
	
	public void procesa(Invoc_proc inv) {
		if(this.existeId(inv.getId().toString())) {
			inv.setVinculo(valorDe(inv.getId().toString()));
		}
		else {
        	System.out.println("ERROR: " + inv.getId().toString() + " - InvocProc");
			errores.add(tipoError.idNoDeclarado + ": " + inv.getId().toString());
		}
		inv.getPreales().procesa(this);
	}
	
	
	// Segunda pasada
	private class Procesa2 extends ProcesamientoPorDefecto{
		
		public void procesa(Decs_muchas decs) {
			decs.decs().procesa(this);
			decs.dec().procesa(this);
		}
		
		public void procesa(Decs_una dec) {
			dec.dec().procesa(this);
		}
		
		public void procesa(Decs_vacia dec) {}
		
		public void procesa(DecTipo dec) {
			dec.getTipo().procesa(this);
		}
		
		public void procesa(DecVar dec) {
			dec.getTipo().procesa(this);
		}
		
		public void procesa(DecProc dec) {
			dec.getPforms().procesa(this);
		}
		
		public void procesa(Int int_) {}
		
		public void procesa(Bool bool_) {}
		
		public void procesa(Real real_) {}
		
		public void procesa(StringTipo string_) {}
		
		public void procesa(Null null_) {}
		
		public void procesa(Array arr) {
			arr.getTipo().procesa(this);
		}
		
		public void procesa(RecordTipo rec) {
			rec.getCampos().procesa(this);
		}
		
		public void procesa(Pointer p) {
			if (p.getEnumTipo() == EnumTipo.REF) {
				Tipo apuntado = p.getApuntado();
				Ref r = (Ref)apuntado; 
				if(existeId(r.getId().toString())) {
					p.setVinculo(valorDe(r.getId().toString()));
				}
				else {
		        	System.out.println("ERROR:");
					errores.add(tipoError.idNoDeclarado + ": " + r.getId().toString());
				}
			}
			else {
				p.getTipo().procesa(this);
			}
		}
		
		public void procesa(Ref ref) {}
		
		public void procesa(Campo campo) {
			campo.getTipo().procesa(this);
		}
		
		public void procesa(Campos_muchos campos) {
			campos.getCampos().procesa(this);
			campos.getCampo().procesa(this);
		}
		
		public void procesa(Campos_uno campo) {
			campo.getCampo().procesa(this);
		}
		
		public void procesa(Pf_valor pf) {
			pf.getTipo().procesa(this);
		}
		
		public void procesa(Pf_ref pf) {
			pf.getTipo().procesa(this);
		}
		
		
		
		
		
	}
	
	
	

}
	