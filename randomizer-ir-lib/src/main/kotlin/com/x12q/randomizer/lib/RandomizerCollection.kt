package com.x12q.randomizer.lib

import kotlin.reflect.KClass

/**
 * A collection of randomizers
 */
interface RandomizerCollection {
    val randomizersMap:Map<KClass<*>, ClassRandomizer<*>>
    fun getRandomizerForClass(clazz:KClass<*>):ClassRandomizer<*>?
}
