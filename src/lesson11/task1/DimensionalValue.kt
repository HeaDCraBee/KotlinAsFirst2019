@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import java.lang.IllegalArgumentException


/**
 * Класс "Величина с размерностью".
 *
 * Предназначен для представления величин вроде "6 метров" или "3 килограмма"
 * Общая сложность задания - средняя.
 * Величины с размерностью можно складывать, вычитать, делить, менять им знак.
 * Их также можно умножать и делить на число.
 *
 * В конструктор передаётся вещественное значение и строковая размерность.
 * Строковая размерность может:
 * - либо строго соответствовать одной из abbreviation класса Dimension (m, g)
 * - либо соответствовать одной из приставок, к которой приписана сама размерность (Km, Kg, mm, mg)
 * - во всех остальных случаях следует бросить IllegalArgumentException
 */
class DimensionalValue(private val nValue: Double, private val nDimension: String) : Comparable<DimensionalValue> {


    companion object {

        //Ф-ия находит аббревиатуру префикса
        private fun getPrefix(x: String): String {
            for (pref in DimensionPrefix.values()) {

                if (pref.abbreviation in x && x.length > 1)
                    return pref.abbreviation

            }

            return ""
        }

        //Ф-ия находит аббревиатуру размерности
        private fun dimensions(x: String): String {
            for (dim in Dimension.values())

                if (dim.abbreviation == x.drop(getPrefix(x).length))
                    return dim.abbreviation

            throw IllegalArgumentException()
        }

        //Ф-ия находит размерность
        private fun getDimension(x: String): Dimension {
            for (dim in Dimension.values()) {

                if (dimensions(x) == dim.abbreviation)
                    return dim
            }

            throw IllegalArgumentException()
        }

        //Ф-ия находит множитель
        private fun getMultiplier(x: String): Double {
            var res = 1.0

            if (getPrefix(x) in x) {
                for (pref in DimensionPrefix.values())
                    if (pref.abbreviation == getPrefix(x))
                        res = pref.multiplier
            }

            return res
        }

        //Конструктор значения из строки
        private fun valueConstructor(x: String):
                Double = x.split(" ").first().toString().toDouble()

        //Конструктор размерности
        private fun dimensionConstructor(x: String): String {
            val a = x.split(" ").last()

            return if (getDimension(a) in Dimension.values())
                a
            else
                throw IllegalArgumentException()
        }

    }


    /**
     * Величина с БАЗОВОЙ размерностью (например для 1.0Kg следует вернуть результат в граммах -- 1000.0)
     */
    val value: Double
        get() {
            return if (nDimension == dimensions(nDimension))
                nValue
            else
                nValue * getMultiplier(nDimension)
        }
    /**
     *
     * БАЗОВАЯ размерность (опять-таки для 1.0Kg следует вернуть GRAM)
     */
    val dimension: Dimension get() = getDimension(nDimension)

    /**
     * Конструктор из строки. Формат строки: значение пробел размерность (1 Kg, 3 mm, 100 g и так далее).
     */
    constructor(s: String) :
            this(valueConstructor(s), dimensionConstructor(s))

    /**
     * Сложение с другой величиной. Если базовая размерность разная, бросить IllegalArgumentException
     * (нельзя складывать метры и килограммы)
     */
    operator fun plus(other: DimensionalValue): DimensionalValue {
        return if (dimension == other.dimension)
            DimensionalValue(value + other.value, dimension.abbreviation)
        else
            throw IllegalArgumentException()
    }

    /**
     * Смена знака величины
     */
    /*Знак не меняется на противоположный, чтобы я не делал со начениями и конвертором...
     Во всех остальных функциях знаки нормально учитываются.
(68)* x.split(" ").first().toString().toDouble() если это умножить на -1 то знаки у assert И actual поменяются местами, но в самой функции умножение на -1 не работает
     * - номер строки
     */
    operator fun unaryMinus():
            DimensionalValue = DimensionalValue(-value, dimension.abbreviation)

    /**
     * Вычитание другой величины. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun minus(other: DimensionalValue): DimensionalValue {
        return if (dimension == other.dimension)
            DimensionalValue(value - other.value, dimension.abbreviation)
        else
            throw IllegalArgumentException()
    }

    /**
     * Умножение на число
     */
    operator fun times(other: Double):
            DimensionalValue = DimensionalValue(value * other, dimension.abbreviation)

    /**
     * Деление на число
     */
    operator fun div(other: Double):
            DimensionalValue = DimensionalValue(value / other, dimension.abbreviation)

    /**
     * Деление на другую величину. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun div(other: DimensionalValue): Double {
        return if (dimension == other.dimension)
            value / other.value
        else
            throw IllegalArgumentException()
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?):
            Boolean = other is DimensionalValue && dimension == other.dimension && value == other.value


    /**
     * Сравнение на больше/меньше. Если базовая размерность разная, бросить IllegalArgumentException
     */
    override fun compareTo(other: DimensionalValue): Int {
        if (dimension == other.dimension)
            return when {
                value > other.value -> 1
                value < other.value -> -1
                else -> 0
            }
        else
            throw IllegalArgumentException()
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + dimension.hashCode()

        return result
    }


}

/**
 * Размерность. В этот класс можно добавлять новые варианты (секунды, амперы, прочие), но нельзя убирать
 */
enum class Dimension(val abbreviation: String) {
    METER("m"),
    GRAM("g");
}

/**
 * Приставка размерности. Опять-таки можно добавить новые варианты (деци-, санти-, мега-, ...), но нельзя убирать
 */
enum class DimensionPrefix(val abbreviation: String, val multiplier: Double) {
    KILO("K", 1000.0),
    MILLI("m", 0.001);
}