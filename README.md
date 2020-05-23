# Shmell

## Архитектура

REPL запускается из класса `Repl`.

Каждая строка парсится сгенерированным ANTLR парсером на основе [грамматики](src/main/antlr/Shell.g4).
Затем `ShellVisitorImpl` переводит дерево из представления `ANTLR` в `ProgramAST`.

`ShellExecutorImpl` создает необходимую команду с помощью абтрактной фабрики `CommandFactory` и запускает полученную команду на входных и выходных потоках приложения.

Каждая команда выполняет свою работу, сама выводя нужную информацию в выходной поток.

## Команды

|         | Количество аргуметов |
|---------|:------:|
| `pwd`  |   0  |
| `exit`  |   0  |
| `cat`  |   0..1 |
| `wc`  |   0..1  |
| `echo`  |   0..*  |
| `external`(внешняя команда)  |   1..*  |

Можно запустить `grep --help` и увидеть как использовать `grep`

```bash
$ grep --help
Usage: grep [OPTIONS] PATTERN [FILEPATH]

Options:
  -i          Выполняет поиск без учета регистра.
  -w          Выполняет поиск только слов целиком.
  -A INT      Распечатывает NUMBER строк после строки с совпадением
  -h, --help  Show this message and exit

Arguments:
  PATTERN   Регулярное выражение
  FILEPATH  Путь до файла
```

## Запуск

Запустить можно из Intellij IDEA или из консоли:

```bash
> ./gradlew run -q --console=plain
```

Запустить тесты можно из Intellij IDEA или из консоли:

```bash
> ./gradlew test
```

Парсеры ANTLR должны генериться при запуске, но если ее нет, то

```bash
> ./gradlew :generateGrammarSource
```
