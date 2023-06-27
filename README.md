# Copy-File

Консольная утилита, позволяющая копировать все файлы из указанной директории в другую директорию с сохранением вложенной структуры поддиректорий. Пример использования программы приведен ниже.

- отображать прогресс копирования файлов в процентах от общего размера файлов.
- сводная информация о количестве успешно скопированных файлов и байт.
- о количестве файлов, которые не удалось скопировать.

## How to use?
    # Build the file.jar
    > mvn clean compile package
    > cd target
  
    # Execute the jar "source" "destination" poolSize
    Example:
    > java -jar hseCopyFile-0.0.1-SNAPSHOT.jar "C:\example_input" "C:\example_output" 10
    июн. 27, 2023 5:33:29 PM hse.copyfile.CopyFile main
    INFO: Progress: 37,43%
    июн. 27, 2023 5:33:31 PM hse.copyfile.CopyFile main
    INFO: Progress: 57,72%
    июн. 27, 2023 5:33:32 PM hse.copyfile.CopyFile main
    INFO: Progress: 59,45%
    Copied 4005 files (286740604 bytes)
    Failed to copy 0 files
