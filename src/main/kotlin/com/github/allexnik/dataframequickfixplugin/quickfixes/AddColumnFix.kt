package com.github.allexnik.dataframequickfixplugin.quickfixes

import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.modcommand.ModPsiUpdater
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.codeinsight.api.applicable.inspections.KotlinModCommandQuickFix
import org.jetbrains.kotlin.psi.KtQualifiedExpression

class AddColumnFix: KotlinModCommandQuickFix<KtQualifiedExpression>() {
    override fun getName(): String {
        TODO("Not yet implemented")
    }
    override fun getFamilyName(): @IntentionFamilyName String {
        TODO("Not yet implemented")
    }

    fun apply(qualifiedExpression: KtQualifiedExpression) {
        TODO("Not yet implemented")
    }

    override fun applyFix(
        project: Project,
        element: KtQualifiedExpression,
        updater: ModPsiUpdater
    ) {
        TODO("Not yet implemented")
    }
}