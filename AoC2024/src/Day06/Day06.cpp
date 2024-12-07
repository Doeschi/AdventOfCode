//
// Created by dgern on 06.12.2024.
//

#include <iostream>
#include <stdexcept>
#include <unordered_set>

#include "Day06.h"

Day06::Day06() : BaseDay{"day06.txt"} {
    initGrid();
}

void Day06::solvePartOne() {
    auto visitedPos{0};

    while (true) {
        auto nextPos{getNextPos()};

        if (nextPos == invalidPos)
            break;

        if (m_grid[nextPos.y][nextPos.x] == '#') {
            pos.dir = (pos.dir + 1) % 4;
        } else {
            if (m_grid[pos.y][pos.x] != 'X') {
                ++visitedPos;
                m_grid[pos.y][pos.x] = 'X';
            }

            pos = nextPos;
        }
    }

    std::cout << "Number of distinct positions: " << visitedPos + 1 << std::endl;
}

void Day06::solvePartTwo() {
    initGrid();

    auto obstructionPositions{0};
    auto startPos{pos};

    for (auto y{0}; y < m_grid.size(); ++y) {
        for (auto x{0}; x < m_grid[0].size(); ++x) {
            if (m_grid[y][x] == '.') {
                m_grid[y][x] = '#';

                std::unordered_set<Position, PositionHash> visitedPos;

                while (true) {
                    auto nextPos{getNextPos()};

                    if (nextPos == invalidPos)
                        goto end;

                    if (m_grid[nextPos.y][nextPos.x] == '#') {
                        pos.dir = (pos.dir + 1) % 4;
                    } else if (visitedPos.contains(nextPos)) {
                        break;
                    } else {
                        visitedPos.insert(pos);
                        pos = nextPos;
                    }
                }

                ++obstructionPositions;

                end:
                m_grid[y][x] = '.';
                pos = startPos;
            }
        }
    }

    std::cout << "Number of obstruction positions: " << obstructionPositions << std::endl;
}

void Day06::initGrid() {
    m_grid.clear();

    for (auto y{0}; y < m_inputLines.size(); ++y) {
        std::vector<char> line;

        for (auto x{0}; x < m_inputLines[y].length(); ++x) {
            auto c = m_inputLines[y][x];

            if (c != '.' && c != '#') {
                switch (c) {
                    case '^':
                        pos.dir = Direction::UP;
                        break;
                    case '>':
                        pos.dir = Direction::RIGHT;
                        break;
                    case 'v':
                        pos.dir = Direction::DOWN;
                        break;
                    case '<':
                        pos.dir = Direction::LEFT;
                        break;
                    default:
                        throw std::runtime_error{"Char unknown"};
                }

                pos.x = x;
                pos.y = y;
            }

            line.push_back(c);
        }

        m_grid.push_back(line);
    }
}

void Day06::printGrid() {
    for (const auto& line: m_grid) {
        for (const auto& c: line) {
            std::cout << c;
        }

        std::cout << std::endl;
    }

    std::cout << std::endl;
}

Day06::Position Day06::getNextPos() const {
    Position nextPos{pos};

    if (pos.dir == Direction::UP)
        --nextPos.y;
    else if (pos.dir == Direction::RIGHT)
        ++nextPos.x;
    else if (pos.dir == Direction::DOWN)
        ++nextPos.y;
    else
        --nextPos.x;

    if (nextPos.x < 0 || nextPos.x > m_grid[0].size() - 1 || nextPos.y < 0 || nextPos.y > m_grid.size() - 1)
        return invalidPos;

    return nextPos;
}
