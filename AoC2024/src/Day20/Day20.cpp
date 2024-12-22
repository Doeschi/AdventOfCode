//
// Created by dgern on 20.12.2024.
//

#include <iostream>
#include <unordered_map>

#include "Day20.h"


Day20::Day20() : BaseDay("day20.txt") {
    initGrid();

    m_width = m_grid[0].size();
    m_height = m_grid.size();

    prepareGridAndPath();
}

void Day20::solvePartOne() {
    std::cout << "2 picoseconds cheat time: " << getCheats(2) << "\n";
}

void Day20::solvePartTwo() {
    std::cout << "20 picoseconds cheat time: " << getCheats(20) << "\n";
}

void Day20::initGrid() {
    for (int y = 0; y < m_inputLines.size(); ++y) {
        std::vector<int> row;

        for (int x = 0; x < m_inputLines[y].size(); ++x) {
            auto& c = m_inputLines[y][x];

            if (c == '#')
                row.push_back(WALL);
            else if (c == '.')
                row.push_back(FREE);
            else if (c == 'S') {
                m_start = Vector2D(x, y);
                row.push_back(FREE);
            } else {
                m_end = Vector2D(x, y);
                row.push_back(FREE);
            }
        }

        m_grid.push_back(row);
    }
}

void Day20::prepareGridAndPath() {
    Vector2D current = m_start;
    auto picoSeconds = 0;

    while (true) {
        m_grid[current.y][current.x] = picoSeconds;
        m_path.push_back(current);

        if (current == m_end)
            break;

        for (const auto& offset: directNeighborOffsets) {
            auto check = current + offset;

            if (m_grid[check.y][check.x] == FREE) {
                current = check;
                break;
            }
        }

        ++picoSeconds;
    }
}

int Day20::getCheats(int picoSeconds) const {
    int cheats = 0;

    for (const auto& pos: m_path) {
        for (int yOffset = -picoSeconds; yOffset <= picoSeconds; ++yOffset) {
            auto diff = picoSeconds - std::abs(yOffset);
            for (int xOffset = -diff; xOffset <= diff; ++xOffset) {
                Vector2D check{pos.x + xOffset, pos.y + yOffset};

                if (!check.isInRange(0, m_width - 1, 0, m_height - 1) ||
                    m_grid[check.y][check.x] == WALL)
                    continue;

                auto distance = std::abs(xOffset) + std::abs(yOffset);
                auto saving = m_grid[check.y][check.x] - m_grid[pos.y][pos.x] - distance;

                if (saving >= 100)
                    ++cheats;
            }
        }
    }

    return cheats;
}

void Day20::printGrid() const {
    for (const auto& row: m_grid) {
        for (const auto& c: row) {
            if (c == WALL)
                std::cout << '#';
            else
                std::cout << std::to_string(c)[0];
        }

        std::cout << "\n";
    }

    std::cout << std::endl;
}