package net.ucoz.testcompose

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class MaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return maskFilter1000(text)
    }

    private fun maskFilter1000(text: AnnotatedString): TransformedText {

        val input = text.toString()

        val out = when {

            //1,000-100,000
            text.length in 4..6 -> input.substring(0, text.length - 3) + "," +
                    input.substring(text.length - 3, text.length)

            //2,000,000-200,000,000
            text.length > 6 -> input.substring(0, text.length - 6) + "," +
                    input.substring(text.length - 6, text.length - 3) + "," +
                    input.substring(text.length - 3, text.length)

            else -> input
        }


        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset in 4..6 -> offset + 1
                    offset > 6 -> offset + 2
                    else -> offset
                }
            }

            override fun transformedToOriginal(offset: Int) = originalToTransformed(offset)
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}