//
// Created by dgern on 01.12.2024.
//

#pragma once

#include <string>
#include <vector>

class BaseDay {
public:
    explicit BaseDay(std::string filepath);

    virtual ~BaseDay() = default;

    [[maybe_unused]] virtual void solvePartOne() = 0;
    [[maybe_unused]] virtual void solvePartTwo() = 0;

protected:
    std::vector<std::string> m_inputLines;
};
