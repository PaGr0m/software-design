package executor

// Структура для храния переменных шелла.
data class ShellContext(
    private val storage: MutableMap<String, String> = mutableMapOf()
) : MutableMap<String, String> by storage
