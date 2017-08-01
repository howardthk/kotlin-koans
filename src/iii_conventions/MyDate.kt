package iii_conventions

import java.sql.Time

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int =
            when {
                year != other.year -> year - other.year
                month != other.month -> month - other.month
                else -> dayOfMonth - other.dayOfMonth
            }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(start = this, endInclusive = other)

enum class TimeInterval {

    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> = DateIterator(this)

    operator fun contains(d: MyDate): Boolean = start <= d && d <= endInclusive
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {

    var current: MyDate = dateRange.start
    override fun hasNext(): Boolean = current <= dateRange.endInclusive

    override fun next(): MyDate {
        val result = current;
        current = current.nextDay()
        return result
    }

}

operator fun MyDate.plus(timeInterval: TimeInterval) = this.addTimeIntervals(timeInterval = timeInterval, number = 1)

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(timeInterval = this, number = number)

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval) = this.addTimeIntervals(timeInterval = repeatedTimeInterval.timeInterval, number = repeatedTimeInterval.number)
