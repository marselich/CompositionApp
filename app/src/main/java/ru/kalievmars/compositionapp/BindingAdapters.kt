package ru.kalievmars.compositionapp

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("emojiResult")
fun bindEmojiResult(imageView: ImageView, isWinner: Boolean) {
    if(isWinner) {
        imageView.setImageResource(R.drawable.ic_smile)
    } else {
        imageView.setImageResource(R.drawable.ic_sad)
    }
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, result: Int) {
    setResultToTextView(textView, R.string.required_score, result)
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, result: Int) {
    setResultToTextView(textView, R.string.required_score, result)
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, result: Int) {
    setResultToTextView(textView, R.string.required_percentage, result)
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, result: Int) {
    setResultToTextView(textView, R.string.score_percentage, result)
}


private fun setResultToTextView(textView: TextView, resId: Int, result: Int) {
    textView.text = String.format(
        textView.context.resources.getString(resId),
        result.toString()
    )
}