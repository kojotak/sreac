package cz.kojotak.sreac.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cz.kojotak.sreac.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class AutowiredLoggerTest {

	@Autowired ZipperService zipper;
	@Autowired SRealityService sreality;
	@Autowired Logger logger;
	
	@Test
	public void differentLoggers() {
		assertNotEquals(logger.getName(), zipper.logger.getName());
		assertNotEquals(logger.getName(), sreality.logger.getName());
		assertNotEquals(zipper.logger.getName(), sreality.logger.getName());
	}

}
