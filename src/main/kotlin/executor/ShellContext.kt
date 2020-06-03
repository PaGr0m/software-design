package executor

import java.nio.file.Files
import java.nio.file.Paths

// Структура для хранения переменных шелла.
data class ShellContext(
    private val storage: MutableMap<String, String> = mutableMapOf(),
    var currentPath: String = System.getProperty("user.dir")
) : MutableMap<String, String> by storage {

    // Текущий путь
    fun getPath(arguments: String): String? {
        if (arguments.isEmpty()) {
            return currentPath
        }

        if (Files.isDirectory(Paths.get(currentPath, arguments))) {
            return Paths.get(currentPath, arguments).toFile().canonicalPath
        } else if (Files.isDirectory(Paths.get(arguments))) {
            return Paths.get(arguments).toFile().canonicalPath
        }

        return null
    }
}
