//
// Created by dgern on 04.12.2024.
//

#pragma once

#include <vector>

#include "../BaseDay/BaseDay.h"

class Day04 : public BaseDay {
public:
    Day04();

    void solvePartOne() override;

    void solvePartTwo() override;

    using Grid = std::vector<std::vector<char>>;
private:

    Grid m_grid;

    size_t m_size;

    void prepareGrid();

    [[nodiscard]] int countXmas(int x, int y) const;

    [[nodiscard]] bool hasXmas(int x, int y, int xScale, int yScale) const;

    [[nodiscard]] bool hasMasCross(int x, int y) const;
};