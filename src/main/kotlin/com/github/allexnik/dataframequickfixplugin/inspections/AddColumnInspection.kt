package com.github.allexnik.dataframequickfixplugin.inspections

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.util.InspectionMessage
import org.jetbrains.kotlin.analysis.api.KaSession
import org.jetbrains.kotlin.idea.codeinsight.api.applicable.inspections.KotlinApplicableInspectionBase
import org.jetbrains.kotlin.idea.codeinsight.api.applicable.inspections.KotlinModCommandQuickFix
import org.jetbrains.kotlin.psi.KtQualifiedExpression
import org.jetbrains.kotlin.psi.KtVisitor


class AddColumnInspection : KotlinApplicableInspectionBase.Simple<KtQualifiedExpression, Unit>() {
    override fun getProblemDescription(element: KtQualifiedExpression, context: Unit): @InspectionMessage String {
        TODO()
    }

    override fun createQuickFix(element: KtQualifiedExpression, context: Unit): KotlinModCommandQuickFix<KtQualifiedExpression> {
        TODO("Not yet implemented")
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): KtVisitor<*, *> {
        TODO()
    }

    override fun isApplicableByPsi(element: KtQualifiedExpression): Boolean {
        TODO()
    }

    override fun KaSession.prepareContext(element: KtQualifiedExpression): Unit? {
        return Unit
    }
}