package cz.kojotak.sreac.ctrl;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.kojotak.sreac.service.SRealityService;
import cz.kojotak.sreac.service.ZipperService;
import cz.kojotak.sreac.to.Inzerat;
import cz.kojotak.sreac.to.Priloha;

@Controller
public class WebController {

	@Autowired ZipperService zipper;
	@Autowired SRealityService sreality;
	@Autowired Logger logger;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("name", "world");
		return "thymeleaf";
	}

	@RequestMapping(value = "/testzip/{srealityId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> inzerat(@PathVariable long srealityId) {
		logger.info("looking for: "+srealityId);
		Inzerat inzerat = sreality.stahniInzerat(srealityId);
		logger.info("fetched: " + inzerat);
		Map<Priloha, byte[]> prilohy = sreality.stahniPrilohy(inzerat);
		logger.debug("downloaded "+prilohy.size()+" images");
		byte[] data = zipper.zabal(inzerat, prilohy);
		logger.info("packed into: "+data.length+" b");
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sreality_"+inzerat.id+".zip");
		return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
	}
}
