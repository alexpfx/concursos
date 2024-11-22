#!/bin/bash

# gradle test --tests "*TextNormalizationTest.doProcess_original" > original.txt && \
# gradle test --tests "*TextNormalizationTest.doProcess_after" > cleaned.txt 
# 
# idea diff original.txt cleaned.txt 

gradle test --tests "*TextHmlationTest.doProcess_original" > original.txt && \
gradle test --tests "*TextHmlationTest.doProcess_after" > htmlzation.txt 

idea diff original.txt htmlzation.txt 

# gradle test --tests "*CutBodyTest.doProcess_original" > original_cleaned.txt && \
# gradle test --tests "*CutBodyTest.doProcess_after" > body.txt 2> err.txt

# idea diff original_cleaned.txt body.txt
