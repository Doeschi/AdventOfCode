//
// Created by dgern on 07.12.2024.
//

#pragma once

#include <vector>
#include <array>

#include "../BaseDay/BaseDay.h"

class Day07 : public BaseDay {
public:
    Day07();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    struct Equation {
        long long result;
        std::vector<long long> values;
    };

    std::vector<Equation> m_equations;

    void initEquations();

    [[nodiscard]] bool
    isSolvable(const Equation& equation, long long subResult, int index, const std::vector<char>& operators) const;

    long long getCalibrationResul(const std::vector<char>& operators) const;
};