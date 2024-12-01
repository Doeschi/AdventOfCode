//
// Created by dgern on 01.12.2024.
//

#pragma once

#include <string>
#include <vector>

class BaseDay {
public:
    explicit BaseDay(std::string filepath);

    ~BaseDay() = default;

    virtual void solvePartOne() const = 0;
    virtual void solvePartTwo() const = 0;

protected:
    std::vector<std::string> m_inputLines;
};
