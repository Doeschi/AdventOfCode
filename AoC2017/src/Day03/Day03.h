//
// Created by dgern on 15.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"

class Day03 : public BaseDay {
public:
    Day03();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    int m_squareNumber{};

    static constexpr const int m_simulationSize = 500;
    static constexpr const int m_offset = m_simulationSize / 2;

};