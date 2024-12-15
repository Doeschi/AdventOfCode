//
// Created by dgern on 15.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"

class Day02: public BaseDay{
public:
    Day02();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    std::vector<std::vector<int>> m_spreadSheet;

    void initSpreadSheet();
};