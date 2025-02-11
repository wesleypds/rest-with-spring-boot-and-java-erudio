package br.com.wesleypds.rest_with_spring_boot_and_java_erudio;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
	
	private static final String template = "Hello, %s!";
	
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping
	public Greeting greeting(@RequestParam(defaultValue = "World") String name ) {
		
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
		
	}

}
