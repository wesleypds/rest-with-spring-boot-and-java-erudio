package br.com.wesleypds.service;

import org.springframework.stereotype.Service;

import br.com.wesleypds.exceptions.UnsupportedMathOperationException;

@Service
public class MathService {

    public Double sum(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    public Double subtraction(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    public Double multiplication(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    public Double division(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    public Double average(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2D;
    }

    public Double squareRoot(String number) {
        if (!isNumeric(number)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return Math.sqrt(convertToDouble(number));
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
