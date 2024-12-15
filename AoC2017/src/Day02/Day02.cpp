//
// Created by dgern on 15.12.2024.
//
#include <iostream>
#include <algorithm>

#include "Day02.h"
#include "../util/Util.h"

Day02::Day02() : BaseDay{"day02.txt"} {
    initSpreadSheet();
}

void Day02::solvePartOne() {
    int checksum = 0;

    for (const auto& row: m_spreadSheet) {
        auto min = *std::min_element(row.begin(), row.end());
        auto max = *std::max_element(row.begin(), row.end());

        checksum += max - min;
    }

    std::cout << "Checksum: " << checksum << "\n";
}

void Day02::solvePartTwo() {
    auto sum = 0;

    for (const auto& row: m_spreadSheet) {
        for (int i = 0; i < row.size(); ++i) {
            for (int j = 0; j < row.size(); ++j) {
                if (i == j)
                    continue;

                if (row[i] % row[j] == 0) {
                    sum += row[i] / row[j];
                    goto found;
                }
            }
        }
        found:;
    }

    std::cout << "Sum of evenly divisible values: " << sum << "\n";
}

void Day02::initSpreadSheet() {
    for (const auto& inputLine: m_inputLines) {
        auto row = split<int>(inputLine, ' ');
        m_spreadSheet.push_back(row);
    }
}
