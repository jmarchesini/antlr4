#!/bin/bash

antlr4 Hello.g4
javac *.java

grun Hello r -tokens

# input  : hello world
# input  : CTRL-D (EOF)
# output :
# [@0,0:4='hello',<'hello'>,1:0]
# [@1,6:10='world',<ID>,1:6]
# [@2,12:11='<EOF>',<EOF>,2:0]

grun Hello r -tree

# input  : hello world
# input  : CTRL-D (EOF)
# output : (r hello world)

grun Hello r -gui

# input  : hello world
# input  : CTRL-D (EOF)
# output : AST dialog box
