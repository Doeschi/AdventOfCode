//
// Created by dgern on 10.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"
#include "../util/Util.h"

class Day10 : public BaseDay {
public:
    Day10();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Map = std::vector<std::vector<int>>;

    Map m_map{};
    int m_width{};
    int m_height{};

    void initMap();

    [[nodiscard]] std::vector<Position> getPeaks(Position pos) const;
};