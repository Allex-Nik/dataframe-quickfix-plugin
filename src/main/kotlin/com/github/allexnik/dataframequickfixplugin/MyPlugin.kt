package com.github.allexnik.dataframequickfixplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class HelloAction: AnAction("Say Hello") {
    override fun actionPerformed(e: AnActionEvent) {
        Messages.showMessageDialog("Hello from the new plugin", "Greeting", Messages.getInformationIcon())
    }
}