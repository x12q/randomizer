package com.x12q.randomizer.randomizer

import kotlin.reflect.KParameter

/**
 * Create a [ParameterRandomizer] that check parameter with [condition], and generate random instances with [makeRandomIfApplicable]
 */
inline fun <reified T> paramRandomizer(
    crossinline condition: (ParamInfo) -> Boolean,
    crossinline makeRandomIfApplicable: (ParamInfo) -> T,
): ParameterRandomizer<T> {

    return object : ParameterRandomizer<T> {

        override val paramClassData: RDClassData = RDClassData.from<T>()

        override fun isApplicableTo(
            parameterClassData: RDClassData,
            parameter: KParameter,
            parentClassData: RDClassData
        ): Boolean {
            val paramInfo = ParamInfo(
                paramClass = paramClassData,
                kParam = parameter,
                parentClass = parentClassData
            )
            return condition(paramInfo)
        }

        override fun random(parameterClassData: RDClassData, parameter: KParameter, parentClassData: RDClassData): T {
            val paramInfo = ParamInfo(
                paramClass = paramClassData,
                kParam = parameter,
                parentClass = parentClassData
            )
            return makeRandomIfApplicable(paramInfo)
        }
    }
}
