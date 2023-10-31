package dev.catsuperberg.e_commerce_exercise.client.presentation.ui.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.ArrayDeque

class PhoneVisualTransformation : VisualTransformation {
    private val offset = LimitedPhoneOffset()

    override fun filter(text: AnnotatedString): TransformedText {
        offset.setOriginalSize(text.length)
        return TransformedText(
            AnnotatedString(fill(text)),
            offset
        )
    }

    private fun fill(phone: AnnotatedString): String {
        if (isPartialPhone(phone.text).not())
            throw Exception("Phone number invalid: can't have non digit characters or length greater than $length")
        val phoneDeque = ArrayDeque(phone.text.toList())
        val string = buildString {
            append("$regionCode (${phoneDeque.pollOrFill(2, placeholderChar)}) ")
            append("${phoneDeque.pollOrFill(3, placeholderChar)}-")
            append("${phoneDeque.pollOrFill(2, placeholderChar)}-")
            append(phoneDeque.pollOrFill(2, placeholderChar))
        }
        return string
    }

    private fun ArrayDeque<Char>.pollOrFill(n: Int, fill: String) =
        buildString { repeat(n) { append(poll() ?: fill) } }

    companion object {
        const val length = 9
        const val regionCode = "+375"
        const val placeholderChar = "_"
        private val partialPhoneRegex = "^\\d{0,$length}\$".toRegex()
        private val fullPhoneRegex = "^\\d{$length}\$".toRegex()

        fun isPartialPhone(string: String) : Boolean = string.matches(partialPhoneRegex)
        fun isPhone(string: String) : Boolean = string.matches(fullPhoneRegex)
    }
}
