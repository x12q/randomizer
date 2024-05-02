package com.x12q.randomizer.util


import com.x12q.randomizer.util.ReflectionUtils.canProduceGeneric
import com.x12q.randomizer.util.ReflectionUtils.isAssignableTo
import com.x12q.randomizer.util.ReflectionUtils.isAssignableToGenericOf
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.reflect.typeOf

class ReflectionUtilsTest {
    open class A
    interface B
    open class A1 : B, A()
    class A2 : A1()
    class B1 : B


    @Test
    fun containGeneric() {
        typeOf<List<A>>().canProduceGeneric(A1::class).shouldBeTrue()
        typeOf<List<A>>().canProduceGeneric(A2::class).shouldBeTrue()

        typeOf<List<B>>().canProduceGeneric(A1::class).shouldBeTrue()
        typeOf<List<B>>().canProduceGeneric(A2::class).shouldBeTrue()

        typeOf<List<A1>>().canProduceGeneric(A2::class).shouldBeTrue()

        typeOf<List<A>>().canProduceGeneric(B::class).shouldBeFalse()


    }

    @Test
    fun checkAssignable() {
        A::class.isAssignableTo(typeOf<A>()) shouldBe true
        A1::class.isAssignableTo(typeOf<A>()) shouldBe true

        B::class.isAssignableTo(typeOf<B>()) shouldBe true
        B1::class.isAssignableTo(typeOf<B>()) shouldBe true
        A1::class.isAssignableTo(typeOf<B>()) shouldBe true

        A::class.isAssignableTo(typeOf<A1>()) shouldBe false
        B::class.isAssignableTo(typeOf<B1>()) shouldBe false

        B::class.isAssignableTo(typeOf<A>()) shouldBe false
        B::class.isAssignableTo(typeOf<A1>()) shouldBe false

        A::class.isAssignableTo(typeOf<B>()) shouldBe false
        A1::class.isAssignableTo(typeOf<B1>()) shouldBe false
    }

    @Test
    fun checkGenericAssignable() {

        A::class.isAssignableToGenericOf(typeOf<List<A>>()) shouldBe true
        B::class.isAssignableToGenericOf(typeOf<List<B>>()) shouldBe true
        A1::class.isAssignableToGenericOf(typeOf<List<A1>>()) shouldBe true
        B1::class.isAssignableToGenericOf(typeOf<List<B1>>()) shouldBe true

        A1::class.isAssignableToGenericOf(typeOf<List<A>>()) shouldBe true
        B::class.isAssignableToGenericOf(typeOf<List<A>>()) shouldBe false
        B1::class.isAssignableToGenericOf(typeOf<List<A>>()) shouldBe false

        A::class.isAssignableToGenericOf(typeOf<List<B>>()) shouldBe false
        A1::class.isAssignableToGenericOf(typeOf<List<B>>()) shouldBe true
        B1::class.isAssignableToGenericOf(typeOf<List<B>>()) shouldBe true

        A::class.isAssignableToGenericOf(typeOf<List<A1>>()) shouldBe false
        B::class.isAssignableToGenericOf(typeOf<List<A1>>()) shouldBe false
        B1::class.isAssignableToGenericOf(typeOf<List<A1>>()) shouldBe false

        B::class.isAssignableToGenericOf(typeOf<List<B1>>()) shouldBe false
        A::class.isAssignableToGenericOf(typeOf<List<B1>>()) shouldBe false
        A1::class.isAssignableToGenericOf(typeOf<List<B1>>()) shouldBe false

    }
}
