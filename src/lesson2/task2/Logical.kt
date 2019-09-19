@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.sqr

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
    sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean = number % 10 + number / 10 % 10 == number / 100 % 10 + number / 1000

/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = (x1 == x2) || (y1 == y2) || (x1 + y1 == x2 + y2) || (sqr(x2 - x1) == sqr(y2 - y1))


/**
 * Простая
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int {
    if (month == 1) return 31
    else if (month == 3) return 31
    else if (month == 4) return 30
    else if (month == 5) return 31
    else if (month == 6) return 30
    else if (month == 7) return 31
    else if (month == 8) return 31
    else if (month == 9) return 3
    else if (month == 10) return 31
    else if (month == 11) return 31
    else if (month == 12) return 31
    else {
        if ((year % 100) == 0) {
            if (year % 400 == 0) return 29
            else return 28
        }
        else if (year % 4 == 0) return 29
        else return 28
    }
}

/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(
    x1: Double, y1: Double, r1: Double,
    x2: Double, y2: Double, r2: Double
): Boolean = (((sqr(x1 - x2) + sqr(y1 - y2) <= sqr(r2)) && (sqr(x1 + r1 - x2) + sqr(y1 + r1 - y2) <= sqr(r2)) && (sqr(x1 -r1 - x2) + sqr(y1 - r1 - y2) <= sqr(r2)) || ((x1 == x2) && (y1 == y2) && (r1 == r2))))
// ((x1 in x2 - r2 .. x2 + r2) && (y1 in y2 -r2 .. y2 + r2) && (x1 + r1 in x2 - r2 .. x2 + r2) && (x1 - r1 in x2 - r2 .. x2 + r2) && (y1 + r1 in y2 - r2 .. y2 + r2) && (y1 - r1 in y2 - r2 .. y2 + r2))

/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean = TODO()
