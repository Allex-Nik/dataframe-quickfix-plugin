package com.github.allexnik.dataframequickfixplugin.quickfixes

import com.github.allexnik.dataframequickfixplugin.MyBundle
import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.modcommand.ModPsiUpdater
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.base.psi.replaced
import org.jetbrains.kotlin.idea.base.resources.KotlinBundle
import org.jetbrains.kotlin.idea.codeinsight.api.applicable.inspections.KotlinModCommandQuickFix
import org.jetbrains.kotlin.idea.codeinsight.utils.commitAndUnblockDocument
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtNameReferenceExpression
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.KtQualifiedExpression
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression

class AddColumnFix: KotlinModCommandQuickFix<KtQualifiedExpression>() {
    override fun getName(): String = MyBundle.message("move.column.name.and.lambda.into.builder.dsl")

    override fun getFamilyName(): @IntentionFamilyName String = name

    fun apply(qualifiedExpression: KtQualifiedExpression) {
        val psiFactory = KtPsiFactory(qualifiedExpression.project)

        // Get receiver (for example, df)
        val receiver = qualifiedExpression.receiverExpression

        val receiverText = receiver.text

        // qualifiedExpression is either KtSafeQualifiedExpression or KtDotQualifiedExpression
        val operationSign = when (qualifiedExpression) {
            is KtSafeQualifiedExpression -> "?."
            else -> "."
        }

        // Get a selector expression (for example, add("columnName") { 42 })
        val selector = qualifiedExpression.selectorExpression as? KtCallExpression ?: return

        // Get a callee expression (for example, add)
        val calleeExpr = selector.calleeExpression as? KtNameReferenceExpression ?: return
        val calleeText = calleeExpr.getReferencedName()

        // Get an argument expression (for example, "columnName")
        val argument = selector.valueArguments.singleOrNull()?.getArgumentExpression() ?: return
        val argumentText = argument.text

        // Get a lambda expression (for example, { 42 })
        val lambda = selector.lambdaArguments.singleOrNull()?.getLambdaExpression() ?: return
        val lambdaText = lambda.text

        // Create a new qualified expression
        val newQualifiedExpression = psiFactory.createExpression(
            """
                $receiverText$operationSign$calleeText { 
                    $argumentText from $lambdaText 
                }
                """.trimIndent()
        )

        val result = qualifiedExpression.replaced(newQualifiedExpression)

        result.containingKtFile.commitAndUnblockDocument()
    }

    override fun applyFix(
        project: Project,
        element: KtQualifiedExpression,
        updater: ModPsiUpdater
    ) {
        apply(element)
    }
}