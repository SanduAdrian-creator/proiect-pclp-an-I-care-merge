package test2;

public class client {
	private int ID, buget;
	private String nume;
	private int card_bancar;
	private cos cosul;
	
	public void adaugaProdus(Produs p, int a) {
		this.cosul.adaugaProdusInCos(p, a);
	}
	
	public void setID(int x) {
		this.ID = x;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setBuget(int x) {
		this.buget = x;
	}
	
	public int getBuget() {
		return this.buget;
	}
	
	public void setNume(String x) {
		this.nume = x;
	}
	
	public String getNume() {
		return this.nume;
	}
	
	public void setCard(int x) {
		this.card_bancar = x;
	}
	
	public int getCard() {
		return this.card_bancar;
	}
}
