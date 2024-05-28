package test2;

public class Produs {
	private boolean disponibilitate, valabilitate;
	private String nume;
	private int pret, calorii, id;
	private int expira_zi, expira_luna, expira_an;
	private String type;
	
	public Produs() {
		this.nume = null;
		this.disponibilitate = false;
		this.valabilitate = false;
		this.pret = 0;
		this.calorii = 0;
		this.expira_an = 0;
		this.expira_luna = 0;
		this.expira_zi = 0;
		setType(null);
		setId(0);
	}
	
	public Produs(String d, String v, String n, int p, int c, int ea, int el, int ez, String tip, int id) {
		this.nume = n;
		if(d.equals("da")) this.disponibilitate = true;
		else this.disponibilitate = false;
		if(v.equals("da")) this.valabilitate = true;
		else this.valabilitate = false;
		this.pret = p;
		this.calorii = c;
		this.expira_an = ea;
		this.expira_luna = el;
		this.expira_zi = ez;
		this.setType(tip);
		this.setId(id);
	}
	
	public void afisareProdus() {
		System.out.println(id + " " + nume + " " + valabilitate + " " + disponibilitate + " " + pret + " " + expira_zi + " " + expira_luna + " " + expira_an + " " + type);
	}
	
	public void setNume(String x) {
		this.nume = x;
	}
	
	public String getNume() {
		return this.nume;
	}
	
	public void setDisponibilitate(boolean x) {
		this.disponibilitate = x;
	}
	
	public boolean getDisponibilitate() {
		return this.disponibilitate;
	}
	
	public void setValabilitate(boolean x) {
		this.valabilitate = x;
	}
	
	public boolean getValabilitate() {
		return this.valabilitate;
	}
	
	public void setPret(int x) {
		this.pret = x;
	}
	
	public int getPret() {
		return this.pret;
	}
	
	public void setCalorii(int x) {
		this.calorii = x;
	}
	
	public int getCalorii() {
		return this.calorii;
	}
	
	public void setExpira_zi(int x) {
		this.expira_zi = x;
	}
	
	public int getExpira_zi() {
		return this.expira_zi;
	}
	
	public void setExpira_luna(int x) {
		this.expira_luna = x;
	}
	
	public int getExpira_luna() {
		return this.expira_luna;
	}
	
	public void setExpira_an(int x) {
		this.expira_an = x;
	}
	
	public int getExpira_an() {
		return this.expira_an;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
