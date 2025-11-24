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
