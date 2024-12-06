//
// Created by dgern on 06.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"

class Day06 : public BaseDay {
public:
    Day06();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Grid = std::vector<std::vector<char>>;

    enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    };

    struct Position {
        int x{0};
        int y{0};
        int dir{Direction::UP};

        bool operator==(const Position& other) const = default;
    };

    struct PositionHash {
        std::size_t operator()(const Position& p) const {
            std::size_t h1 = std::hash<int>()(p.x);
            std::size_t h2 = std::hash<int>()(p.y);
            std::size_t h3 = std::hash<int>()(p.dir);

            return h1 ^ (h2 << 1) ^ (h3 << 2);
        }
    };

    Grid m_grid;
    Position pos;
    Position invalidPos{-1, -1};

    void initGrid();

    void printGrid();

    Position getNextPos() const;
};