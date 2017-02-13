Programmer Name: Ajana C Sathian

ASTParser
*********
Example.java is the ASTParser class
Functions in it : 
     - To compute the Halsted Complexity
     - Parse the given Java class
     - Connt the number of operands and operators.

How to Parse an Java Sourse program using my ASTparser class:
****************************************************************

In the Example.java class (which is in the JavaSampleSimpleExample/src/main/java),there is a function named ParseFilesInDir(). You can change the String dirPath1 value to the desired java sourse program , inorder to work this ASTParser in it.

How to Run the ASTParser
************************
Running Example.java will give you result

Operators 
*************
This program only count operators as 38 operators according the JLS specification( https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.12)
The operators are:
+,-,*,/,%,++,--,==,!=,>,<,>=,<=,&,|,^,~,<<,>>,>>>,&&,||,!,=,+=,-=,*=,/=,%=,<<=,>>=,&=,^=,|=,?: