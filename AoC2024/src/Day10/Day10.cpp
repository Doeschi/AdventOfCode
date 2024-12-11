//
// Created by dgern on 10.12.2024.
//

#include <iostream>
#include <unordered_set>

#include "Day10.h"

Day10::Day10() : BaseDay{"day10.txt"} {
    initMap();

    m_width = static_cast<int>(m_map[0].size());
    m_height = static_cast<int>(m_map.size());
}

void Day10::solvePartOne() {
    auto trailheadScore{0};

    for (int y = 0; y < m_map.size(); ++y) {
        for (int x = 0; x < m_map[y].size(); ++x) {
            if (m_map[y][x] == 0) {
                auto peaks = getPeaks(Position{x, y});
                std::unordered_set<Position, PositionHash> uniquePeaks;
                uniquePeaks.insert(peaks.begin(), peaks.end());

                trailheadScore += uniquePeaks.size();
            }
        }
    }

    std::cout << "Trailhead score: " << trailheadScore << "\n";
}

void Day10::solvePartTwo() {
    size_t trailheadRating{0};

    for (int y = 0; y < m_map.size(); ++y) {
        for (int x = 0; x < m_map[y].size(); ++x) {
            if (m_map[y][x] == 0) {
                auto peaks = getPeaks(Position{x, y});
                trailheadRating += peaks.size();
            }
        }
    }

    std::cout << "Trailhead rating: " << trailheadRating << "\n";
}

void Day10::initMap() {
    for (const auto& inputLine: m_inputLines) {
        std::vector<int> line{};
        for (const auto& c: inputLine) {
            line.push_back(c - '0');
        }
        m_map.push_back(line);
    }
}

std::vector<Position> Day10::getPeaks(Position pos) const {
    auto level = m_map[pos.y][pos.x];

    if (level == 9)
        return std::vector<Position>{pos};

    std::vector<Position> peaks{};
    for (const auto& offset: directNeighborOffsets) {
        auto x = pos.x + offset.x;
        auto y = pos.y + offset.y;

        if (x < 0 || x > m_width - 1 || y < 0 || y > m_height - 1)
            continue;

        if ((x != pos.x && y == pos.y) || (x == pos.x && y != pos.y)) {
            if (level + 1 == m_map[y][x]) {
                auto foundPeaks = getPeaks(Position{x, y});
                peaks.insert(peaks.end(), foundPeaks.begin(), foundPeaks.end());
            }
        }
    }

    return peaks;
}
