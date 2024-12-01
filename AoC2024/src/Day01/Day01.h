//
// Created by dgern on 01.12.2024.
//
#pragma once

#include <vector>

#include "../BaseDay/BaseDay.h"

class Day01 : public BaseDay {
public:
    Day01();

    void solvePartOne() const override;

    void solvePartTwo() const override;

private:
    struct LocationLists {
        std::vector<int> first;
        std::vector<int> second;

        [[nodiscard]] size_t nbrOfLocations() const { return first.size(); };
    };

    [[nodiscard]] LocationLists getLists() const;
};
