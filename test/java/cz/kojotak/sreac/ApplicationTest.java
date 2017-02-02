package cz.kojotak.sreac;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import cz.kojotak.sreac.ctrl.WebController;
import cz.kojotak.sreac.service.SRealityService;
import cz.kojotak.sreac.service.ZipperService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ApplicationTest {

	@Autowired SRealityService sreality;
	@Autowired ZipperService zipper;
	@Autowired WebController controller;
	
	long idInzeratu = 3614822748L;
	
	@Test
	public void ziskejInzerat() {
		assertNotNull(sreality.stahniInzerat(idInzeratu));
	}
	
	@Test
	public void zipniInzerat(){
		ResponseEntity<byte[]> entity = controller.inzerat(idInzeratu);
		assertNotNull(entity);
		byte[] zip = entity.getBody();
		assertNotNull(zip);
	}

}
