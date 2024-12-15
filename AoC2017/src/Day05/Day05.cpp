//
// Created by dgern on 15.12.2024.
//

#include <iostream>

#include "Day05.h"

Day05::Day05() : BaseDay{"day05.txt"} {
    initJumpList();
}

void Day05::solvePartOne() {
    auto jumpList = m_jumpList;
    auto steps = 0;
    auto pointer = 0;

    while (pointer >= 0 && pointer < jumpList.size()) {
        auto tmp = pointer;
        pointer += jumpList[pointer];
        ++jumpList[tmp];
        ++steps;
    }

    std::cout << "Steps needed to reach exit: " << steps << "\n";
}

void Day05::solvePartTwo() {
    auto jumpList = m_jumpList;
    auto steps = 0;
    auto pointer = 0;

    while (pointer >= 0 && pointer < jumpList.size()) {
        auto tmp = pointer;
        pointer += jumpList[pointer];

        if (jumpList[tmp] >= 3)
            --jumpList[tmp];
        else
            ++jumpList[tmp];

        ++steps;
    }

    std::cout << "Steps needed to reach exit with second jump rule: " << steps << "\n";
}

void Day05::initJumpList() {
    for (const auto& jump: m_inputLines)
        m_jumpList.push_back(std::stoi(jump));
}
