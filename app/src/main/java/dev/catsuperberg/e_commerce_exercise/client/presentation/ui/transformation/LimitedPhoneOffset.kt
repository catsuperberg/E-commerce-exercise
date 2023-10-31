package dev.catsuperberg.e_commerce_exercise.client.presentation.ui.transformation

import androidx.compose.ui.text.input.OffsetMapping

class LimitedPhoneOffset() : OffsetMapping {
    private val rawCarrierEnd = 2
    private val rawFirstEnd = 5
    private val rawSecondEnd = 7

    private val transCarrierStart = 6
    private val transCarrierEnd = 8
    private val transFirstStart = 8
    private val transFirstEnd = 13
    private val transSecondStart = 9
    private val transSecondEnd = 16
    private val transThirdStart = 10

    private var originalSize = 0

    fun setOriginalSize(value: Int) { originalSize = value}

    override fun originalToTransformed(offset: Int): Int {
        return when {
            offset < rawCarrierEnd -> offset + transCarrierStart
            offset < rawFirstEnd -> offset + transFirstStart
            offset < rawSecondEnd -> offset + transSecondStart
            else -> offset + transThirdStart
        }
    }

    override fun transformedToOriginal(offset: Int): Int {
        val mapped =  when {
            offset < transCarrierStart -> 0
            offset <= transCarrierEnd -> (offset - transCarrierStart).coerceAtLeast(0)
            offset <= transFirstEnd -> offset - transFirstStart
            offset <= transSecondEnd -> offset - transSecondStart
            else -> offset - transThirdStart
        }
        return  mapped.coerceAtMost(originalSize)
    }
}
