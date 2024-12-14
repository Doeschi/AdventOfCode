//
// Created by dgern on 14.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"
#include "../util/Util.h"

class Day14 : public BaseDay {
public:
    Day14();

    void solvePartTwo() override;

    void solvePartOne() override;

private:
    struct Robot {
        Point2D pos;
        Point2D vel;
    };

    std::vector<Robot> m_robots;
    std::vector<std::vector<int>> m_cells;

    int m_width;
    int m_height;

    void initRobots();

    void initCells();

    void moveRobots(int seconds);

    [[nodiscard]] int getSafetyFactor() const;

    void toJson() const;
};
