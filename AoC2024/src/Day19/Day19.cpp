//
// Created by dgern on 19.12.2024.
//

#include "Day19.h"
#include "../util/Util.h"

Day19::Day19() : BaseDay("day19.txt") {
    initTowels();
}

void Day19::solvePartOne() {

}

void Day19::solvePartTwo() {

}

void Day19::initTowels() {
    m_towels = split<std::string>(m_inputLines[0], ',');
    for (auto& towel: m_towels)
        trim(towel);

    m_patterns.insert(m_patterns.begin(), m_inputLines.begin() + 2, m_inputLines.end());
}
