package com.github.kelvinspatola.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.kelvinspatola.exception.UnsupportedMathOperationException;
import com.github.kelvinspatola.math.SimpleMath;
import static com.github.kelvinspatola.request.converters.NumberConverter.*;

@RestController
public class MathController {
	private SimpleMath math = new SimpleMath();	

	@RequestMapping(value="/sum/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		} 
		return math.sum(convertToDouble(numberOne), convertToDouble(numberTwo));
	}
	
	@RequestMapping(value="/sub/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double sub(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		} 
		return math.sub(convertToDouble(numberOne), convertToDouble(numberTwo));
	}
	
	@RequestMapping(value="/mult/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double mult(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		} 
		return math.mult(convertToDouble(numberOne), convertToDouble(numberTwo));
	}
	
	@RequestMapping(value="/div/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double div(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception, ArithmeticException {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		} 
		if (convertToDouble(numberTwo) == 0D) {
			throw new ArithmeticException("Division by zero is not allowed!");
		}
		return math.div(convertToDouble(numberOne), convertToDouble(numberTwo));
	}
	
	@RequestMapping(value="/avg/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double avg(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		} 
		return math.avg(convertToDouble(numberOne), convertToDouble(numberTwo));
	}
	
	@RequestMapping(value="/sqrt/{numberOne}", method=RequestMethod.GET)
	public Double sqrt(@PathVariable("numberOne") String numberOne) throws Exception {
		if (!isNumeric(numberOne)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		} 
		return math.sqrt(convertToDouble(numberOne));
	}
	
	@RequestMapping(value="/operation/{numberOne}/{numberTwo}/{oper}", method=RequestMethod.GET)
	public Double operation(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo, @PathVariable("oper") Character oper) throws Exception {
		if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		} 
		if (!isValidOperator(oper)) {
			throw new UnsupportedMathOperationException("This is not a supported operator!");
		} 
		
		switch (oper) {
		case '+': return math.sum(convertToDouble(numberOne), convertToDouble(numberTwo));
		case '-': return math.sub(convertToDouble(numberOne), convertToDouble(numberTwo));
		case '*': return math.mult(convertToDouble(numberOne), convertToDouble(numberTwo));
		case 'd': return math.div(convertToDouble(numberOne), convertToDouble(numberTwo));
		default: return 0D;
		}
	}
}
