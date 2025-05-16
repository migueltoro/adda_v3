## Modelo

$$

f\left(i,j,k\right) =\begin{cases}
(\bot,w\left(i,j\right)), & k = n, (i,j) \in g \\
\bot, & k = n,  (i,j) \notin g \\
\min_{a\in A(i,j,k)\vert g\neq \bot }g\left(i,j,k,a\right), & k<n \\
\end{cases} $$



$$ g\left(i,j,k,a\right) =\begin{cases}
\bot , & f_{w}\left(i,j,k+1\right) = \bot \\
(N,f_{w}\left(i,j,k+1\right)), & a = F \\
\bot , & f_{w}\left(i,k,k+1\right) = \bot \vee f_{w}\left(k,j,k+1\right
(Y,f_{w}\left(i,k,k+1\right)+f_{w}\left(k,j,k+1\right)), & a = T \\
\end{cases} $$

$$ A\left(i,j,k\right) =\begin{cases}
[F], & i = k\vee j = k \\
[T,F], & ¬(i = k\vee j = k) \\
\end{cases} $$

$$ s\left(i,j,k\right) =\begin{cases}
(i,j), & f_{a}\left(i,j,k\right) = \bot \\
s(i,j,k+1), & f_{a}\left(i,j,k\right) = F \\
s\left(i,k,k+1\right)+s\left(k,j,k+1\right), & f_{a}\left(i,j,k\right) =
\end{cases} $$

$$
s\left(i,j,k\right) =
\begin{cases}(i,j),& f_{a}\left(i,j,k\right) = \bot \\ s(i,j,k+1), & f_{a}\left(i,j,k\right) = \
s\left(i,k,k+1\right)+s\left(k,j,k+1\right),&f_{a}\left(i,j,k\right) =
\end
{cases} $$

## Grafo

### Propiedades del vértice

 - $i$: Integer, básica
 - $pr$: Integer, peso restante, básica
