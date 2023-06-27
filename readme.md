# Copy-File

Консольная утилита, позволяющая копировать все файлы из указанной директории в другую директорию с сохранением вложенной структуры поддиректорий. Пример использования программы приведен ниже.

## How to use?
    # Build the file.jar
    > mvn clean compile package
    > cd target
  
    # Execute the jar source destination poolSize
    Example:
    > > java -jar hseCopyFile-0.0.1-SNAPSHOT.jar C:\example_input C:\example_output 10
    июн. 27, 2023 5:14:37 PM hse.copyfile.CopyFile main
    INFO: Progress: 25,20%
    июн. 27, 2023 5:14:38 PM hse.copyfile.CopyFile main
    INFO: Progress: 46,29%
    июн. 27, 2023 5:14:39 PM hse.copyfile.CopyFile main
    INFO: Progress: 57,17%
    июн. 27, 2023 5:14:40 PM hse.copyfile.CopyFile main
    INFO: Progress: 62,81%
    Copied 4005 files (302949488 bytes)
    Failed to copy 0 files


## Command line application in a Docker container
    > docker container run -it pengs01/docker-copy-file:firsttry
