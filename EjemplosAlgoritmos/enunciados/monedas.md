# Mochila

El problema de las Monedas parte de una lista L de tamaño $n$ de monedas disponibles. Cada moneda $M_i$ de la lista es de la forma $M_i= (w_i, v_i, m_i)$ dónde  $w_i, v_i, m_i$ son, respectivamente, su peso, su valor unitario y el número de unidades disponibles. Se trata de intercambiar un valor  $V$ por monedas cuyo peso total sea mínimo.

## Datos

- $w_i$: peso unitario de la moneda $i$
- $v_i$: valor unitario de la moneda $i$
- $m_i$: número de unidades disponibles de la moneda $i$
- $V$: valor a intercmbiar
- $n$: número de monedas

## Modelo

Si $x_i$ es el número de unidades escogidas de la moneda $i$ el problema puede modelarse como:

$$ \min \sum\limits_{i=0}^{n-1} x_i w_i $$

$$ \sum\limits_{i=0}^{n-1} x_i v_i = V $$

$$ x_i\le m_i,\ \ \ \ i\in\left[0,n-1\right] $$

$$ int\ \ \ x_i,\ \ \ \ \ i\in\left[0,n-1\right] $$

## Grafo

### Propiedades del vértice

 - $i$: Integer, básica
 - $vr$: Integer, valor restante, básica


## Algoritmo PDR SUM

$$
\begin{equation}
f\left(i,cr\right) = \begin{cases}
\bot, & i = n, v \gt 0 \\
(\bot, 0), & i = n, v = 0 \\
\min_{a \in A(i,vr)} g\left(i,vr,a\right), & i \lt n \\
\end{cases}
\end{equation}
$$

$$
\begin{equation}
g\left(i,vr,a\right) = (a,f_{w}\left(i+1, vr-a v_i\right)+a w_i)
\end{equation}
$$

$$
\begin{equation}
A\left(i,vr\right) = [min(m_i,vr/v_i ),…,0]
\end{equation}
$$

$$
\begin{equation}
s\left(i,cr\right) =\begin{cases}
{}, & i = n \\
s(i+1,vr-f_a(i,vr) v_i )+(M_i,f_a(i,vr)), & i \lt n \\
\end{cases}
\end{equation}
$$