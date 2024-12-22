//
// Created by dgern on 19.12.2024.
//

#include <iostream>

#include "Day19.h"
#include "../util/Util.h"

Day19::Day19() : BaseDay("day19.txt") {
    initTowels();
}

void Day19::solvePartOne() {
    auto possiblePatterns = 0;
    for (const auto& pattern: m_patterns) {
        std::unordered_set<std::string> impossiblePatterns{};
        if (patternIsPossible(pattern, impossiblePatterns))
            ++possiblePatterns;

    }

    std::cout << "Possible patterns: " << possiblePatterns << "\n";
}

void Day19::solvePartTwo() {

}

void Day19::initTowels() {
    auto towels = split<std::string>(m_inputLines[0], ',');

    for (auto& towel: towels) {
        trim(towel);
        m_towels[towel[0]].push_back(towel);
    }

    m_patterns.insert(m_patterns.begin(), m_inputLines.begin() + 2, m_inputLines.end());
}

bool Day19::patternIsPossible(const std::string& pattern, std::unordered_set<std::string>& impossiblePatterns) {
    for (const auto& towel: m_towels[pattern[0]]) {
        if (pattern.starts_with(towel)) {
            if (pattern.size() == towel.size())
                return true;

            auto reducedPattern = pattern.substr(towel.size());

            if (impossiblePatterns.contains(reducedPattern))
                return false;

            if (patternIsPossible(reducedPattern, impossiblePatterns))
                return true;
        }
    }

    impossiblePatterns.insert(pattern);
    return false;
}
