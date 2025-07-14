/*
A secret agent encodes a message by recursively scrambling it using a random encryption 
protocol.  The encryption process follows these rules:
 - If the message is a single character, leave it unchanged.
 - If the message has more than one character:
		- Split the message into two non-empty parts at any random position.
		- With a 50% chance, swap the two parts; otherwise, keep them in the same order.
		- Repeat this scrambling recursively on each part.

This encryption method produces a scrambled version of the original message.

You are now given two messages:
original: the message before scrambling.
scrambled: a possibly scrambled version of the original message.

Write a program to determine whether the scrambled message could have been produced 
by scrambling the original message using the above protocol.

Sample Input:
-------------
Two strings, original and scrambled, each of equal length.

Sample Output:
---------------
Return true if the scrambled string could be a scrambled version of the original using 
the given encryption protocol. Otherwise, return false.


Sample Input:
-------------
secure cesure

Sample Output:
---------------
true

Explanation: 
------------
One possible scrambling path leads from "secure" to "cesure".

Sample Input:
-------------
planet npealt

Sample Output:
---------------
false

Explanation: 
------------
No sequence of valid splits and swaps can lead to "petlan" from "npealt".
 
*/