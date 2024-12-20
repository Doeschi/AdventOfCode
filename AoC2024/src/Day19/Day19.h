//
// Created by dgern on 19.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"

class Day19 : public BaseDay {
public:
    Day19();

    void solvePartOne() override;

    void solvePartTwo() override;
private:
    std::vector<std::string> m_towels;
    std::vector<std::string> m_patterns;

    void initTowels();
};