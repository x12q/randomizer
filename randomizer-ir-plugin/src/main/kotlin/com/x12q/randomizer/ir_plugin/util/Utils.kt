package com.x12q.randomizer.ir_plugin.util

/**
 * Evaluate each function in [functionList], stop at the first output that satisfies [resultIsOk]
 */
fun <T> stopAtFirst(
    resultIsOk: (T) -> Boolean,
    vararg functionList: () -> T
): T? {
    for (func in functionList) {
        val result = func()
        if (resultIsOk(result)) {
            return result
        }
    }
    return null
}

/**
 * Evaluate each function in [functionList], stop at the first output that is not null
 */
fun <T> stopAtFirstNotNullResult(
    vararg functionList: () -> T?
): T? {
    return stopAtFirst({ it != null }, *functionList)
}