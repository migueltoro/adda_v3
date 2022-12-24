package us.lsi.curvefitting;

public enum FittingType {
	LINEAL, // a + n*b
	POWERLOG, // a*n^b*(ln n)^c + d
	POWERLOG2, // a*n^b*(ln n)^c
	POWERLOG3, // a*n^b*(ln n)
	POLYNOMIAL, //a*n^b + ... + d
	POWER, // a*n^b + c
	POWER2, // a*n^b
	LOG, // a*(ln n) + b
	LOG2, // a*(ln n)
	NLOGN, // a*n*(ln n) + b
	NLOGN2, // a*n*(ln n)
	EXP, // a*b^(c*n) + d
	EXP2, // a*b^n + c
	EXP3  // a*b^n
}
