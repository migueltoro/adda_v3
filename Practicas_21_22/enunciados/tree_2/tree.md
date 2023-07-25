# Ejercicio 2: Árboles

Implemente en Java la siguiente función recursiva:

String caminoStringMinimo (Tree\<String\> t, Predicate\<String\> p), que dado un árbol n-ario de tipo String y un predicado sobre String, devuelva la cadena de menor longitud que cumpla que es el resultado de concatenar las etiquetas de un camino desde la raíz hasta un nodo hoja (no vacío) sin pasar por ningún nodo cuya etiqueta cumpla el predicado.

No está permitido usar métodos que obtengan todos los hijos de un nivel dado de un árbol (cada llamada recursiva debe recibir como entrada un objeto de tipo árbol diferente).

Ejemplo.
Entrada:

```
t = ab(bb(_,acc(d,ec,f),ffff),abf(b(f),c))
p = El String de entrada contiene la letra c.
Salida: “ababfbf” (longitud 7)
```