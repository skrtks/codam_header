package com.samkortekaas.codam.header

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.NlsSafe
import java.lang.StringBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Generate : AnAction() {
    override fun actionPerformed(AnActionEvent: AnActionEvent) {
        val file = AnActionEvent.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        var filename = file.name
        val filenameMaxLength = 51
        filename = formatUserString(filename, filenameMaxLength)

        var user = "By: " + System.getenv("USER") + " " + "<" + System.getenv("USER") + "@student.codam.nl>"
        val userMaxLength = 47
        user = formatUserString(user, userMaxLength)

        var user2 = "by " + System.getenv("USER")
        val user2MaxLength = 17
        user2 = formatUserString(user2, user2MaxLength)

        var user3 = "by " + System.getenv("USER")
        val user3MaxLength = 17
        user3 = formatUserString(user3, user3MaxLength)

        val header = createHeader(filename, user, user2, user3)

        val runnable = Runnable {
            AnActionEvent.getData(LangDataKeys.EDITOR)?.document?.insertString(0, header)
        }
        WriteCommandAction.runWriteCommandAction(getEventProject(AnActionEvent), runnable)
    }

    private fun createHeader(filename: String, user: String, user2: String, user3: String): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date()
        val (startComment, endComment) = if (filename.contains("Makefile")) {
            "#" to "#"
        } else {
            "/*" to "*/"
        }
        return """$startComment ${if (startComment.length == 1) "*" else ""}**************************************************************************${if (endComment.length == 1) "*" else ""} $endComment
$startComment${if (startComment.length == 1) " " else ""}                                                                            ${if (endComment.length == 1) " " else ""}$endComment
$startComment${if (startComment.length == 1) " " else ""}                                                        ::::::::            ${if (endComment.length == 1) " " else ""}$endComment
$startComment${if (startComment.length == 1) " " else ""}   $filename:+:    :+:            ${if (endComment.length == 1) " " else ""}$endComment
$startComment${if (startComment.length == 1) " " else ""}                                                     +:+                    ${if (endComment.length == 1) " " else ""}$endComment
$startComment${if (startComment.length == 1) " " else ""}   $user  +#+                     ${if (endComment.length == 1) " " else ""}$endComment
$startComment${if (startComment.length == 1) " " else ""}                                                   +#+                      ${if (endComment.length == 1) ' ' else ""}$endComment
$startComment${if (startComment.length == 1) " " else ""}   Created: ${dateFormat.format(date)} $user2#+#    #+#                 ${if (endComment.length == 1) " " else ""}$endComment
$startComment${if (startComment.length == 1) " " else ""}   Updated: ${dateFormat.format(date)} $user3########   odam.nl         ${if (endComment.length == 1) " " else ""}$endComment
$startComment${if (startComment.length == 1) ' ' else ""}                                                                            ${if (endComment.length == 1) " " else ""}$endComment
$startComment ${if (startComment.length == 1) "*" else ""}**************************************************************************${if (endComment.length == 1) "*" else ""} $endComment

"""
    }

    private fun formatUserString(originalUser: String, userMaxLength: Int): String {
        val user = StringBuilder()
        user.append(originalUser)
        if (user.length > userMaxLength) {
            user.setLength(userMaxLength - 3)
            user.append("...")
        }
        while (user.length < userMaxLength) user.append(' ')
        return user.toString()
    }
}