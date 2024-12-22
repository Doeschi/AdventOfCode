//
// Created by dgern on 19.12.2024.
//

#pragma once

#include <unordered_set>
#include <unordered_map>

#include "../BaseDay/BaseDay.h"

class Day19 : public BaseDay {
public:
    Day19();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    std::unordered_map<char, std::vector<std::string>> m_towels;
    std::vector<std::string> m_patterns;

    void initTowels();

    [[nodiscard]] bool patternIsPossible(const std::string& pattern, std::unordered_set<std::string>& impossiblePatterns);
};