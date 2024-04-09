package test2;

public class cos {
	private int nrProduse, total;
	private Produs produs[];
	private int ID;
	
	public void adaugaProdusInCos(Produs p, int a) {
		if(this.nrProduse == 0) this.setnrProduse(1);
		else this.setnrProduse(nrProduse + 1);
		this.setProdus(p, nrProduse);
	}
	
	public void setProdus(Produs p, int x) {
		this.produs[x] = p;
	}
	
	public void updateTotal(Produs p, int a) {
		int pret = p.getPret() * a;
		this.total = pret + this.total;
	}
	
	public void setID(int x) {
		this.ID = x;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setnrProduse(int x) {
		this.nrProduse = x;
	}
	
	public int getnrProduse() {
		return this.nrProduse;
	}
}


