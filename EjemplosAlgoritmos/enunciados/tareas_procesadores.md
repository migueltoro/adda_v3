# Tareas y procesadores

El problema se formula de la siguiente manera: Dado una lista de $n$ tareas con duraciones $d_i$ y un conjunto de $m$ procesadores buscar la asignación de tareas a procesadores tal que el tiempo total de ejecución sea mínimo.

## Datos

 - $n$: número de tareas
 - $m$: número de procesadores
 - $d_i$: duración la tarea $i$

## Primer modelo

Escogiendo las variables binarias $x_{ij}$ que toman valor 1 si la tarea $i$ es asignada al procesador $j$ el problema puede ser formulado como:

$$ min\ T $$

$$ \sum\limits_{i=0}^{n-1} d_i x_{ij} \le T,\ j \in [0,m-1] $$

$$ \sum\limits_{j=0}^{m-1} x_{ij} =1,\ i \in [0,n-1] $$

$$ bin\ x_{ij},\ i \in [0,n-1],j \in [0,m-1] $$

## Segundo modelo

La variable $x_i$ indica ahora el procesador al que se asigna la tarea $i$.

$$ \min \max\limits_{j : [0,n-1]} \sum\limits_{i=0|x_i=j}^{n-1} d_i $$

$$ 0 \le x_i <\lt m,\ \ i\in [0,n-1] $$

$$int\ x_i,\ i\in [0,n-1] $$

## Grafo

### Propiedades del vértice del grafo

- $i$: _Integer_, básica
- $c$: List\<Double\> de tamaño $m$, cargas de los procesadores, básica
- $cm = \max\limits_{j:[0,m-1]} cargas[j]$ : _Double_, derivada, carga del procesador más cargado
- $nMin = \underset{j:[0,m-1]}{argmin} \ cargas[j]$: _Integer_, derivada, procesador menos cargado

- Problema inicial $(0,[0,…,0]_m)$

## Algoritmo PDR Last

$$
\begin{equation}
f\left(i,c\right) = \begin{cases}
(\bot, cm(c)), & i = n \\
\min_{a \in A(i,c)} g\left(i,c,a\right), & i \lt n \\
\end{cases}
\end{equation}
$$

$$
\begin{equation}
g\left(i,ca,a\right) = (a,f_{w}\left(i+1, c+(a,c[a]+d_i)\right))
\end{equation}
$$

$$
\begin{equation}
A\left(i,c\right) = [0,…,m-1]
\end{equation}
$$

$$
\begin{equation}
s\left(i,cr\right) =\begin{cases}
[], & i = n \\
f_a(i,c)+s(i+1,c+(i,f_a(i,c))), & i \lt n \\
\end{cases}
\end{equation}
$$

