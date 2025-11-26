# Project 3: Turing Machine

* Author: Connor Nielsen & Phuc Le
* Class: CS361 Section 1
* Semester: Fall 2025

## Overview
This program simulates a Turing machine specified in a user-provided file that is fed a bi-infinite tape with the following assumptions:
- It is deterministic
- Transitions for all tape symbols are provided
- The machine will eventually halt
## Reflection

Write a brief (2-3 paragraph) reflection describing your experience with this
project. Answer the following questions (but feel free to add other insights):
- What worked well and what was a struggle?
- What concepts still aren't quite clear?
- What techniques did you use to make your code easy to debug and modify?
- What would you change about your design process?
- If you could go back in time, what would you tell yourself about doing this project?
Connor:
This project didn't feel too difficult in my opinion. I think trying to optimize the performance took way too long, but I am happy with the results we got. Its interesting that on my personal machine file 5 ran in less than a second, but on onyx it took about 2 seconds. I think there are significantly more optimizations that we could implement, but after spending about 3 days updating files and testing different methods, spending more time on it couldn't be afforded. I can't think of anything that really doesn't make sense about Turing machines. I know there is significantly more stuff I don't understand/know about, but I don't know what I don't know. 

One of the biggest things we did to make this easier to debug and modify was to start with an incredibly object oriented approach before slowly removing unnecessary objects that only slowed our machine. This allowed us to really understand each piece and what it did so that when we reimplemented certain pieces, we already knew what to do and that the rest of the code would work. That way, if something broke, we knew exactly what caused it.

I can't really think of anything in our design process that I would change. I think this was one of the better projects I've worked, mainly because we chose to take things slow and build up everything piece by piece. If I could back in time, I would tell myself to stop trying to optimize the code during Thankgiving break. Our implementation was done Wednesday of the week before. All the additional optimizations that were tried turned out slower.

Phuc:
This project certainly was the hardest project for me to get an initial understanding of -- especially with the way the
input file format was designed. I kept initially getting lost in what the origin state was for each listed transition
while trying to construct a mental model of the provided Turing machine files. The in-class walkthrough combined with
Connor's attempts to explain it was what allowed me to finally understand why it was omitted from the transition list.
It still feels quite funky to me that the provided testing files all had no input strings, since I was taught in my
operating systems class that any program that doesn't take in any input isn't very useful at all. However, I know that
empty string is a valid input. 

Print statements were handy for helping us spot when exactly bugs occurred. I think the overall design process was fine
since we focused on the file parser first. The only thing I would tell myself if I could time travel back is to get more
sleep before our in-person programming sessions.

## File descriptions
### TM.java
This is our implementation of our Turing machine that uses the specified Turing machine file and utilizes a bi-infinite 
tape. 
### TMSimulator.java
This is the driver file of our Turing machine implementation that simply checks for the provided Turing Machine file and 
calls our TM's constructor which utilizes it.

## Compiling and Using
Assuming that you are in the project's root directory, do the following:
1. ``javac tm/*.java`` to compile both Java files
2. ``java tm.TMSimulator <filename.txt>``
where filename.txt is the desired Turing machine to simulate
## Sources used
The following stack overflow helped us convert our 2D array of states/transitions into a flattened 1D array
https://stackoverflow.com/questions/1730961/convert-a-2d-array-index-into-a-1d-index

One of the optimizations I was worried about was that using a Strings charAt() function would be O(n) which would significantly degrade performance. This source discussed the topic and pointed out the method was an array lookup which is constant time.
https://stackoverflow.com/questions/6461402/java-charat-and-deletecharat-performance

We switched to a BufferedReader to access and parse the text file. This link was the basis for our BufferedReader logic.
https://www.w3schools.com/java/java_bufferedreader.asp

One of the first bugs we ran into was an index out of bounds issue that was arising from our direct casting from a char into an int. This link pointed out that you need to offset the cast by a constant '0' character so that it would be the right value.
https://stackoverflow.com/questions/21196926/converting-an-int-to-a-char-in-c

This last link wasn't used in our final implementation, but it was instramental in understanding how the tape should function.

Our first tape implementation was a circular and doubly linked list that would allow us to expand the size of the tape in constant time. This was incredibly slow compared to just expanding an array, but it helped us understand how the tape should work and what was best for the tape logic.
https://www.geeksforgeeks.org/dsa/doubly-linked-list-using-sentinel-nodes