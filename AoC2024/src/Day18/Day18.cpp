//
// Created by dgern on 18.12.2024.
//

#include "iostream"
#include "deque"
#include "unordered_set"
#include <unordered_map>


#include "Day18.h"

Day18::Day18() : BaseDay{"day18.txt"} {
    initBytes();
    initGrid();
}

void Day18::solvePartOne() {
    // set bytes
    for (int i = 0; i < SIM_BYTES; ++i) {
        auto& byte = m_bytes[i];
        m_grid[byte.y][byte.x] = '#';
    }

    std::cout << "Number of steps to the end: " << getScoreToEnd() << "\n";
}

void Day18::solvePartTwo() {
    for (int i = SIM_BYTES; i < m_bytes.size(); ++i) {
        auto& byte = m_bytes[i];

        m_grid[byte.y][byte.x] = '#';
        auto score = getScoreToEnd();

        if (score == UNREACHABLE) {
            std::cout << "Coordinate of found byte: " << byte.x << "," << byte.y << "\n";
            break;
        }
    }
}

void Day18::initBytes() {
    for (const auto& line: m_inputLines) {
        auto splited = split<int>(line, ',');
        m_bytes.push_back(Vector2D{splited[0], splited[1]});
    }
}

void Day18::initGrid() {
    for (int y = 0; y < GRID_SIZE; ++y) {
        std::vector<char> row;
        for (int x = 0; x < GRID_SIZE; ++x) {
            row.push_back('.');
        }
        m_grid.push_back(row);
    }
}

int Day18::getScoreToEnd() const {
    std::deque<Tile> queue;
    std::unordered_map<Vector2D, int, Vector2DHash> seen{};

    queue.push_back(Tile{START, 0});
    seen[START] = 0;

    while (!queue.empty()) {
        auto tile = queue.front();
        queue.pop_front();

        for (const auto& offset: directNeighborOffsets) {
            auto check = Tile{tile.pos + offset, tile.score + 1};

            if (check.pos.x < 0 || check.pos.x > GRID_SIZE - 1 ||
                check.pos.y < 0 || check.pos.y > GRID_SIZE - 1 ||
                m_grid[check.pos.y][check.pos.x] == '#')
                continue;

            if (seen.contains(check.pos)) {
                if (seen[check.pos] > check.score) {
                    seen[check.pos] = check.score;
                    queue.push_back(check);
                }

                continue;
            }

            seen[check.pos] = check.score;
            queue.push_back(check);
        }
    }

    if (!seen.contains(END))
        return UNREACHABLE;
    else
        return seen[Vector2D{GRID_SIZE - 1, GRID_SIZE - 1}];
}


void Day18::printGrid() const {
    for (const auto& row: m_grid) {
        for (const auto& c: row) {
            std::cout << c;
        }

        std::cout << "\n";
    }

    std::cout << std::endl;
}
