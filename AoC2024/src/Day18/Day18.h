//
// Created by dgern on 18.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"
#include "../util/Util.h"

class Day18 : public BaseDay {
public:
    Day18();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Grid = std::vector<std::vector<char>>;

    struct Tile {
        Vector2D pos{};
        int score{};

        bool operator==(const Tile& t) const {
            return pos.x == t.pos.x && pos.y == t.pos.y;
        };
    };

    static constexpr const int GRID_SIZE = 71;
    static constexpr const int SIM_BYTES = 1024;
    static constexpr const int UNREACHABLE = -1;

    static constexpr const Vector2D START{0, 0};
    static constexpr const Vector2D END{GRID_SIZE - 1, GRID_SIZE - 1};

    Grid m_grid;
    std::vector<Vector2D> m_bytes;

    void initBytes();

    void initGrid();

    void printGrid() const;

    int getScoreToEnd() const;
};