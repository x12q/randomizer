package com.x12q.randomizer.randomizer.builder

import com.x12q.randomizer.RDClassData
import com.x12q.randomizer.RandomContext
import com.x12q.randomizer.RandomGenerator
import com.x12q.randomizer.randomizer.ClassRandomizer
import com.x12q.randomizer.randomizer.ParamInfo
import com.x12q.randomizer.randomizer.ParameterRandomizer
import com.x12q.randomizer.randomizer.clazz.classRandomizer
import com.x12q.randomizer.randomizer.param.paramRandomizer
import com.x12q.randomizer.randomizer.primitive.*

/**
 * A builder that can build a list of [ParameterRandomizer]
 */
class ParamRandomizerListBuilder {

    private var normalRandomizers = mutableListOf<ParameterRandomizer<*>>()

    /**
     * Contextual randomizers are those that rely on an external context object.
     */
    var contextualRandomizers = mutableListOf<ParameterRandomizer<*>>()

    /**
     * This must be set before adding any contextual randomizers, otherwise.
     */
    var externalContext: RandomContext? = null


    fun build(): Collection<ParameterRandomizer<*>> {
        return normalRandomizers.toList()
    }

    fun buildContextualRandomizer():Collection<ParameterRandomizer<*>>{
        if (contextualRandomizers.isNotEmpty()) {
            if (externalContext != null) {
                return contextualRandomizers.toList()
            } else {
                throw IllegalStateException("${this::class.simpleName} must have an inner context in order to invoke buildContextualRandomizer")
            }
        } else {
            return emptyList()
        }
    }

    fun add(randomizer: ParameterRandomizer<*>): ParamRandomizerListBuilder {
        normalRandomizers.add(randomizer)
        return this
    }

    inline fun <reified T> randomizerForParameter(
        crossinline condition: (target: ParamInfo) -> Boolean,
        crossinline random: (ParamInfo) -> T,
    ): ParamRandomizerListBuilder {
       return this.add(paramRandomizer(
           condition = condition,
           random = random
       ))
    }

    /**
     * Create a [ParameterRandomizer] that only check for type match
     */
    inline fun <reified T> randomizerForParameter(
        crossinline random: (ParamInfo) -> T,
    ): ParamRandomizerListBuilder {
        return this.add(paramRandomizer(random))
    }


    fun getContext(): RandomContext {
        val context = externalContext
        if (context == null) {
            throw IllegalStateException("A ${RandomContext::class.simpleName} must be provided when adding a contextual randomizer")
        } else {
            return context
        }
    }

    /**
     * Create a [ParameterRandomizer] that only check for type match
     */
    inline fun <reified T> randomizerForParameter(): ParamRandomizerListBuilder {
        this.contextualRandomizers.add(
            paramRandomizer <T> {
                val generator = RandomGenerator(getContext())
                val clzzData = RDClassData.from<T>()
                generator.random(clzzData) as T
            }
        )
        return this
    }


