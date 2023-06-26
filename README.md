# copy-File

Консольная утилита, выполняющая функцию копии всех файлов из источника в назначение с сохранением структуры поддиректорий. Ниже приведен пример использования утилиты.

## How to use?
    # Build the file.jar
    > mvn clean compile package
    > cd target
  
    # Execute the jar:
    > java -jar hse-0.0.1-SNAPSHOT.jar
  
    # source destination poolSize 
    Example:
  
    > C:\example_input C:\example_output 10
    Progress: 17,53%
    Copied 5 files (17 bytes)
    Failed to copy 0 files

## Command line application in a Docker container
    > docker container run -it pengs01/docker-copy-File:firsttry
