//
// Created by dgern on 20.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"
#include "../util/Util.h"

class Day20 : public BaseDay {
public:
    Day20();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Grid = std::vector<std::vector<int>>;

    static constexpr int WALL = -1;
    static constexpr int FREE = -2;

    Grid m_grid{};
    int m_width{};
    int m_height{};

    std::vector<Vector2D> m_path{};

    Vector2D m_start{};
    Vector2D m_end{};

    void initGrid();

    void prepareGridAndPath();

    [[nodiscard]] int getCheats(int picoSeconds) const;

    void printGrid() const;
};