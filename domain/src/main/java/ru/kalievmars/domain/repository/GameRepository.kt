package ru.kalievmars.domain.repository

import ru.kalievmars.domain.entities.GameSettings
import ru.kalievmars.domain.entities.Level
import ru.kalievmars.domain.entities.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings

}