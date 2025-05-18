# Notación

- Por $p$, representaremos un problema que en general vendrá definido por sus propiedades básicas y por $p_0$.
- Por $gr$ un grafo el problema inicial
- Por $gp$ un camino en un grafo
- Por $(i,j) \in gr$ que la arista $i, j$ pertenece al grafo
- Por $w(i,j)$ el peso de la arista correspondiente
- Por $(a,w)$ una solución parcial, $Sp$,  formada por una alternativa y un peso
- Usaremos la función $f(p)$ para calcular una solución parcial del problema $p$ de tipo $P$. La función $f$ devuelve una solución parcial y por eficiencia la guardamos en un $Map\<P,Sp\>$. Los valores de la función $f(p)$ tienen dos componentes que representamos por $f_a(p)$ y por $f_w(p)$. Entonces $f(p)=(f_a (p),f_w (p))$. El valor óptimo será $f_w (p_0)$.
- Usaremos la función $g(p,a)$ para calcular la socuicón parcial de $p$ si se toma la alternativa $a$
- Usaremos la función $s(p)$ para calcular la solución de $p$ de tipo $S$
- Usaremos ${}, ms + (ob,a)$ para representar el multiset vacío y añadir $a$ unidades de $ob$ al multiset $ms$. Si $ob$ o $a$ es nulo,o el resultado es nulo.
- Usaremos $[], a + ls$  para representar la lista vacía y añadir a la lista $ls$ por la izquierda  y  $ls+a$  para añadirla por la derecha. Si $a$ es nulo el resultado es nulo.
- Usaremos $[0,…,0]_m$ para representar una lista con m ceros y $ls+(i,ls[i]+d)$ para  sumar $d$ al valor de la casilla $i$ de $ls$.
- Para agregar los elementos en una lista en forma de árbol usaremos un BinaryTree<M> con métodos de factoría $lt(m), bt(*,m1,m2)$
- La función $\min_{a \in A(p)} g(p,a)$ o equivalentemente $\max_{a \in A(p)} g(p,a)$ filtran los valores $g(p,a)$ no nulos antes de tomar el mínimo o máximo y devuelven nulo si todos lo son.

# Enunciado

Dado un grafo gr y dos vértices $i_0,j_0$ encontrar el camino míninimo de uno a otro. El problema se generliza a $(i,j,k)$ que representa encontrar el camino mínimo de i a j usando como ciudades intermedias las del rango $[k,...,n]$. El problema inicial es $(i_0,j_0,0)$.


# Problema de Floyd 

$$
\begin{equation}
f\left(i,j,k\right) = \begin{cases}
(\bot, w\left(i,j\right)), & k = n, (i,j) \in g \\
\bot, & k = n,  (i,j) \notin g  \\
\min_{a \in A(i,j,k)} g\left(i,j,k,a\right), & k \lt n \\
\end{cases}
\end{equation}
$$

$$
\begin{equation}
g\left(i,j,k,a\right) = \begin{cases}
\bot , & f_{w}\left(i,j,k+1\right) = \bot \\
(F,f_{w}\left(i,j,k+1\right)), & a = F \\
\bot , & f_{w}\left(i,k,k+1\right) = \bot \vee f_{w}\left(k,j,k+1\right) = \bot \\
(T,f_{w}\left(i,k,k+1\right)+f_{w}\left(k,j,k+1\right)), & a = T \\
\end{cases}
\end{equation}
$$

$$
\begin{equation}
A\left(i,j,k\right) =\begin{cases}
[F], & i = k \vee j = k \\
[T,F], & \neg \left(i = k \vee j = k\right) \\
\end{cases}
\end{equation}
$$

$$
\begin{equation}
s\left(i,j,k\right) =\begin{cases}
(i,j), & f_{a}\left(i,j,k\right) = \bot \\
s(i,j,k+1), & f_{a}\left(i,j,k\right) = F \\
s\left(i,k,k+1\right)+s\left(k,j,k+1\right), & f_{a}\left(i,j,k\right) = T \\
\end{cases}
\end{equation}
$$
