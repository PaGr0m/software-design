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

```shell script
> ./gradlew run -q --console=plain
```

Запустить тесты можно из Intellij IDEA или из консоли:

```shell script
> ./gradlew test
```

Парсеры ANTLR должны генериться при запуске, но если ее нет, то

```shell script
> ./gradlew :generateGrammarSource
```

### Пример работы 

```shell script
> ./gradlew run -q --console=plain
$ ls
README.md
build
build.gradle.kts
gradle
gradle.properties
gradlew
gradlew.bat
settings.gradle.kts
src
text.txt

$ echo "$wow"
Возникла ошибка: Не найдено идентификатора wow
$ echo hello | wc
1 1 5
$ cat text.txt
this
is example
of
text

$ cat text.txt | grep -i -A 1 t
this
is example
text

$ cat text.txt | grep -i T
this
text

$ pwd
/Users/artembobrov/Documents/JB-ITMO-2/sd-itmo
$ echo 'wo  ho'
wo  ho
$ hello=world

$ echo "'hello    $hello'"
'hello    world'
$ exit
```
