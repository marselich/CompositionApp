package ru.kalievmars.domain.usecases

import ru.kalievmars.domain.entities.GameSettings
import ru.kalievmars.domain.entities.Level
import ru.kalievmars.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level) : GameSettings {
        return repository.getGameSettings(level)
    }

}