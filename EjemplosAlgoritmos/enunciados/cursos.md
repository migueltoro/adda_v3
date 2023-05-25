# Cursos

Existe una oferta de cursos de verano, en cada uno de los cuales se tratan diversas temáticas, 
algunas de ellas comunes a varios cursos. Además, de cada curso se conoce su precio de inscripción, 
y el centro donde se imparte.  

Se desea conocer la lista de cursos en los que matricularse, teniendo en cuenta que: 

 - Entre todos los cursos seleccionados se deben cubrir todas las temáticas
 - No se pueden elegir cursos de más de un número determinado de centros diferentes (maxCentros)
 - Se debe minimizar el precio total de inscripción.

Datos

 - $n$: entero, número de cursos.
 - $m$: entero, número de temáticas
 - $r$: entero, número de centros_
 - $mc$: entero, número máximo de centros diferentes
 - $t_{ij}$: boolean, en el curso i se trata la temática j, i $\in [0,n)$, $j \in [0,m)$
 - $p_i$: real, precio de inscripción del curso $i$, $i \in [0,n)$
 - $c_{ik}$: boolean, el curso $i$ se imparte en el centro $k$, $i \in [0,n)$, $k \in [0,r)$

Variables $x_i$ que representan si el curso $i$ se ha elegido e $y_k$ si se ha elegido el centro $k$.

$$
\begin{matrix}
  \min \sum_{i=0}^{n-1} p_i x_i &  \\
  \sum_{i=0|t_{ij}}^{n-1} x_i \geq 1 & j \in [0,m) \\
  \sum_{k=0}^{r-1} y_k \le mc & \\
  x_i \le y_k & i \in [0,n), k \in [0,r) | c_{ik}
  bin\ x_i,y_k & i\in[0,n),k\in[0,r)
 \end{matrix}
$$


 - $n$: entero, número de cursos.
 - $m$: entero, número de temáticas
 - $r$: entero, número de centros_
 - $mc$: entero, número máximo de centros diferentes
 - $t_i$: Set<Integer>, conjunto de tematicas del curso $i$, i $\in [0,n)$
 - $p_i$: real, precio de inscripción del curso $i$, $i \in [0,n)$
 - $c_i$: entero, el centro donde se imparte el curso $i$, $i \in [0,n)$.

Variables $x_i$ que representan si el curso $i$ se ha elegido e $y_k$ si se ha elegido el centro $k$.