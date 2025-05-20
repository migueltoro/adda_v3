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
(F,f_{w}\left(i,j,k+1\right)), & a = F \\
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
