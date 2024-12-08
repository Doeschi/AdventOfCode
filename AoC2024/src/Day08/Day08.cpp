//
// Created by dgern on 08.12.2024.
//

#include <iostream>
#include <unordered_set>

#include "Day08.h"

Day08::Day08() : BaseDay{"day08.txt"} {
    m_gridWidth = static_cast<int>(m_inputLines[0].size());
    m_gridHeight = static_cast<int>(m_inputLines.size());
    m_maxIterations = std::max(m_gridWidth, m_gridHeight);

    initFrequencies();
}

void Day08::solvePartOne() {
    std::unordered_set<Position, PositionHash> uniqueLocations;

    for (const auto& [frequency, antennas]: m_frequencies) {
        auto antiNodes = getAntiNodesFromAntennas(antennas, 1);
        uniqueLocations.insert(antiNodes.cbegin(), antiNodes.cend());
    }

    std::cout << "Number of unique locations: " << uniqueLocations.size() << "\n";
}

void Day08::solvePartTwo() {
    std::unordered_set<Position, PositionHash> uniqueLocations;

    for (const auto& [frequency, antennas]: m_frequencies) {
        auto antiNodes = getAntiNodesFromAntennas(antennas, m_maxIterations);
        uniqueLocations.insert(antiNodes.cbegin(), antiNodes.cend());

        // all antennas are anti nodes to
        if (antennas.size() > 1)
            uniqueLocations.insert(antennas.cbegin(), antennas.cend());

    }

    std::cout << "Number of unique locations with part two: " << uniqueLocations.size() << "\n";
}

void Day08::initFrequencies() {
    for (auto y{0}; y < m_inputLines.size(); ++y) {
        auto& line = m_inputLines[y];

        for (auto x{0}; x < line.size(); ++x) {
            auto c = m_inputLines[y][x];

            if (c != '.')
                m_frequencies[c].push_back(Position{x, y});
        }
    }
}

std::unordered_set<Position, PositionHash>
Day08::getAntiNodesFromAntennas(const std::vector<Position>& antennas, const int maxIterations) const {
    std::unordered_set<Position, PositionHash> uniqueLocations;

    for (int i = 0; i < antennas.size() - 1; ++i) {
        auto& first = antennas[i];

        for (int j = i + 1; j < antennas.size(); ++j) {
            auto& second = antennas[j];

            auto xDiff = first.x - second.x;
            auto yDiff = first.y - second.y;

            auto firstAntiNodes = getAntiNodes(first, xDiff, yDiff, maxIterations);
            auto secondAntiNodes = getAntiNodes(second, xDiff * -1, yDiff * -1, maxIterations);

            uniqueLocations.insert(firstAntiNodes.cbegin(), firstAntiNodes.cend());
            uniqueLocations.insert(secondAntiNodes.cbegin(), secondAntiNodes.cend());
        }
    }

    return uniqueLocations;
}

std::unordered_set<Position, PositionHash>
Day08::getAntiNodes(const Position& pos, const int xDiff, const int yDiff, const int maxIterations) const {
    std::unordered_set<Position, PositionHash> positions;

    for (int i = 1; i < maxIterations + 1; ++i) {
        Position location{pos.x + (xDiff * i), pos.y + (yDiff * i)};

        if (location.x >= 0 && location.x < m_gridWidth && location.y >= 0 &&
            location.y < m_gridHeight)
            positions.insert(location);

        else
            break;
    }

    return positions;
}


