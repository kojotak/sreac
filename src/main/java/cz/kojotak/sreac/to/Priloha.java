package cz.kojotak.sreac.to;

public class Priloha {

	public final String url;
	public final String nazev;
	public final byte[] data;

	public Priloha(String url, String nazev, byte[] data) {
		super();
		this.url = url;
		this.nazev = nazev;
		this.data = data;
	}

	@Override
	public String toString() {
		return "PojmenovaneUrl [url=" + url + ", nazev=" + nazev + "]";
	}
	
}
