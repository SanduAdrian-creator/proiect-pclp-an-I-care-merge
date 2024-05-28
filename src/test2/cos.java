package test2;

public class cos {
	private int nrProduse, total;
	private Produs produs[];
	private int kg[];
	private int ID;
	
	public cos(int size) {
        produs = new Produs[size];
        kg = new int[size];
    }
	
	public void adaugaProdusInCos(Produs p, int a) {
		if(this.nrProduse == 0) this.setnrProduse(1);
		else this.setnrProduse(nrProduse + 1);
		this.setProdus(p, nrProduse, a);
	}
	
	public void setProdus(Produs p, int x, int k) {
		this.produs[x] = p;
		this.kg[x] = k;
	}
	
	public void updateTotal(Produs p, int a) {
		int pret = p.getPret() * a;
		this.total = pret + this.total;
	}
	
	public int gettotal() {
		return this.total;
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
	
	public int getKgProdus(int i) {
		return kg[i];
	}
	
	public void afisareProduse() {
		for(int i = 1; i <= nrProduse; i++)
			produs[i].afisareProdus();
	}
	
	public Produs getProdus(int i) {
		return produs[i];
	}
}


