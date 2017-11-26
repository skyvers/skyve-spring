package hello.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelloController {

	@RequestMapping("/hello")
    public String index() {
		return "Greetings from Spring Boot!";
    }

	/*@GetMapping("/")
	public ModelAndView firstPage() {
		return new ModelAndView("home");
	}*/

	@RequestMapping("/home")
	public ModelAndView firstPage(HttpServletRequest request) {
		return new ModelAndView("home");
	}
    
}
