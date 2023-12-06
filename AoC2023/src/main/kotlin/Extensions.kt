/*
 * Copyright (c) 2023. by Dominic Gernert
 */


fun LongRange.intersects(other: LongRange): Boolean {
    return last >= other.first && first <= other.last
}

fun LongRange.intersection(other: LongRange): LongRange {
    return LongRange(maxOf(first, other.first), minOf(last, other.last))
}