# Mochila

El problema de la Mochila parte de una lista de objetos L de tamaño $n$. Cada objeto $ob_i$ de la lista es de la forma $ob_i= (w_i, v_i, m_i)$ dónde  $w_i, v_i, m_i$ son, respectivamente, su peso, su valor unitario y el número de unidades disponibles. La mochila tiene una capacidad $C$.  El problema busca ubicar en la mochila el máximo número unidades de cada objeto, teniendo en cuenta las disponibles, que quepan en la mochila para que el valor de estos sea máximo. 

## Datos

- $w_i$: peso unitario del objeto $i$
- $v_i$: valor unitario del objeto $i$
- $m_i$: número de unidades disponibles del objeto $i$
- $C$: capacidad de la mochila
- $n$: número de objetos

## Modelo

Si $x_i$ es el número de unidades del objeto $i$ en la mochila el problema puede modelarse como:

$$ \max \sum\limits_{i=0}^{n-1} x_i v_i $$

$$ \sum\limits_{i=0}^{n-1} x_i w_i \le C $$

$$ x_i\le m_i,\ \ \ \ i\in\left[0,n-1\right] $$

$$ int\ \ \ x_i,\ \ \ \ \ i\in\left[0,n-1\right] $$

## Grafo

### Propiedades del vértice

 - $i$: Integer, básica
 - $cr$: Integer, peso restante, básica
 
## Algoritmo PDR Sum

$$
\begin{equation}
f\left(i,cr\right) = \begin{cases}
(\bot, 0), & i = n \\
\min_{a \in A(i,cr)} g\left(i,cr,a\right), & i \lt n \\
\end{cases}
\end{equation}
$$

$$
\begin{equation}
g\left(i,cr,a\right) = (a,f_{w}\left(i+1, cr-a w_i\right)+a v_i)
\end{equation}
$$

$$
\begin{equation}
A\left(i,cr\right) = [min(m_i,cr/w_i),…,0]
\end{equation}
$$

$$
\begin{equation}
s\left(i,cr\right) =\begin{cases}
{}, & i = n \\
s(i+1,cr-f_a(i,cr) w_i )+(o_i,f_a (i,cr)), & i \lt n \\
\end{cases}
\end{equation}
$$


