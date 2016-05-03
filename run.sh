#!/bin/sh

ant compile && ant jar && java -jar jar/Launcher.jar translit.txt names.stats test.txt