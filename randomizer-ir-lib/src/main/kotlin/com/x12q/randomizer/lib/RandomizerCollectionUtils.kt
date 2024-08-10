package com.x12q.randomizer.lib

import kotlin.random.Random


inline fun <reified T:Any> RandomizerCollection.getRandomizer(): ClassRandomizer<T>?{
    return randomizersMap[T::class] as? ClassRandomizer<T>
}

inline fun <reified T:Any> RandomContext.random(): T?{
    val randomizer = this.getRandomizer<T>()
    val resultFromRandomizer = randomizer?.random()
    if(resultFromRandomizer!=null){
        return resultFromRandomizer
    }else{
        val rt = when(T::class){
            Int::class -> this.randomConfig.nextInt() as T
            Double::class -> randomConfig.nextDouble() as T
            Float::class -> randomConfig.nextFloat() as T
            Long::class -> randomConfig.nextLong() as T
            Short::class -> randomConfig.nextShort() as T
            Byte::class -> randomConfig.nextByte() as T
            Char::class -> randomConfig.nextChar() as T // Generates a random character between 'A' and 'z'
            Boolean::class -> randomConfig.nextBoolean() as T
            String::class -> randomConfig.nextStringUUID() as T

            // Unsigned types
            UInt::class -> randomConfig.nextUInt() as T
            ULong::class -> randomConfig.nextULong() as T
            UShort::class -> randomConfig.nextUShort() as T
            UByte::class -> randomConfig.nextUByte() as T
            else -> null
        }
        return rt
    }
}

inline fun <reified T:Any> RandomContext.randomOrNull(random:Random): T?{
    val randomizer = this.getRandomizer<T>()
    val v1 = randomizer?.random()
    return if(random.nextBoolean()) v1 else null
}
