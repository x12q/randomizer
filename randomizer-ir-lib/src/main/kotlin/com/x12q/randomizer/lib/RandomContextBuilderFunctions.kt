package com.x12q.randomizer.lib

import com.x12q.randomizer.lib.randomizer.constantRandomizer
import com.x12q.randomizer.lib.randomizer.factoryRandomizer


object RandomContextBuilderFunctions{
    inline fun <reified T : Any> RandomContextBuilder.constant(value:T): RandomContextBuilder {
        return add(constantRandomizer(value))
    }
    inline fun <reified T : Any> RandomContextBuilder.constant(makeValue:()->T): RandomContextBuilder {
        val value = makeValue()
        return constant(value)
    }

    inline fun <reified T : Any> RandomContextBuilder.factory(noinline makeRandom:()->T): RandomContextBuilder {
        return add(factoryRandomizer(makeRandom))
    }

    // TODO add other convenient functions such as int(), float(), string()...
}
