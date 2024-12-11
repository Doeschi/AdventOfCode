//
// Created by dgern on 11.12.2024.
//

#pragma once

#include <unordered_map>

#include "../BaseDay/BaseDay.h"

class Day11 : public BaseDay {
public:
    Day11();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    std::unordered_map<int64_t, int64_t> m_stones;

    [[nodiscard]] int64_t countStones(int numberOfBlinks) const;
};

