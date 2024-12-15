//
// Created by dgern on 15.12.2024.
//

#include <iostream>

#include "Day01.h"

Day01::Day01() : BaseDay{"day01.txt"} {}

void Day01::solvePartOne() {
    auto sum = 0;
    auto& input = m_inputLines[0];

    for (int idx = 0; idx < input.size(); ++idx) {
        auto nextIdx = (idx + 1) % input.size();

        if (input[idx] == input[nextIdx])
            sum += input[idx] - '0';
    }

    std::cout << "Captcha solution: " << sum << "\n";
}

void Day01::solvePartTwo() {
    auto sum = 0;
    auto& input = m_inputLines[0];
    auto halfSize = input.size() / 2;

    for (int idx = 0; idx < input.size(); ++idx) {
        auto nextIdx = (idx + halfSize) % input.size();

        if (input[idx] == input[nextIdx])
            sum += input[idx] - '0';
    }

    std::cout << "Captcha solution part two: " << sum << "\n";
}
