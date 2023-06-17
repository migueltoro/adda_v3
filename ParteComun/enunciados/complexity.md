
# Complexity

Cálculo empírico de la complejidad de un algoritmo

Se compone de dos partes: Generación de datos y ajuste de curvas.

## Generación de datos

- La clase _GenData_ genera los ficheros de tiempos de ejecución asociados a tamaños. Tiene dos métodos:
	- Function\<Integer,Long\> time(Consumer\<Integer\> algorithm): devuelve una función que asocia un tamaño al tiempo tardado en ejecutar el consumer algorithm.
	- Function\<Integer,Long\> time(Consumer\<Integer\> preparation, Consumer\<Integer\> algorithm): devuelve una función que asocia un tamaño al tiempo tardado en preparar los datos y posteriormente ejecutar el consumer algorithm.
	- tiemposEjecucion(Function\<Integer,Long\> algorithm, String ficheroTiempos, Integer tMin, Integer tMax, Integer tInc, Integer numIter, Integer numIterWarmup): Este método genera un fichero de datos _ficheroTiempos_ con datos de tamaños que van de _tMin_ a _tMax_ con incrementos de _tInc_. Se establecen un número de iteraciones para obtener la media del tiempo de ejecución para cada tamaño. El entero _numIterWarmup_ establece, para cada tamaño, un número de ejecuciones previas a las medidas necesarias para calcular la media.

## Ajuste de curvas

- La clase _DataFile_ lee un fichero de datos de ejecución y ajusta la curva elegida con el método:
	- List\<WeightedObservedPoint\> points(String file).
- La clase _Fitting_ ajusta una lista de puntos al tipo de curva elegido con el método:
	- Pair\<Function\<Double, Double\>, String\> fitCurve(List\<WeightedObservedPoint\> points, FittingType tipoAjuste). El método devuelve un par con una función que calcula los valores del ajuste para cada tamaño y el título del ajuste.
- Los tipos posibles de ajustes son:
	 - LINEAL, // a + n*b
	 - POWERLOG, // a*n^b*(ln n)^c + d
	 - POWERLOG2, // a*n^b*(ln n)^c
	 - POWERLOG3, // a*n^b*(ln n)
	 - POLYNOMIAL, //a*n^b + ... + d
	 - POWER, // a*n^b + c
	 - POWER2, // a*n^b
	 - LOG, // a*(ln n) + b
	 - LOG2, // a*(ln n)
	 - NLOGN, // a*n*(ln n) + b
	 - NLOGN2, // a*n*(ln n)
	 - EXP, // a*b^(c*n) + d
	 - EXP2, // a*b^n + c
	 - EXP3 // a*b^n

- La clase _MatPlotLib_ representa un ajuste con el método: 
	- show(String dataFile, Function\<Double, Double\> f, String title). Esta clase necesita tener Pyhton instalado
- La clase _CanvasPlot_ representa un ajuste con el método: 
	- show(String dataFile, Function\<Double, Double\> f, String title). Esta clase no necesita tener Pyhton instalado aunque los resultados son de menor calidad que con el método anterior