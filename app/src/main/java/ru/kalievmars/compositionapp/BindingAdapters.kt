package ru.kalievmars.compositionapp

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

interface OnOptionClickListener {

    fun onOptionClick(option: Int)
}


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

@BindingAdapter("enoughCountOfRightAnswers")
fun bindEnoughCountOfRightAnswers(textView: TextView, isGreen: Boolean) {
    textView.setTextColor(getColorByState(isGreen, textView))
}

@BindingAdapter("enoughPercentOfRightAnswers")
fun bindEnoughPercentOfRightAnswers(progressBar: ProgressBar, isGreen: Boolean) {
    val color = getColorByState(isGreen, progressBar)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("intToString")
fun bindIntToString(textView: TextView, int: Int) {
    textView.text = int.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, listener: OnOptionClickListener) {
    textView.setOnClickListener {
        listener.onOptionClick(textView.text.toString().toInt())
    }
}



private fun setResultToTextView(textView: TextView, resId: Int, result: Int) {
    textView.text = String.format(
        textView.context.resources.getString(resId),
        result.toString()
    )
}

private fun getColorByState(goodState: Boolean, view: View): Int {
    val colorResId = if(goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return view.context.getColor(colorResId)
}