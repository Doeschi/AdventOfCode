//
// Created by dgern on 07.12.2024.
//
#include <iostream>

#include "Day07.h"
#include "../util/Util.h"


Day07::Day07() : BaseDay{"day07.txt"} {
    initEquations();
}

void Day07::solvePartOne() {
    std::cout << "Total calibration result with + and *: " << getCalibrationResult({'+', '*'}) << std::endl;
}

void Day07::solvePartTwo() {
    std::cout << "Total calibration result with +, * and |: " << getCalibrationResult({'+', '*', '|'}) << std::endl;
}

void Day07::initEquations() {
    for (const auto& line: m_inputLines) {
        auto splitted = split<std::string>(line, ':');

        auto result = std::stoll(splitted[0]);

        auto valuesString = splitted[1];
        trim(valuesString);
        auto values = split<int64_t>(valuesString, ' ');

        m_equations.push_back(Equation{result, values});
    }
}

bool Day07::isSolvable(const Day07::Equation& equation, int64_t subResult, int index,
                       const std::vector<char>& operators) const {

    for (const auto& op: operators) {
        auto before = subResult;

        // apply operation
        if (op == '+') {
            subResult += equation.values[index];
        } else if (op == '*') {
            subResult *= equation.values[index];
        } else if (op == '|') {
            subResult *= powerOfTen[countDecimalDigits(equation.values[index])];
            subResult += equation.values[index];
        }

        // we got the correct result and all terms are used
        if (subResult == equation.result && index == equation.values.size() - 1) {
            return true;
        }

        // only make the recursive call if we are not yet at the last term
        // and the subResult has not exceeded the correct result yet
        if (index < equation.values.size() - 1 && subResult <= equation.result)
            if (isSolvable(equation, subResult, index + 1, operators))
                return true;

        // undo operation
        subResult = before;
    }

    return false;
}

int64_t Day07::getCalibrationResult(const std::vector<char>& operators) const {
    auto calibrationResul{0ll};

    for (const auto& equation: m_equations) {
        if (isSolvable(equation, equation.values[0], 1, operators))
            calibrationResul += equation.result;
    }

    return calibrationResul;
}


