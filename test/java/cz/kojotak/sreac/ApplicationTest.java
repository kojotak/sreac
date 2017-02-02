package cz.kojotak.sreac;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cz.kojotak.sreac.service.SRealityService;
import cz.kojotak.sreac.service.ZipperService;
import cz.kojotak.sreac.to.Inzerat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ApplicationTest {

	@Autowired SRealityService sreality;
	@Autowired ZipperService zipper;
	
	long idInzeratu = 3614822748L;
	
	@Test
	public void ziskejInzerat() {
		assertNotNull(sreality.stahni(idInzeratu));
	}
	
	@Test
	public void zipniInzerat(){
		Inzerat inzerat = sreality.stahni(idInzeratu);
		byte[] bajty = zipper.zabal(inzerat);
	}

}
