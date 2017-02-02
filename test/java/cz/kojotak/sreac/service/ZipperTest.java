package cz.kojotak.sreac.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cz.kojotak.sreac.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ZipperTest {

	@Autowired ZipperService zipper;
	
	@Test
	public void normalizeDiacritics() {
		String string =  "Příšerně žluťoučký kůň úpěl ďábelské ódy.";
		String nazev = zipper.nazevSouboru(string);
		assertEquals("priserne_zlutoucky_kun_upel_dabelske_ody", nazev);
	}

}
