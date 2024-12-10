//
// Created by dgern on 06.12.2024.
//

#include <iostream>
#include <stdexcept>
#include <unordered_set>

#include "Day06.h"

Day06::Day06() : BaseDay{"day06.txt"} {
    initLab();
}

void Day06::solvePartOne() {
    auto lab{m_lab};

    // walk the path
    lab.getPath();

    auto visitedPos{0};
    for (const auto& line: lab.grid) {
        for (const auto& c: line) {
            if (c == 'X')
                ++visitedPos;
        }
    }

    std::cout << "Number of distinct positions: " << visitedPos << std::endl;
}

void Day06::solvePartTwo() {
    auto pathLab{m_lab};
    auto path{pathLab.getPath()};

    path.erase(m_lab.guardPos);
    std::unordered_set<Position, PositionHash> validPositions;

    auto testLab{m_lab};
    for (const auto& possiblePos: path) {
        if (validPositions.contains(possiblePos))
            continue;

        testLab.grid[possiblePos.y][possiblePos.x] = '#';
        std::unordered_set<PositionWithDir, PositionWithDirHash> visitedPos;

        while (true) {
            visitedPos.insert(PositionWithDir{testLab.guardPos.x, testLab.guardPos.y, testLab.dir});

            auto nextPos{testLab.getNextPos()};

            if (nextPos == invalidPos)
                break;
            if (testLab.grid[nextPos.y][nextPos.x] == '#') {
                testLab.turnRight();
            } else if (visitedPos.contains(PositionWithDir(nextPos.x, nextPos.y, testLab.dir))) {
                validPositions.insert(possiblePos);
                break;
            } else {
                testLab.guardPos = nextPos;
            }
        }

        testLab.grid[possiblePos.y][possiblePos.x] = '.';
        testLab.guardPos = m_lab.guardPos;
        testLab.dir = m_lab.dir;
    }

    std::cout << "Number of valid positions: " << validPositions.size() << std::endl;
}

void Day06::initLab() {
    for (auto y{0}; y < m_inputLines.size(); ++y) {
        std::vector<char> line;

        for (auto x{0}; x < m_inputLines[y].length(); ++x) {
            auto c = m_inputLines[y][x];

            if (c != '.' && c != '#') {
                switch (c) {
                    case '^':
                        m_lab.dir = Direction::UP;
                        break;
                    case '>':
                        m_lab.dir = Direction::RIGHT;
                        break;
                    case 'v':
                        m_lab.dir = Direction::DOWN;
                        break;
                    case '<':
                        m_lab.dir = Direction::LEFT;
                        break;
                    default:
                        throw std::runtime_error{"Char unknown"};
                }

                m_lab.guardPos.x = x;
                m_lab.guardPos.y = y;
            }

            line.push_back(c);
        }

        m_lab.grid.push_back(line);
    }
}

Day06::Position Day06::Lab::getNextPos() const {
    Position nextPos{guardPos};

    if (dir == Direction::UP)
        --nextPos.y;
    else if (dir == Direction::RIGHT)
        ++nextPos.x;
    else if (dir == Direction::DOWN)
        ++nextPos.y;
    else
        --nextPos.x;

    if (nextPos.x < 0 || nextPos.x > grid[0].size() - 1 || nextPos.y < 0 || nextPos.y > grid.size() - 1)
        return invalidPos;

    return nextPos;
}


void Day06::Lab::turnRight() {
    dir = (dir + 1) % 4;
}

std::unordered_set<Day06::Position, Day06::PositionHash> Day06::Lab::getPath() {
    std::unordered_set<Position, PositionHash> positions;

    while (true) {
        positions.insert(guardPos);
        grid[guardPos.y][guardPos.x] = 'X';

        auto nextPos{getNextPos()};
        if (nextPos == invalidPos)
            break;
        if (grid[nextPos.y][nextPos.x] == '#')
            turnRight();
        else
            guardPos = nextPos;
    }

    return positions;
}

void Day06::Lab::printGrid() const {
    for (const auto& line: grid) {
        for (const auto& c: line) {
            std::cout << c;
        }

        std::cout << std::endl;
    }

    std::cout << std::endl;
}

