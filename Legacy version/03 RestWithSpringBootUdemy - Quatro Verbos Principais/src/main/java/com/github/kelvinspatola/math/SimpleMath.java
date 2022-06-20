package com.github.kelvinspatola.math;

public class SimpleMath {
	
	public Double sum(Double a, Double b) {
		return a + b;
	}
	
	public Double sub(Double a, Double b) {
		return a - b;
	}
	
	public Double mult(Double a, Double b) {
		return a * b;
	}
	
	public Double div(Double a, Double b) {
		return a / b;
	}
	
	public Double avg(Double a, Double b) {
		return sum(a, b) / 2;
	}
	
	public Double sqrt(Double a) {
		return Math.sqrt(a);
	}
}
