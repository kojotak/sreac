package cz.kojotak.sreac.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
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
	
	public byte[] zabal(Inzerat inzerat) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try(ZipOutputStream zipStream = new ZipOutputStream(out)){
			zipStream.putNextEntry(new ZipEntry(""+inzerat.id+".json"));
			zipStream.write(inzerat.json.getBytes(defaultCharset));
			zipStream.closeEntry();
			int pocitadlo = 0;
			for(Priloha obrazek : inzerat.obrazky){
				String nazevObrazku = String.format("obrazek_%02d.jpg", ++pocitadlo);
				zipStream.putNextEntry(new ZipEntry(nazevObrazku));
				zipStream.write(obrazek.data);
				zipStream.closeEntry();
			}
			zipStream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return out.toByteArray();
	}

}
