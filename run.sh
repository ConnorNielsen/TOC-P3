#!/bin/bash
cd tm/
rm -R *.class
cd ../
javac tm/TMState.java
javac tm/TM.java
javac tm/TMSimulator.java
time java tm.TMSimulator file0.txt