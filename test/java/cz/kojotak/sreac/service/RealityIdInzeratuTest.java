package cz.kojotak.sreac.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cz.kojotak.sreac.Application;

@RunWith(JUnit4.class)
public class RealityIdInzeratuTest {

	SRealityService sreality = new SRealityService();
	
	@Test
	public void happyDay() {
		testIdInzeratu(2145964380L, "https://www.sreality.cz/detail/prodej/byt/3+kk/cesky-brod-liblice-k-vysilaci/2145964380#img=0&fullscreen=false");
	}
	
	@Test
	public void nullString() {
		testIdInzeratu(null, null);
	}
	
	@Test
	public void badDomain(){
		testIdInzeratu(null, "https://foo.bar/baz/321654");
	}
	
	@Test
	public void badUrl(){
		testIdInzeratu(null, "https://www.sreality.cz/foo/bar/baz?serious=false");
	}
	
	private void testIdInzeratu(Long expected, String url){
		Long idInzeratu = sreality.idInzeratu(url);
		if(expected!=null){
			assertNotNull(idInzeratu);
			assertEquals(expected, idInzeratu);
		}else{
			assertNull(idInzeratu);
		}
	}

}
