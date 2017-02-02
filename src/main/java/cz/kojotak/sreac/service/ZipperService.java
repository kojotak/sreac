package cz.kojotak.sreac.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.kojotak.sreac.to.Inzerat;
import cz.kojotak.sreac.to.Priloha;

@Service
public class ZipperService {

	@Autowired Charset defaultCharset;
	@Autowired Logger logger;

	public byte[] zabal(Inzerat inzerat, Map<Priloha, byte[]> prilohy) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try(ZipOutputStream zipStream = new ZipOutputStream(out)){
			if(inzerat!=null && inzerat.json !=null){
				zipStream.putNextEntry(new ZipEntry(""+inzerat.id+".json"));
				zipStream.write(inzerat.json.getBytes(defaultCharset));
				zipStream.closeEntry();
			}
			int pocitadlo = 0;
			for(Map.Entry<Priloha, byte[]> priloha : prilohy.entrySet()){
				byte[] data = priloha.getValue();
				if(data==null || data.length ==0){
					logger.debug("skipping empty "+priloha.getKey());
					continue;
				}
				String normalizovany = nazevSouboru(priloha.getKey().nazev);
				String nazevObrazku = String.format("%02d_%s.jpg", ++pocitadlo, normalizovany);
				zipStream.putNextEntry(new ZipEntry(nazevObrazku));
				zipStream.write(priloha.getValue());
				zipStream.closeEntry();
			}
			zipStream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	String nazevSouboru(String nazevObrazku) {
		String str = "";
		if (nazevObrazku != null) {
			str = Normalizer.normalize(nazevObrazku, Normalizer.Form.NFD);
			str = str.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			str = str.toLowerCase();
			str = str.replaceAll("\\s+", "_");
			str = str.replaceAll("[^\\p{Alnum}\\_]", "");
		}
		return str;
	}
}
