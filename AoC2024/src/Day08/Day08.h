//
// Created by dgern on 08.12.2024.
//

#pragma once

#include <unordered_map>
#include <vector>

#include "../BaseDay/BaseDay.h"
#include "../util/Util.h"

class Day08 : public BaseDay {
public:
    Day08();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    std::unordered_map<char, std::vector<Position>> m_frequencies;

    int m_gridWidth;
    int m_gridHeight;
    int m_maxIterations;

    void initFrequencies();

    [[nodiscard]] std::unordered_set<Position, PositionHash>
    getAntiNodes(const Position& pos, int xDiff, int yDiff, int maxIterations) const;

    [[nodiscard]] std::unordered_set<Position, PositionHash>
    getAntiNodesFromAntennas(const std::vector<Position>& antennas, int maxIterations) const;
};