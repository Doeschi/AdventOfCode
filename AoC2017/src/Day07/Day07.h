//
// Created by dgern on 15.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"

class Day07 : public BaseDay {
public:
    Day07();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    struct Program {
        std::string name;
        int weight;
        std::vector<std::string> abovePrograms;
    };

    std::vector<Program> m_programs;

    void initPrograms();
};