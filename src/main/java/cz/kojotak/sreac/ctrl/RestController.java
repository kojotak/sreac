package cz.kojotak.sreac.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.kojotak.sreac.service.SRealityService;
import cz.kojotak.sreac.service.ZipperService;
import cz.kojotak.sreac.to.Inzerat;

@Controller
public class RestController {

	@Autowired ZipperService zipper;
	@Autowired SRealityService sreality;
	Logger logger = LoggerFactory.getLogger(RestController.class);

	@RequestMapping(value = "/testzip/{srealityId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> inzerat(@PathVariable long srealityId) {
		logger.info("looking for: "+srealityId);
		Inzerat inzerat = sreality.stahni(srealityId);
		logger.info("downloaded: " + inzerat);
		byte[] data = zipper.zabal(inzerat);
		logger.info("packed into: "+data.length+" b");
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sreality_"+inzerat.id+".zip");
		return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
	}
}