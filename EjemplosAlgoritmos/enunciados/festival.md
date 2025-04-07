Un organizador de festivales debe decidir cuántas entradas de diferentes tipos (general, VIP, premium) asignar a distintas áreas del recinto del festival. Cada área tiene un aforo máximo que no puede excederse, y se requiere garantizar una cantidad mínima de entradas de cada tipo que deben asignarse. Además, cada tipo de entrada tiene un costo de implementación distinto para cada área (por ejemplo, infraestructura adicional para entradas VIP). El objetivo es determinar cuántas entradas de cada tipo asignar a cada área para minimizar los costos de implementación, asegurando que se cumplan los límites de aforo y las cuotas mínimas de cantidad de entradas por tipo.

Datos de entrada:

- <i>n</i>: tipos de entradas (general, VIP, premium, etc.)
- <i>m</i>: número de áreas en el recinto
- <i>$A_j$</i>: aforo máximo permitido en el área <i>j</i>, <i>j</i> en [0,<i>m</i>).
- <i>$Q_j$</i>​: cantidad mínima de entradas que deben asignarse para el tipo <i>i</i>, <i>i</i> en [0,<i>n</i>).
- <i>$C_{ij}$</i>​: coste de asignar una entrada de tipo <i>i</i> al área <i>j</i>, <i>i</i> en [0,<i>n</i>), <i>j</i> en [0,<i>m</i>).

Variables de decisión:

- <i>$x_{ij}</i>​: cantidad de entradas del tipo <i>i</i> asignadas al área <i>j$</i>. 

Restricciones:

- Las entradas asignadas a cada área no deben superar su aforo: $\sum_i x_{ij} \leq A_j$ para cada <i>j</i>.
- La cantidad total de entradas asignadas de cada tipo debe cumplir su cuota mínima:
$\sum_j x_{ij}  \geq Q_i$ para cada <i>i</i>.


Función objetivo:

$\min \sum_i \sum_j C_{ij} x_{ij}$

Modelo AG: cromosoma Range linealizado

$\min \sum_i \sum_j C_{ij} x_{ij}$  
$\sum_i x_{ij} \leq A_j, j \in [0,m)$  
$\sum_j x_{ij}  \geq Q_i, i \in [0,n)$  
$int \ x_{ij},  i \in [0,n),\ j \in [0,m)$