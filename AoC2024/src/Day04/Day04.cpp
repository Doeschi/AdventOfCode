//
// Created by dgern on 04.12.2024.
//

#include <iostream>

#include "Day04.h"

using namespace std::string_literals;

Day04::Day04() : BaseDay{"day04.txt"} {
    prepareGrid();
    m_size = m_grid.size() - 1;
}

void Day04::solvePartOne() {
    auto xmasCount{0};

    for (auto y{0}; y < m_grid.size(); ++y) {
        for (auto x{0}; x < m_grid[y].size(); ++x) {
            if (m_grid[y][x] == 'X') {
                xmasCount += countXmas(x, y);
            }
        }
    }

    std::cout << "XMAS count: " << xmasCount << std::endl;
}

void Day04::solvePartTwo() {
    auto masCrossCount{0};
    for (auto y{0}; y < m_grid.size(); ++y) {
        for (auto x{0}; x < m_grid[y].size(); ++x) {
            if (m_grid[y][x] == 'A' && hasMasCross(x, y)) {
                ++masCrossCount;
            }
        }
    }

    std::cout << "MAS cross count: " << masCrossCount << std::endl;
}

void Day04::prepareGrid() {
    for (const auto& inputLine: m_inputLines) {
        std::vector<char> gridLine;

        for (const auto& c: inputLine) {
            gridLine.push_back(c);
        }

        m_grid.push_back(gridLine);
    }
}

int Day04::countXmas(int x, int y) const {
    auto xmasCount{0};

    for (auto yScale{-1}; yScale < 2; ++yScale) {
        for (auto xScale{-1}; xScale < 2; ++xScale) {
            if (yScale != 0 || xScale != 0) {
                if (hasXmas(x, y, xScale, yScale)) {
                    ++xmasCount;
                }
            }
        }
    }

    return xmasCount;
}

bool Day04::hasXmas(int x, int y, int xScale, int yScale) const {
    auto searchString{"XMAS"s};

    for (auto i{0}; i < searchString.length(); ++i) {
        auto testX = x + (i * xScale);
        auto testY = y + (i * yScale);

        if (testX < 0 || testX > m_size || testY < 0 || testY > m_size)
            return false;

        if (m_grid[testY][testX] != searchString[i])
            return false;
    }

    return true;
}

bool Day04::hasMasCross(int x, int y) const {
    if (x > 0 && x < m_size && y > 0 && y < m_size) {
        std::string mainDiagonal{};
        mainDiagonal = m_grid[y - 1][x + 1];
        mainDiagonal += m_grid[y + 1][x - 1];

        std::string secondaryDiagonal{};
        secondaryDiagonal = m_grid[y - 1][x - 1];
        secondaryDiagonal += m_grid[y + 1][x + 1];

        if ((mainDiagonal == "MS" || mainDiagonal == "SM") && (secondaryDiagonal == "SM" || secondaryDiagonal == "MS"))
            return true;
    }

    return false;
}
