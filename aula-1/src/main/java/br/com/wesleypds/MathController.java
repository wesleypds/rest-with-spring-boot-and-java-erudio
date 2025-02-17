package br.com.wesleypds;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wesleypds.exceptions.UnsupportedMathOperationException;

@RestController
@RequestMapping("/sum")
public class MathController {

	@GetMapping("/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) throws Exception {

		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return convertToDouble(numberOne) + convertToDouble(numberTwo);
	}

	private Double convertToDouble(String number) {
		if (number == null) return 0D;
		number = number.replaceAll(",", ".");
		if (isNumeric(number)) return Double.parseDouble(number);
		return 0D;
	}

	private boolean isNumeric(String number) {
		if (number == null) return false;
		number = number.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}

}
