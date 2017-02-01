package cz.kojotak.sreac.to;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;
public class Inzerat {

	public final long id;
	public final String nazev;
	public final String popis;
	public final String meta;
	public final Point2D.Double gps;
	public final Integer cena;
	public final String json;
	public final List<Priloha> obrazky;
	
	public Inzerat(long id,String nazev, String popis, String meta, Double gps, Integer cena, String json, List<Priloha> obrazky) {
		this.id = id;
		this.nazev = nazev;
		this.popis = popis;
		this.meta = meta;
		this.gps = gps;
		this.cena = cena;
		this.json = json;
		this.obrazky = obrazky!= null ? unmodifiableList(obrazky) : emptyList();
	}

	@Override
	public String toString() {
		return "Inzerat [id=" + id + ", nazev=" + nazev + ", popis=" + popis + ", meta=" + meta + ", gps=" + gps
				+ ", cena=" + cena + ", obrazky=" + obrazky + "]";
	}
	
}
