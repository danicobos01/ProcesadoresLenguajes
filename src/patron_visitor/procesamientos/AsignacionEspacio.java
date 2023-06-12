package patron_visitor.procesamientos;

import patron_visitor.asint.ProcesamientoPorDefecto;
import patron_visitor.asint.TinyASint.*;

public class AsignacionEspacio extends ProcesamientoPorDefecto {
	
	private int dir = 0;
	private int nivel = 0;
	

	public void procesa(Prog_con_decs prog) {
		prog.getDeclaraciones().procesa(this);
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
	
	public void procesa(Decs_vacia decs) {}
	
	public void procesa(DecVar dec) {
		dec.setNivel(this.nivel);
		dec.setDir(this.dir);
		dec.getTipo().procesa(this);
		this.dir += dec.getTipo().getTam();
	}
	
	public void procesa(DecTipo dec) {
		dec.getTipo().procesa(this);
	}
	
	public void procesa(DecProc dec) {
		int dirAnt = this.dir;
		this.nivel++;
		dec.setNivel(this.nivel);
		this.dir = 0;
		dec.getPforms().procesa(this);
		dec.getDecs().procesa(this);
		dec.getIns().procesa(this);
		dec.setTam(this.dir);
		this.dir = dirAnt;
		this.nivel--;
	}
	

	public void procesa(Int int_) {
		int_.setTam(1);
	}
	public void procesa(Real real) {
		real.setTam(1);
	}
	public void procesa(Bool bool) {
		bool.setTam(1);
	}
	public void procesa(StringTipo str) {
		str.setTam(1);
	}
	public void procesa(Null null_) {}
	
	
	public void procesa(Array arr) {
		arr.getTipoElems().procesa(this);
	}
	
	public void procesa(RecordTipo rec) {
		rec.setTam(desplazamiento(rec.getCampos())); 
	}
	
	public void procesa(Pointer p) {
		p.setTam(1);
		if(p.getApuntado() instanceof Ref) {
			p.getTipo().procesa(this);
		}
	}
	
	public void procesa(Ref ref) {
		DecTipo dec = (DecTipo) ref.getVinculo();
		ref.setTam(dec.getTipo().getTam());
	}
	
	
	 
	public void procesa(Campo campo) {}
	
	public void procesa(Campos_muchos campos) {
		campos.getCampos().procesa(this);
		campos.getCampo().getTipo().procesa(this);
	}
	
	public void procesa(Campos_uno campos) {
		campos.getCampo().getTipo().procesa(this);
	}
	
	public int desplazamiento(Campos_uno campos) {
		campos.setDespl(0);
		campos.getTipoNodo().procesa(this);
		return campos.getTipoNodo().getTam();
	}
	
	public int desplazamiento(Campos campos) {
		return -1;
	}
	
	public int desplazamiento(Campos_muchos campos) {
		int desp = desplazamiento(campos.getCampos());
		campos.getCampo().getTipo().procesa(this);
		campos.getCampo().setDespl(desp);
		return desp + campos.getCampo().getTipo().getTam();
	}
	
	
	// Parámetros
	
	public void procesa(Pf_valor p) {
		p.setDir(this.dir);
		p.setNivel(this.nivel);
		p.getTipo().procesa(this);
		this.dir += p.getTipo().getTam();
	}
	
	public void procesa(Pf_ref p) {
		p.setDir(this.dir);
		p.setNivel(this.nivel);
		p.getTipo().procesa(this);
		this.dir += 1;
	}
	
	
	public void procesa(Pf_muchos pf) {
		pf.getPforms().procesa(this);
		pf.getPf().procesa(this);
	}
	
	public void procesa(Pf_uno pf) {
		pf.getPf().procesa(this);
	}
	
	public void procesa(Pf_vacio pf) {}
	 
	
	
	
	// Instrucciones
	public void procesa(Asignacion ins) {}
	
	public void procesa(If_then ins) {
		ins.getIns().procesa(this);
	}
	
	public void procesa(If_then_else ins) {
		ins.getIns1().procesa(this);
		ins.getIns2().procesa(this);
	}
	
	public void procesa(While ins) {
		ins.getIns().procesa(this);
	}
	
	public void procesa(Read ins) {}
	
	public void procesa(Write ins) {}
	
	public void procesa(NewLine ins) {}
	
	public void procesa(New ins) {}
	
	public void procesa(Delete ins) {}
	
	public void procesa(Seq ins) {
		int dirAnt = this.dir;
		ins.getDecs().procesa(this);
		ins.getIns().procesa(this);
		this.dir = dirAnt;
	}
	
	public void procesa(Invoc_proc ins) {}
	
	
	public void procesa(Ins_muchas ins) {
		ins.ins().procesa(this);
		ins.in().procesa(this);
	}
	
	public void procesa(Ins_una ins) {
		ins.in().procesa(this);
	}
	
	public void procesa (Ins_vacia ins) {}
	
	
	
}