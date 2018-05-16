#!/usr/bin/env bash
set -x
echo "	@ Compiling all source code "
find ../src -name "*.java" > sources.txt
javac @sources.txt -d .
rm sources.txt

rm -rf java
mkdir -p java

mv entities java/
mv generalRepository java/
mv shared java/
mv interfaces java/
mv registry java/
mv structures java/

zip -r java.zip java