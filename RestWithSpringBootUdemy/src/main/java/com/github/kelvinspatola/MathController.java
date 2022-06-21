package com.github.kelvinspatola;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {
	
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/soma/{num1}/{num2}", method = RequestMethod.GET)
	public Double sum(@PathVariable(value = "num1") String num1, @PathVariable(value = "num2") String num2) throws Exception {
		
		if (!isNumeric(num1) || !isNumeric(num2)) {
			throw new Exception("It´s not a number!");
		}
		Double result = convertToDouble(num1) + convertToDouble(num2);
		return result;
	}

	private Double convertToDouble(String strNumber) {
		if (strNumber == null) return 0D;
		String number = strNumber.replaceAll(",", ".");
		if (isNumeric(number)) return Double.parseDouble(number);
		return 0D;
	}

	private boolean isNumeric(String strNumber) {
		if (strNumber == null) return false;
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
