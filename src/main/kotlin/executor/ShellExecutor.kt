package executor

import parse.ProgramAST

// Интерфейс для запуска строки шелла
interface ShellExecutor {
    // Исполняет программу, представленную в виде AST
    fun execute(program: ProgramAST)
}
