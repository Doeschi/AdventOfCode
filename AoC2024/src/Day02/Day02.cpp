//
// Created by dgern on 02.12.2024.
//

#include <iostream>
#include <algorithm>

#include "Day02.h"
#include "../util/Util.h"

Day02::Day02() : BaseDay{"day02.txt"} {}

void Day02::solvePartOne() {
    auto safeReports{0};

    for (const auto& line: m_inputLines) {
        auto levels = split<int>(line, ' ');

        if (isSafe(levels))
            ++safeReports;
    }

    std::cout << "Number of safe reports: " << safeReports << std::endl;
}

void Day02::solvePartTwo() {
    auto safeReports{0};

    for (const auto& line: m_inputLines) {
        auto levels = split<int>(line, ' ');

        if (isSafe(levels)) {
            ++safeReports;
        } else {
            for (auto i{0}; i < levels.size(); ++i) {
                auto levelsCpy = levels;
                levelsCpy.erase(levelsCpy.begin() + i);

                if (isSafe(levelsCpy)) {
                    ++safeReports;
                    break;
                }
            }
        }
    }

    std::cout << "Number of safe reports with tolerance: " << safeReports << std::endl;
}

bool Day02::isSafe(Day02::Levels& levels) {
    auto const firstDiff = levels[0] - levels[1];

    if (firstDiff == 0)
        return false;

    if (firstDiff < 0)
        std::reverse(levels.begin(), levels.end());

    for (int i{0}; i < levels.size() - 1; ++i) {
        auto const diff = levels[i] - levels[i + 1];

        if (diff > 3 || diff < 1) {
            return false;
        }
    }

    return true;
}

