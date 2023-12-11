/*
 * Copyright (c) 2023. by Dominic Gernert
 */


fun LongRange.intersects(other: LongRange): Boolean {
    return last >= other.first && first <= other.last
}

fun LongRange.intersection(other: LongRange): LongRange {
    return LongRange(maxOf(first, other.first), minOf(last, other.last))
}

// https://discuss.kotlinlang.org/t/feature-request-regex-findall-with-overlap/27729
fun Regex.findAllWithOverlap(input: CharSequence, startIndex: Int = 0): Sequence<MatchResult> {
    if (startIndex < 0 || startIndex > input.length) {
        throw IndexOutOfBoundsException("Start index out of bounds: $startIndex, input length: ${input.length}")
    }
    return generateSequence({ find(input, startIndex) }, { find(input, it.range.first + 1) })
}