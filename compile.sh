#!/bin/bash

cd ~/workspace/gvgai

rm -rf classes

ant compile

cd classes
ln -s ../examples examples
ln -s ../sprites sprites


