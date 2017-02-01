package cz.kojotak.sreac.cfg;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Bean
	public Charset defaultCharset(){
		try{
			return Charset.forName("UTF-8");
		}catch(UnsupportedCharsetException e){
			throw new RuntimeException(e);
		}
	}
	
}
