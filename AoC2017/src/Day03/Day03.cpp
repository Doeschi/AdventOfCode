//
// Created by dgern on 15.12.2024.
//

#include <iostream>
#include <array>

#include "Day03.h"
#include "../util/Util.h"

Day03::Day03() : BaseDay{"day03.txt"} {
    m_squareNumber = std::stoi(m_inputLines[0]);
}

void Day03::solvePartOne() {
    Point2D pos{0, 0};
    Point2D direction{1, 0};

    int repeat = 1;
    int nbrOfRepeats = 1;
    int counter = 0;

    for (int i = 1; i < m_squareNumber; ++i) {
        pos += direction;
        --repeat;

        if (repeat == 0) {
            direction = Point2D{direction.y, -direction.x};

            ++counter;
            if (counter == 2) {
                ++nbrOfRepeats;
                counter = 0;
            }

            repeat = nbrOfRepeats;
        }
    }

    std::cout << "Manhattan distance: " << std::abs(pos.x) + std::abs(pos.y) << "\n";
}

void Day03::solvePartTwo() {
    std::array<std::array<int, m_simulationSize>, m_simulationSize> grid{0};

    Point2D pos{m_offset, m_offset};
    Point2D direction{1, 0};

    int repeat = 1;
    int nbrOfRepeats = 1;
    int counter = 0;

    grid[m_offset][m_offset] = 1;

    for (int i = 1; i < m_squareNumber; ++i) {
        pos += direction;
        --repeat;

        if (repeat == 0) {
            direction = Point2D{direction.y, -direction.x};

            ++counter;
            if (counter == 2) {
                ++nbrOfRepeats;
                counter = 0;
            }

            repeat = nbrOfRepeats;
        }

        auto val = 0;

        for (const auto& offset: allNeighborOffsets) {
            auto neighbor = pos + offset;
            val += grid[neighbor.y][neighbor.x];
        }

        if (val > m_squareNumber) {
            std::cout << "First value larger then input: " << val << "\n";
            goto found;
        } else {
            grid[pos.y][pos.x] = val;
        }
    }

    found:;
}
