package com.github.allexnik.dataframequickfixplugin.inspections

import com.github.allexnik.dataframequickfixplugin.quickfixes.AddColumnFix
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.util.InspectionMessage
import org.jetbrains.kotlin.analysis.api.KaSession
import org.jetbrains.kotlin.idea.base.resources.KotlinBundle
import org.jetbrains.kotlin.idea.codeinsight.api.applicable.inspections.KotlinApplicableInspectionBase
import org.jetbrains.kotlin.idea.codeinsight.api.applicable.inspections.KotlinModCommandQuickFix
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtNameReferenceExpression
import org.jetbrains.kotlin.psi.KtQualifiedExpression
import org.jetbrains.kotlin.psi.KtStringTemplateExpression
import org.jetbrains.kotlin.psi.KtVisitor
import org.jetbrains.kotlin.psi.qualifiedExpressionVisitor


class AddColumnInspection : KotlinApplicableInspectionBase.Simple<KtQualifiedExpression, Unit>() {
    override fun getProblemDescription(element: KtQualifiedExpression, context: Unit): @InspectionMessage String {
        return KotlinBundle.message("column.name.may.be.moved.into.builder.dsl")
    }

    override fun createQuickFix(
        element: KtQualifiedExpression,
        context: Unit
    ): KotlinModCommandQuickFix<KtQualifiedExpression> = AddColumnFix()

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): KtVisitor<*, *> {
        return qualifiedExpressionVisitor { qualifiedExpression ->
            visitTargetElement(qualifiedExpression, holder, isOnTheFly)
        }
    }

    override fun isApplicableByPsi(element: KtQualifiedExpression): Boolean {
        val selector = element.selectorExpression as? KtCallExpression ?: return false
        val calleeExpr = selector.calleeExpression as? KtNameReferenceExpression ?: return false
        if (calleeExpr.getReferencedName() != "add") return false

        val arguments = selector.valueArguments
        if (arguments.size != 1) return false

        val firstArg = arguments.first().getArgumentExpression() ?: return false
        if (firstArg !is KtStringTemplateExpression) return false

        val lambda = selector.lambdaArguments.singleOrNull()?.getLambdaExpression() ?: return false
        if (lambda.bodyExpression == null) return false

//        return lambda.bodyExpression?.statements?.any { it.text.contains("from") } != true
        return true
    }

    override fun KaSession.prepareContext(element: KtQualifiedExpression): Unit? {
        return Unit
    }
}