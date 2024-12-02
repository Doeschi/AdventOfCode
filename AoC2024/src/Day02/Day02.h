//
// Created by dgern on 02.12.2024.
//

#pragma once

#include <vector>

#include "../BaseDay/BaseDay.h"


class Day02 : public BaseDay {
public:
    Day02();

    void solvePartOne() const override;

    void solvePartTwo() const override;

private:
    using Levels = std::vector<int>;

    [[nodiscard]] static bool isSafe(Levels& levels) ;
};