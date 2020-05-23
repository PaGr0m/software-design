package parse

sealed class ShellError(message: String) : Exception(message) {
    class Identifier: ShellError("Неправильный идентификатор")
    class NotFoundID(info: String): ShellError("Не найдено идентификатора $info")
    class NotFoundCommand(info: String): ShellError("Не найдено команды $info")
    class Parse: ShellError("Неправильный ввод")
}

