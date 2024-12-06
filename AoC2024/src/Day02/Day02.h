//
// Created by dgern on 02.12.2024.
//

#pragma once

#include <vector>

#include "../BaseDay/BaseDay.h"


class Day02 : public BaseDay {
public:
    Day02();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Levels = std::vector<int>;

    [[nodiscard]] static bool isSafe(Levels& levels) ;
};