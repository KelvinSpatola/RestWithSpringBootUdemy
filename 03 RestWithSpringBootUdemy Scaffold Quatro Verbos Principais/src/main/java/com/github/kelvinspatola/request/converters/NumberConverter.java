package com.github.kelvinspatola.request.converters;

public class NumberConverter {
	
	static public Double convertToDouble(String strNumber) {
		if (strNumber == null) return 0D;
		String number = strNumber.replaceAll(",", ".");
		if (isNumeric(number)) return Double.parseDouble(number);
		return 0D;
	}

	static public boolean isNumeric(String strNumber) {
		if (strNumber == null) return false;
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
	static public boolean isValidOperator(Character oper) {
		if (oper == null) return false; 
		return "+-*/".contains(oper.toString());
	}
}
