# Enunciado

Dada una lista de $n$ matrices $m_i$ de filas y columnas respectivas $r_i,c_i$ que queremos multiplicar encontrar un árbol óptimo que indique la forma de multiplicarlas para conseguir el número mínimo de multiplicaciones. El producto de las matrices consecutivas $m_i,m_{i+1} implica $r_i c_i c_{i+1}$ multiplicaciones. El problema se generaliza a $i,j$ y el problema inicial es $(0,n)$.

El árbol tendrá como métodos de factoría $lt(m), bt(*,m_1,m_2)$


# Problema de las matrices 

$$
\begin{equation}
f\left(i,j\right) = \begin{cases}
(\bot, 0), & j-i = 1 \\
(\bot, r_i c_i c_{i+1}), & j-i = 2  \\
\min_{a \in A(i,j)} g\left(i,j,a\right), & j-i \gt 2 \\
\end{cases}
\end{equation}
$$

$$
\begin{equation}
g\left(i,j,a\right) = (a,f_{w}\left(i,a\right)+f_{w}\left(a,j\right)+r_i c_(a-1) c_(j-1))
\end{equation}
$$

$$
\begin{equation}
A\left(i,j\right) = [i+1,...,j-1]
\end{equation}
$$

$$
\begin{equation}
s\left(i,j\right) =\begin{cases}
lt(m_i), & j-i = 1 \\
bt(\*,m_i,m_{i+1}, & j-i = 2 \\
bt(\*,s(i,f_a(i,j),s(f_a(i,j),j)), & j-i \gt 2 \\
\end{cases}
\end{equation}
$$
