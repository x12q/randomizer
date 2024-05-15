package com.x12q.randomizer

import com.x12q.randomizer.randomizer.RandomizerCollection
import com.x12q.randomizer.randomizer.config.RandomizerConfig
import com.x12q.randomizer.randomizer_checker.RandomizerChecker
import javax.inject.Inject
import kotlin.random.Random

data class RandomContext @Inject constructor(
    val random: Random,
    val lv1RandomizerCollection: RandomizerCollection,
    val randomizerChecker: RandomizerChecker,
    val defaultRandomConfig: RandomizerConfig,
)
