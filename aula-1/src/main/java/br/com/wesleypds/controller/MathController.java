package br.com.wesleypds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wesleypds.service.MathService;

@RestController
@RequestMapping("/calculator")
public class MathController {

	@Autowired
	private MathService mathService;

	@GetMapping("/sum/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) {
		return mathService.sum(numberOne, numberTwo);
	}

	@GetMapping("/subtraction/{numberOne}/{numberTwo}")
	public Double subtraction(@PathVariable String numberOne, @PathVariable String numberTwo) {
		return mathService.subtraction(numberOne, numberTwo);
	}

	@GetMapping("/multiplication/{numberOne}/{numberTwo}")
	public Double multiplication(@PathVariable String numberOne, @PathVariable String numberTwo) {
		return mathService.multiplication(numberOne, numberTwo);
	}

	@GetMapping("/division/{numberOne}/{numberTwo}")
	public Double division(@PathVariable String numberOne, @PathVariable String numberTwo) {
		return mathService.division(numberOne, numberTwo);
	}

	@GetMapping("/average/{numberOne}/{numberTwo}")
	public Double average(@PathVariable String numberOne, @PathVariable String numberTwo) {
		return mathService.average(numberOne, numberTwo);
	}

	@GetMapping("/square-root/{number}")
	public Double squareRoot(@PathVariable String number) {
		return mathService.squareRoot(number);
	}

}
