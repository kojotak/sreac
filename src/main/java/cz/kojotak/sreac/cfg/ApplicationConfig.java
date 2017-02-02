package cz.kojotak.sreac.cfg;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import cz.kojotak.sreac.Application;
import cz.kojotak.sreac.util.AutowiredLogger;

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
	
	@Bean
	@Primary
	public BeanPostProcessor autowiredLogger(){
		return new AutowiredLogger();
	}
	
	@Bean
	public Logger defaultLogger(){
		return LoggerFactory.getLogger(Application.class);
	}
	
	@Bean
	@Primary
	public ObjectMapper prettyPrinter(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper;
	}
}
