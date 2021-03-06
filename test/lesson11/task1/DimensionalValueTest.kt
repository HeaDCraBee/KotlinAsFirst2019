package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

internal class DimensionalValueTest {

    private fun assertApproxEquals(expected: DimensionalValue, actual: DimensionalValue, eps: Double) {
        assertEquals(expected.dimension, actual.dimension)
        assertEquals(expected.value, actual.value, eps)
    }

    @Test
    @Tag("Normal")
    fun base() {
        val first = DimensionalValue(1.0, "Kg")
        assertEquals(1000.0, first.value)
        assertEquals(Dimension.GRAM, first.dimension)

        val second = DimensionalValue("200 m")
        assertEquals(200.0, second.value)
        assertEquals(Dimension.METER, second.dimension)

        assertThrows(IllegalArgumentException::class.java) {
            DimensionalValue("21e m")
        }

        assertThrows(IllegalArgumentException::class.java) {
            DimensionalValue("21 Ks")
        }

        val third = DimensionalValue(1.0, "KHz")
        assertEquals(1000.0, third.value)
        assertEquals(Dimension.HERTZ, third.dimension)
    }

    @Test
    @Tag("Easy")
    fun plus() {
        assertApproxEquals(DimensionalValue("2 Km"), DimensionalValue("1 Km") + DimensionalValue("1000 m"), 1e-8)

        assertThrows(IllegalArgumentException::class.java) {
            DimensionalValue("1 g") + DimensionalValue("1 m")
        }

        assertThrows(IllegalArgumentException::class.java) {
            DimensionalValue("e Kg") + DimensionalValue("1 g")
        }

        assertApproxEquals(DimensionalValue("3.5 KHz"), DimensionalValue("2 KHz") + DimensionalValue("1500 Hz"), 1e-8)
    }

    @Test
    @Tag("Easy")
    operator fun unaryMinus() {
        assertApproxEquals(DimensionalValue("-2 g"), -DimensionalValue("2 g"), 1e-12)

        assertApproxEquals(DimensionalValue("-2 Kg"), -DimensionalValue("2000 g"), 1e-12)

        assertApproxEquals(DimensionalValue("-1 g"), -DimensionalValue("1 g"), 1e-12)

        assertApproxEquals(DimensionalValue("-1 Hz"), -DimensionalValue("1 Hz"), 1e-12)
    }

    @Test
    @Tag("Easy")
    fun minus() {
        assertApproxEquals(DimensionalValue("0 m"), DimensionalValue("1 Km") - DimensionalValue("1000 m"), 1e-10)

        assertThrows(IllegalArgumentException::class.java) {
            DimensionalValue("1 g") - DimensionalValue("1 m")
        }

        assertApproxEquals(DimensionalValue("3 g"), DimensionalValue("1 g") - DimensionalValue("-2 g"), 1e-10)

        assertApproxEquals(DimensionalValue("-1 g"), DimensionalValue("1 g") - DimensionalValue("2 g"), 1e-10)

        assertApproxEquals(DimensionalValue("1 Hz"), DimensionalValue("1 KHz") - DimensionalValue("999 Hz"), 1e-10)
    }

    @Test
    @Tag("Easy")
    fun times() {
        assertApproxEquals(DimensionalValue("2 Kg"), DimensionalValue("2 g") * 1000.0, 1e-8)

        assertApproxEquals(DimensionalValue("-2 Kg"), DimensionalValue("2 g") * -1000.0, 1e-8)

        assertApproxEquals(DimensionalValue("-2 mHz"), DimensionalValue("2 Hz") * -0.001, 1e-8)
    }

    @Test
    @Tag("Easy")
    fun divValue() {
        assertEquals(1.0, DimensionalValue("3 m") / DimensionalValue("3000 mm"), 1e-10)

        assertThrows(IllegalArgumentException::class.java) {
            DimensionalValue("1 g") / DimensionalValue("1 m")
        }

        assertEquals(1.0, DimensionalValue("3 Hz") / DimensionalValue("3000 mHz"), 1e-10)
    }

    @Test
    @Tag("Easy")
    fun divDouble() {
        assertApproxEquals(DimensionalValue("42 mm"), DimensionalValue("42 m") / 1000.0, 1e-11)

        assertApproxEquals(DimensionalValue("42 KHz"), DimensionalValue("42 Hz") / 0.001, 1e-11)
    }

    @Test
    @Tag("Easy")
    fun equals() {
        assertEquals(DimensionalValue("1 Kg"), DimensionalValue("1 Kg"))

        assertEquals(DimensionalValue("3 mm"), DimensionalValue("3 mm"))

        assertEquals(DimensionalValue("1 KHz"), DimensionalValue("1 KHz"))
    }

    @Test
    @Tag("Easy")
    fun hashCodeTest() {
        assertEquals(DimensionalValue("1 Kg").hashCode(), DimensionalValue("1 Kg").hashCode())

        assertEquals(DimensionalValue("1 Hz").hashCode(), DimensionalValue("1 Hz").hashCode())
    }

    @Test
    @Tag("Easy")
    fun compareTo() {
        assertTrue(DimensionalValue("1 Kg") < DimensionalValue("1500 g"))

        assertTrue(DimensionalValue("1 m") > DimensionalValue("900 mm"))

        assertTrue(DimensionalValue("1 Hz") > DimensionalValue("0.0009 Hz"))
    }
}