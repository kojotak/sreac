package cz.kojotak.sreac.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

	@Autowired JdbcTemplate jdbc;
	
	@ResponseBody
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}
	
	@ResponseBody
	@RequestMapping("/jdbc")
	String jdbc(){
		return jdbc.queryForObject("select 'hello jdbc'", String.class);
	}

}
