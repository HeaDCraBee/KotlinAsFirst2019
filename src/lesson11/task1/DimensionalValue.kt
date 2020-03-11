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
class DimensionalValue(nValue: Double, nDimension: String) : Comparable<DimensionalValue> {


    companion object {

        //Ф-ия находит аббревиатуру префикса
        private fun getPrefix(dimension: String): String {
            for (pref in DimensionPrefix.values()) {

                if (pref.abbreviation in dimension && dimension.length > 1)
                    return pref.abbreviation

            }

            return ""
        }

        //Ф-ия находит аббревиатуру размерности
        private fun dimensions(dimension: String): String {
            for (dim in Dimension.values())

                if (dim.abbreviation == dimension.drop(getPrefix(dimension).length))
                    return dim.abbreviation

            throw IllegalArgumentException()
        }

        //Ф-ия находит размерность
        private fun getDimension(dimension: String): Dimension {
            for (dim in Dimension.values()) {

                if (dimensions(dimension) == dim.abbreviation)
                    return dim
            }

            throw IllegalArgumentException()
        }

        //Ф-ия находит множитель
        private fun getMultiplier(dimension: String): Double {
            var res = 1.0

            if (getPrefix(dimension) in dimension) {
                for (pref in DimensionPrefix.values())
                    if (pref.abbreviation == getPrefix(dimension))
                        res = pref.multiplier
            }

            return res
        }

        //Конструктор значения из строки
        private fun parseValue(dimension: String):
                Double = dimension.split(" ").first().toString().toDouble()

        //Конструктор размерности
        private fun parseDimension(dimension: String): String {
            val a = dimension.split(" ").last()

            return if (getDimension(a) in Dimension.values())
                a
            else
                throw IllegalArgumentException()
        }

    }


    /**
     * Величина с БАЗОВОЙ размерностью (например для 1.0Kg следует вернуть результат в граммах -- 1000.0)
     */
    val value: Double = if (nDimension == dimensions(nDimension)) nValue else nValue * getMultiplier(nDimension)
    /**
     *
     * БАЗОВАЯ размерность (опять-таки для 1.0Kg следует вернуть GRAM)
     */
    val dimension: Dimension  = getDimension(nDimension)

    /**
     * Конструктор из строки. Формат строки: значение пробел размерность (1 Kg, 3 mm, 100 g и так далее).
     */
    constructor(s: String) :
            this(parseValue(s), parseDimension(s))

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
    GRAM("g"),
    HERTZ("Hz");
}

/**
 * Приставка размерности. Опять-таки можно добавить новые варианты (деци-, санти-, мега-, ...), но нельзя убирать
 */
enum class DimensionPrefix(val abbreviation: String, val multiplier: Double) {
    KILO("K", 1000.0),
    MILLI("m", 0.001);
}