package ru.kalievmars.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
) : Parcelable {

    val percentOfRightAnswers: Int
            get() {
                if(countOfQuestions == 0) {
                    return 0
                }
                return (countOfRightAnswers / countOfQuestions.toDouble() * 100).toInt()
            }



}
