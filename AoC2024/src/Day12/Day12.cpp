//
// Created by dgern on 12.12.2024.
//
#include <iostream>
#include <unordered_set>
#include <algorithm>

#include "Day12.h"

Day12::Day12() : BaseDay{"day12.txt"} {
    initGardenPlots();
    initRegions();
}

void Day12::solvePartOne() {
    size_t totalPrice{0};

    for (const auto& region: m_regions)
        totalPrice += region.plots.size() * region.edgePieces.size();

    std::cout << "Total price: " << totalPrice << "\n";
}

void Day12::solvePartTwo() {
    size_t totalPrice{0};

    for (auto& region: m_regions) {
        std::unordered_set<int> edgeIdentifiers;
        for (const auto& edgePiece: region.edgePieces)
            edgeIdentifiers.insert(*edgePiece.identifier);

        totalPrice += region.plots.size() * edgeIdentifiers.size();
    }

    std::cout << "Total price with bulk discount: " << totalPrice << "\n";
}

void Day12::initGardenPlots() {
    for (const auto& inputLine: m_inputLines) {
        std::vector<char> line;

        for (const auto& c: inputLine)
            line.push_back(c);

        m_gardenPlots.plots.push_back(line);
    }

    m_gardenPlots.width = m_gardenPlots.plots[0].size();
    m_gardenPlots.height = m_gardenPlots.plots.size();
}


void Day12::initRegions() {
    std::unordered_set<Point2D, Point2DHash> visitedPlots;

    for (int y = 0; y < m_gardenPlots.plots.size(); ++y) {
        for (int x = 0; x < m_gardenPlots.plots[y].size(); ++x) {
            Point2D pos{x, y};

            if (visitedPlots.contains(pos))
                continue;

            Day12::Region region{m_gardenPlots, pos};
            visitedPlots.insert(region.plots.begin(), region.plots.end());
            m_regions.push_back(region);
        }
    }
}

Day12::Region::Region(const Day12::GardenPlots& gardenPlots, const Point2D pos) {
    searchPlots(gardenPlots, pos);
    matchEdges();
}

void Day12::Region::searchPlots(const Day12::GardenPlots& gardenPlots, const Point2D currentPlot) {
    if (plots.contains(currentPlot))
        return;

    plots.insert(currentPlot);

    for (const auto& offset: directNeighborOffsets) {
        Point2D neighbor{currentPlot.x + offset.x, currentPlot.y + offset.y};

        if (neighbor.x < 0 || neighbor.x > gardenPlots.width - 1 || neighbor.y < 0 ||
            neighbor.y > gardenPlots.height - 1) {
            edgePieces.insert(EdgePiece{currentPlot, neighbor});
            continue;
        }

        if (gardenPlots.plots[neighbor.y][neighbor.x] == gardenPlots.plots[currentPlot.y][currentPlot.x])
            searchPlots(gardenPlots, neighbor);
        else
            edgePieces.insert(EdgePiece{currentPlot, neighbor});
    }
}

void Day12::Region::matchEdges() {
    auto identifier{0};

    for (const auto& edgePiece: edgePieces) {
        if (*edgePiece.identifier != EdgePiece::invalidIdentifier)
            continue;

        for (auto& innerOffset: directNeighborOffsets) {
            for (auto& outerOffset: directNeighborOffsets) {
                EdgePiece possibleEdge{edgePiece.inner + innerOffset, edgePiece.outer + outerOffset};

                auto otherPiece = edgePieces.find(possibleEdge);

                if (otherPiece != edgePieces.end()) {
                    if (*edgePiece.identifier == EdgePiece::invalidIdentifier &&
                        *otherPiece->identifier == EdgePiece::invalidIdentifier) {
                        *edgePiece.identifier = identifier;
                        ++identifier;
                    } else if (*edgePiece.identifier == EdgePiece::invalidIdentifier &&
                               *otherPiece->identifier != EdgePiece::invalidIdentifier) {
                        *edgePiece.identifier = *(*otherPiece).identifier;
                    } else if (*edgePiece.identifier != EdgePiece::invalidIdentifier &&
                               *otherPiece->identifier != EdgePiece::invalidIdentifier) {
                        auto minIdent = std::min(*edgePiece.identifier, *(*otherPiece).identifier);
                        auto maxIdent = std::max(*edgePiece.identifier, *(*otherPiece).identifier);

                        for (const auto& p: edgePieces) {
                            if (*p.identifier == maxIdent)
                                *p.identifier = minIdent;
                        }
                    }
                }
            }
        }

        if (*edgePiece.identifier == EdgePiece::invalidIdentifier) {
            *edgePiece.identifier = identifier;
            ++identifier;
        }
    }
}
