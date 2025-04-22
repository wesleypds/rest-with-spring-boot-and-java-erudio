package curso.spring.boot.service;

import org.springframework.stereotype.Service;

import curso.spring.boot.exception.UnsupportedMathOperationException;

@Service
public class MathService {

    public Double sum(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) 
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    public Double sub(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) 
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    public Double mult(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) 
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    public Double div(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) 
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        if (!isPossibleDiv(numberTwo))
            throw new UnsupportedMathOperationException("Can't divide by zero!");

        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    private boolean isPossibleDiv(String strNumber) {
        if (convertToDouble(strNumber) == 0) return false;
        return true;
    }

    private Double convertToDouble(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) 
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        String number = strNumber.replace(",", ".");
        return Double.parseDouble(number);
    }

    private boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",", ".");
        return (number.matches("[-+]?[0-9]*\\.?[0-9]+"));
    }

}
