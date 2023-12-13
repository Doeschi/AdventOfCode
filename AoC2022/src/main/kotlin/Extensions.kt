/*
 * Copyright (c) 2023. by Dominic Gernert
 */


fun IntRange.intersects(other: IntRange): Boolean {
    return last >= other.first && first <= other.last
}

fun IntRange.intersection(other: IntRange): IntRange {
    return IntRange(maxOf(first, other.first), minOf(last, other.last))
}