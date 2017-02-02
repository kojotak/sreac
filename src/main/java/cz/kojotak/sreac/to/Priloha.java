package cz.kojotak.sreac.to;

public class Priloha {

	public final String url;
	public final String nazev;

	public Priloha(String url, String nazev) {
		super();
		this.url = url;
		this.nazev = nazev;
	}

	@Override
	public String toString() {
		return "Priloha [url=" + url + ", nazev=" + nazev + "]";
	}
}