    /**
     * Add a [Set] randomizer to this builder.
     */
    fun <T> set(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Set<T>
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(setParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [Set] randomizer to this builder.
     */
    fun <T> set(
        random: (ParamInfo) -> Set<T>
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(setParamRandomizer(random))
        return this
    }

    /**
     * Add a [List] randomizer to this builder.
     */
    fun <T> list(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> List<T>
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(listParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [List] randomizer to this builder.
     */
    fun <T> list(
        random: (ParamInfo) -> List<T>
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(listParamRandomizer(random))
        return this
    }

    /**
     * Add a [Map] randomizer to this builder.
     */
    fun <K, V> map(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Map<K, V>
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(mapParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [Map] randomizer to this builder.
     */
    fun <K, V> map(
        random: (ParamInfo) -> Map<K, V>
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(mapParamRandomizer(random))
        return this
    }


    /**
     * Add an [Int] randomizer to this builder.
     */
    fun int(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Int,
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(intParamRandomizer(condition, random))
        return this
    }

    /**
     * Add an [Int] randomizer to this builder.
     */
    fun int(
        random: (ParamInfo) -> Int,
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(intParamRandomizer(random))
        return this
    }

    /**
     * Add an [Int] randomizer to this builder.
     */
    fun int(
        range: IntRange,
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(intParamRandomizer(range))
        return this
    }

    /**
     * Add an [Int] randomizer to this builder.
     */
    fun int(until: Int): ParamRandomizerListBuilder {
        normalRandomizers.add(intParamRandomizer(until))
        return this
    }

    /**
     * Add a [Float] randomizer to this builder.
     */
    fun float(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Float
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(floatParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [Float] randomizer to this builder.
     */
    fun float(
        random: (ParamInfo) -> Float
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(floatParamRandomizer(random))
        return this
    }

    /**
     * Add a [Float] randomizer to this builder.
     */
    fun float(from: Float, to: Float): ParamRandomizerListBuilder {
        normalRandomizers.add(floatParamRandomizer(from, to))
        return this
    }

    /**
     * Add a [Float] randomizer to this builder.
     */
    fun float(until: Float): ParamRandomizerListBuilder {
        normalRandomizers.add(floatParamRandomizer(until))
        return this
    }

    /**
     * Add a [String] randomizer to this builder.
     */
    fun string(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> String
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(stringParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [String] randomizer to this builder.
     */
    fun string(
        random: (ParamInfo) -> String
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(stringParamRandomizer(random))
        return this
    }


    /**
     * Add an uuid [String] randomizer to this builder.
     */
    fun uuidString():ParamRandomizerListBuilder{
        normalRandomizers.add(uuidStringParamRandomizer())
        return this
    }

    /**
     * Add a [Double] randomizer to this builder.
     */
    fun double(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Double
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(doubleParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [Double] randomizer to this builder.
     */
    fun double(
        random: (ParamInfo) -> Double
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(doubleParamRandomizer(random))
        return this
    }

    /**
     * Add a [Double] randomizer to this builder.
     */
    fun double(from: Double, to: Double): ParamRandomizerListBuilder {
        normalRandomizers.add(doubleParamRandomizer(from, to))
        return this
    }

    /**
     * Add a [Double] randomizer to this builder.
     */
    fun double(until: Double): ParamRandomizerListBuilder {
        normalRandomizers.add(doubleParamRandomizer(until))
        return this
    }


    /**
     * Add a [Byte] randomizer to this builder.
     */
    fun byte(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Byte
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(byteParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [Byte] randomizer to this builder.
     */
    fun byte(
        random: (ParamInfo) -> Byte
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(byteParamRandomizer(random))
        return this
    }

    /**
     * Add a [Short] randomizer to this builder.
     */
    fun short(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Short
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(shortParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [Short] randomizer to this builder.
     */
    fun short(
        random: (ParamInfo) -> Short
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(shortParamRandomizer(random))
        return this
    }

    /**
     * Add a [Boolean] randomizer to this builder.
     */
    fun boolean(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Boolean
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(booleanParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [Boolean] randomizer to this builder.
     */
    fun boolean(
        random: (ParamInfo) -> Boolean
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(booleanParamRandomizer(random))
        return this
    }

    /**
     * Add a [Long] randomizer to this builder.
     */
    fun long(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Long
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(longParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [Long] randomizer to this builder.
     */
    fun long(
        random: (ParamInfo) -> Long
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(longParamRandomizer(random))
        return this
    }

    /**
     * Add a [Long] randomizer to this builder.
     */
    fun long(
        longRange: LongRange
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(longParamRandomizer(longRange))
        return this
    }

    /**
     * Add a [Long] randomizer to this builder.
     */
    fun long(until: Long): ParamRandomizerListBuilder {
        normalRandomizers.add(longParamRandomizer(until))
        return this
    }

    /**
     * Add a [Char] randomizer to this builder.
     */
    fun char(
        condition: (target: ParamInfo) -> Boolean,
        random: (ParamInfo) -> Char
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(charParamRandomizer(condition, random))
        return this
    }

    /**
     * Add a [Char] randomizer to this builder.
     */
    fun char(
        random: (ParamInfo) -> Char
    ): ParamRandomizerListBuilder {
        normalRandomizers.add(charParamRandomizer(random))
        return this
    }
}
