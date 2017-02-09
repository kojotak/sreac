package cz.kojotak.sreac.ctrl;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cz.kojotak.sreac.service.SRealityService;
import cz.kojotak.sreac.service.ZipperService;
import cz.kojotak.sreac.to.Inzerat;
import cz.kojotak.sreac.to.Priloha;
import cz.kojotak.sreac.to.Vyhledani;

@Controller
public class WebController {

	private static final String TEMPLATE = "thymeleaf";

	@Autowired ZipperService zipper;
	@Autowired SRealityService sreality;
	@Autowired Logger logger;
	
	@GetMapping("/")
	public String show(Model model) {
		model.addAttribute("vyhledani", new Vyhledani());
		return TEMPLATE;
	}
	
	@PostMapping("/")
	public String find(
			@ModelAttribute Vyhledani vyhledani,
			Model model, BindingResult binding){
		logger.info("find: " + vyhledani);
		model.addAttribute("vyhledani", vyhledani);
		Long idInzeratu = sreality.idInzeratu(vyhledani.getUrl());
		if(idInzeratu!=null){
			Inzerat inzerat = sreality.stahniInzerat(idInzeratu);
			model.addAttribute("inzerat", inzerat);
		}else{
			binding.addError(new ObjectError("x", "Tohle nikam nevedlo"));
			vyhledani.setUrl(null);
		}
		return TEMPLATE;
	}

	@GetMapping("/zip/{srealityId}")
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
