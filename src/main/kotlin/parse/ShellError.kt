package parse

// Базовый класс ошибки шелла
sealed class ShellError(message: String) : Exception(message) {
    // Ошибка: При поиске не был найден идентификатор
    class Identifier: ShellError("Неправильный идентификатор")
    // Ошибка: При подстановке не был найден идентификатор c найденным именем
    class NotFoundID(info: String): ShellError("Не найдено идентификатора $info")
    // Ошибка: Не возможно запустить команду с таким именем
    class NotFoundCommand(info: String): ShellError("Не найдено команды $info")
    // Ошибка: Не удалось распарсить строку
    class Parse: ShellError("Неправильный ввод")
}

