#!/bin/bash
cd tm/
rm -R *.class
cd ../
javac tm/TM.java
javac tm/TMSimulator.java
time java tm.TMSimulator file5.txt