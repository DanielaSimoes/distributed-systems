#!/usr/bin/env bash
set -x
echo "	@ Compiling all source code "
find ../src -name "*.java" > sources.txt
javac @sources.txt -d .
rm sources.txt

rm -rf java
mkdir -p java

mv bench java/
mv entities java/
mv general_info_repo java/
mv interfaces java/
mv playground java/
mv referee_site java/
mv registry java/
mv structures java/

zip -r java.zip java