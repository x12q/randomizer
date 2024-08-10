package com.x12q.randomizer.lib

import kotlin.reflect.KClass


class RandomizerCollectionImp(
    override val randomizersMap: Map<KClass<*>, ClassRandomizer<*>>
) : RandomizerCollection {
    override fun getRandomizers(): Map<KClass<*>, ClassRandomizer<*>> {
        return randomizersMap
    }
}