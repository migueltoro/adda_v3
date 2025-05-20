# Notación para los algoritmos de PD

- Por $p$, representaremos un problema que en general vendrá definido por sus propiedades básicas y por $p_0$.
- Por $\bot$ representamos el valor nulo y asumimos que $\bot$ operado con cualquier otro valor resulará 
$\bot$.
- Por $gr$ un grafo el problema inicial
- Por $gp$ un camino en un grafo
- Por $(i,j) \in gr$ que la arista $i, j$ pertenece al grafo
- Por $w(i,j)$ el peso de la arista correspondiente del grafo
- Por $(a,w)$ una solución parcial, $Sp$,  formada por una alternativa y un peso. Asumimos que $a$ puede ser $\bot$ pero si $w$ es $\bot$ lo es la solución parcial. Por lo tanto $w$ no puede ser nunca $\bot$. Las soluciones parciales tienen un orden natural defininido por el de $w$.
- Usaremos la función $f(p)$ para calcular una solución parcial del problema $p$ de tipo $P$. La función $f$ devuelve una solución parcial y por eficiencia la guardamos en un $Map\<P,Sp\>$. Los valores de la función $f(p)$ tienen dos componentes que representamos por $f_a(p)$ y por $f_w(p)$. Entonces $f(p)=(f_a (p),f_w (p))$. El valor óptimo será $f_w(p_0)$.
- Usaremos la función $g(p,a)$ para calcular la solución parcial de $p$ si se toma la alternativa $a$
- Usaremos la función $s(p)$ para calcular la solución de $p$ de tipo $S$
- Usaremos ${}, ms + (ob,a)$ para representar el multiset vacío y añadir $a$ unidades de $ob$ al multiset $ms$. Si $ob$ o $a$ es nulo,o el resultado es nulo. Usaremos la misma notación para variables de tipo Map.
- Usaremos $[], a + ls$  para representar la lista vacía y añadir $a$ a la lista $ls$ por la izquierda  y  $ls+a$  para añadir $a$ por la derecha. Si $a$ es nulo el resultado es nulo. Usaremos $[0,…,0]_m$ para representar una lista con $m$ ceros y $ls+(i,ls[i]+d)$ para sumar $d$ al valor de la casilla $i$ de $ls$.
- Para agregar los elementos de una lista en forma de árbol usaremos un BinaryTree<M> con métodos de factoría $lt(m), bt(\*,m_1,m_2)$. En este caso asumimos el operador $\*$, pero la otación es válida para otros operadores. 
- La función $\min_{a \in A(p)} g(p,a)$ o equivalentemente $\max_{a \in A(p)} g(p,a)$ filtran los valores $g(p,a)$ no nulos antes de tomar el mínimo o máximo y devuelven nulo si todos lo son.