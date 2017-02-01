/**
 * 
 */
package cz.kojotak.sreac.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Configures JSON mapper.
 */
@Configuration
public class JsonMapperConfig {

	@Bean
	public ObjectMapper prettyPrinter(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper;
	}
}
