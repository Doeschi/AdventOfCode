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

    static constexpr std::array<long long, 19> powerOfTen{
            1,
            10,
            100,
            1000,
            10000,
            100000,
            1000000,
            10000000,
            100000000,
            1000000000,
            10000000000,
            100000000000,
            1000000000000,
            10000000000000,
            100000000000000,
            1000000000000000,
            10000000000000000,
            100000000000000000,
            1000000000000000000,
    };

    std::vector<Equation> m_equations;

    void initEquations();

    [[nodiscard]] bool
    isSolvable(const Equation& equation, long long subResult, int index, const std::vector<char>& operators) const;

    [[nodiscard]] long long getCalibrationResult(const std::vector<char>& operators) const;

    [[nodiscard]] static int countDigits(long long number);
};