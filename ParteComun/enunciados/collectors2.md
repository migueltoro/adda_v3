# Collectors2

Es una factoría de Collectors adicional a la ya existente en Java. Se usa en la forma: stream.collect(Collector2.metodo()).

- toMultiset(): el resultado es un Multiset
- toIntegerSet(): el resultado es un IntegerSet
- groupingList(Function\<E,K\> key, Function\<E,R\> value): el resultado es de tipo Map\<K,List\<R\>\>. Forma grupos a partir de la clave proporcionada por key y luego transforma los elementos de cada grupo con la función value.
- groupingList(Function\<E,K\> key, Function\<E,R\> value, Function\<List\<R\>,S\> rs): Forma grupos a partir de la clave proporcionada por _key_ y luego transforma los elementos de cada grupo con la función _value_. Finalmente aplica a cada grupo la función _rs_.
- groupingSet(Function\<E,K\> key, Function\<E,R\> value): el resultado es de tipo Map\<K,Set\<R\>\>. Forma grupos a partir de la clave proporcionada por key y luego transforma los elementos de cada grupo con la función _value_.
- groupingSet(Function\<E,K\> key, Function\<E,R\> value, Function\<List\<R\>,S\> rs): Forma grupos a partir de la clave proporcionada por _key_ y luego transforma los elementos de cada grupo con la función _value_. Finalmente aplica a cada grupo la función _rs_.
- groupingReduce(Function\<E,K\> key, Function\<E,R\> value, BinaryOperator\<R\> rs): Forma grupos a partir de la clave proporcionada por _key_ y luego transforma los elementos de cada grupo con la función _value_. Finalmente aplica a cada grupo la función una reducción con el operador binario _op_.