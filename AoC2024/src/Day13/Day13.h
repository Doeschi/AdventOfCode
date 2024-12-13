//
// Created by dgern on 13.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"

class Day13 : public BaseDay {
public:
    Day13();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    struct ClawMachine {
        int ax, ay, bx, by, prizeX, prizeY;
    };

    std::vector<ClawMachine> m_clawMachines;

    void initClawMachines();
};