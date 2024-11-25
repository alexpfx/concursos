#!/bin/bash

export JAR_FILE=build/libs/kt-extrator_questoes-1.0-SNAPSHOT.jar


# for arg in "$@"; do
  # echo "Processando: $arg"
  # # Seu código aqui
# done

java -Djava.util.logging.config.file=logging.properties -jar "$JAR_FILE" "$@"
# java -jar "$JAR_FILE" "$@"

