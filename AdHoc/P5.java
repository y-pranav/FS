/*
A group of researchers is analyzing satellite imagery of agricultural fields, 
represented by a grid of land sections. Each section is marked either as 
fertile (1) or infertile (0). To efficiently plan crop planting, the researchers 
need to identify the largest rectangular area consisting exclusively of fertile 
land sections.

Given a binary grid representing the land (1 for fertile and 0 for infertile), 
your task is to compute the area of the largest rectangle consisting entirely 
of fertile land sections.

Input Format:
-------------
Line-1: Two integers rows(r) and columns(c) of grid.
Next r lines: c space separated integers, each row of the grid.

Output Format:
--------------
Print an integer, area of the largest rectangle consisting entirely of fertile land sections.

Example:
--------
input=
4 5
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

1 0 1 0 0
2 0 2 1 1
3 1 3 2 2
4 0 0 3 0

output=
6 
*/

