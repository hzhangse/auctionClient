#!/bin/bash
if [ `getconf LONG_BIT` == 32 ]; then
	JARFILE=./signer32.jar
	./tools/java/bin/java32 -jar $JARFILE
else
	JARFILE=./signer64.jar
	./tools/java/bin/java64 -jar $JARFILE
fi
