package com.github.kelvinspatola.controllers;

import static com.github.kelvinspatola.converters.NumberConverter.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.kelvinspatola.exceptions.UnsupportedMathOperationException;
import com.github.kelvinspatola.math.SimpleMath;

@RestController
public class MathController {

	@Autowired
	private SimpleMath math;

	@RequestMapping(value = "/sum/{num1}/{num2}", method = RequestMethod.GET)
	public Double sum(@PathVariable(value = "num1") String num1, @PathVariable(value = "num2") String num2)
			throws Exception {

		if (!isNumeric(num1) || !isNumeric(num2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		Double result = convertToDouble(num1) + convertToDouble(num2);
		return result;
	}

	@RequestMapping(value = "/sub/{num1}/{num2}", method = RequestMethod.GET)
	public Double sub(@PathVariable("num1") String num1, @PathVariable("num2") String num2) throws Exception {
		if (!isNumeric(num1) || !isNumeric(num2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		return math.sub(convertToDouble(num1), convertToDouble(num2));
	}

	@RequestMapping(value = "/mult/{num1}/{num2}", method = RequestMethod.GET)
	public Double mult(@PathVariable("num1") String num1, @PathVariable("num2") String num2) throws Exception {
		if (!isNumeric(num1) || !isNumeric(num2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		return math.mult(convertToDouble(num1), convertToDouble(num2));
	}

	@RequestMapping(value = "/div/{num1}/{num2}", method = RequestMethod.GET)
	public Double div(@PathVariable("num1") String num1, @PathVariable("num2") String num2)
			throws Exception, ArithmeticException {
		if (!isNumeric(num1) || !isNumeric(num2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		if (convertToDouble(num2) == 0D) {
			throw new ArithmeticException("Division by zero is not allowed!");
		}
		return math.div(convertToDouble(num1), convertToDouble(num2));
	}

	@RequestMapping(value = "/avg/{num1}/{num2}", method = RequestMethod.GET)
	public Double avg(@PathVariable("num1") String num1, @PathVariable("num2") String num2) throws Exception {
		if (!isNumeric(num1) || !isNumeric(num2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		return math.avg(convertToDouble(num1), convertToDouble(num2));
	}

	@RequestMapping(value = "/sqrt/{num1}", method = RequestMethod.GET)
	public Double sqrt(@PathVariable("num1") String num1) throws Exception {
		if (!isNumeric(num1)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		return math.sqrt(convertToDouble(num1));
	}
}
