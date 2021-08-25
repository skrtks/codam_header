package com.samkortekaas.codam.header

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.command.WriteCommandAction
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Generate : AnAction() {
    override fun actionPerformed(AnActionEvent: AnActionEvent) {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date()
        val file = AnActionEvent.getData(CommonDataKeys.VIRTUAL_FILE)!!
        var filename = file.name
        while (filename.length < 51) filename += ' '
        var user = "By: " + System.getenv("USER") + " " + "<" + System.getenv("USER") + "@student.codam.nl>"
        while (user.length < 47) user += ' '
        var user2 = "by " + System.getenv("USER")
        while (user2.length < 17) user2 += ' '
        var user3 = "by " + System.getenv("USER")
        while (user3.length < 17) user3 += ' '
        var startComment = "/*"
        var endComment = "*/"
        if (filename.contains("Makefile")) {
            startComment = "#"
            endComment = "#"
        }
        val header =
"""$startComment ${if (startComment.length == 1) "*" else ""}**************************************************************************${if (endComment.length == 1) "*" else ""} $endComment
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
        val runnable = Runnable {
            Objects.requireNonNull(AnActionEvent.getData(LangDataKeys.EDITOR))?.document?.insertString(
                0,
                header
            )
        }
        WriteCommandAction.runWriteCommandAction(getEventProject(AnActionEvent), runnable)
    }
}