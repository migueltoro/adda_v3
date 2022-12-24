grammar BinaryTree;

binary_tree : '_' #emptyTree 
			| label #labelTree 
			| treeLabel=label '(' left=binary_tree ',' right=binary_tree ')' #binaryTree ; 
			
label : ('+'|'-')? INT #intLabel 
	  | ('+'|'-')? DOUBLE #doubleLabel 
	  | ID #idLabel 
	  ;
	  
ID : ('a'..'z' | 'A'..'Z' | '_' | '[' | ']' | ';' | '.') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9' | '[' | ']' | ';' | '.')* ; 

INT : ('0'..'9')+ ; 

DOUBLE : INT '.' INT? ; 

WS : [ \t\r\n]+ -> skip ; 