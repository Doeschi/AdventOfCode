//
// Created by dgern on 12.12.2024.
//
#include <iostream>
#include <unordered_set>

#include "Day12.h"

Day12::Day12() : BaseDay{"day12.txt"} {
    initGardenPlots();

    m_width = m_gardenPlots[0].size();
    m_height = m_gardenPlots.size();

    initRegions();
}

void Day12::solvePartOne() {
    size_t totalPrice{0};

    for (const auto& region: m_regions)
        totalPrice += region.plots.size() * region.getPerimeter();

    std::cout << "Total price: " << totalPrice << "\n";
}

void Day12::solvePartTwo() {
    size_t totalPrice{0};

    for (auto& region: m_regions) {
        region.findEdges();
        totalPrice += region.plots.size() * region.edges.size();
//        std::cout << "Edges: " << region.edges.size() << "\n";
    }

    std::cout << "Total price with bulk discount: " << totalPrice << "\n";
}

void Day12::initGardenPlots() {
    for (const auto& inputLine: m_inputLines) {
        std::vector<char> line;

        for (const auto& c: inputLine)
            line.push_back(c);

        m_gardenPlots.push_back(line);
    }
}


void Day12::initRegions() {
    std::unordered_set<Point2D, Point2DHash> visitedPlots;

    for (int y = 0; y < m_gardenPlots.size(); ++y) {
        for (int x = 0; x < m_gardenPlots[y].size(); ++x) {
            Point2D pos{x, y};

            if (visitedPlots.contains(pos))
                continue;

            Day12::Region region{};
            getPlotsOfRegion(pos, region.plots);
            visitedPlots.insert(region.plots.begin(), region.plots.end());
            m_regions.push_back(region);
        }
    }
}


void Day12::getPlotsOfRegion(const Point2D& currentPlot, std::unordered_set<Point2D, Point2DHash>& plots) const {
    if (plots.contains(currentPlot))
        return;

    plots.insert(currentPlot);

    for (const auto& offset: directNeighborOffsets) {
        Point2D neighbor{currentPlot.x + offset.x, currentPlot.y + offset.y};

        if (neighbor.x < 0 || neighbor.x > m_width - 1 || neighbor.y < 0 || neighbor.y > m_height - 1)
            continue;

        if (m_gardenPlots[neighbor.y][neighbor.x] == m_gardenPlots[currentPlot.y][currentPlot.x])
            getPlotsOfRegion(neighbor, plots);
    }
}

int Day12::Region::getPerimeter() const {
    auto perimeter{0};

    for (const auto& plot: plots) {
        for (const auto& offset: directNeighborOffsets) {
            Point2D neighbor{plot.x + offset.x, plot.y + offset.y};

            if (!plots.contains(neighbor))
                ++perimeter;
        }
    }

    return perimeter;
}

void Day12::Region::findEdges() {
    for (const auto& plot: plots) {
        for (const auto& offset: directNeighborOffsets) {
            Point2D neighbor{plot.x + offset.x, plot.y + offset.y};

            if (!plots.contains(neighbor)) {
                // some intuitive calculations
                float x1 = plot.x + (offset.y * 0.5f) + (offset.x * 0.25f);
                float x2 = plot.x - (offset.y * 0.5f) + (offset.x * 0.25f);
                float y1 = plot.y + (offset.x * 0.5f) + (offset.y * 0.25f);
                float y2 = plot.y - (offset.x * 0.5f) + (offset.y * 0.25f);

                FPoint2D p1{std::min(x1, x2), std::min(y1, y2)};
                FPoint2D p2{std::max(x1, x2), std::max(y1, y2)};

                Edge edge{p1, p2};
                insertEdge(edge);
            }
        }
    }

    // merge all found edges
    tryagain:
    for (int i = 0; i < edges.size() - 1; ++i) {
        for (int j = i + 1; j < edges.size(); ++j) {
            auto& e1 = edges[i];
            auto& e2 = edges[j];

            if (e1.merge(e2)) {
                edges.erase(edges.begin() + j);
                goto tryagain;
            }
        }
    }
}

void Day12::Region::insertEdge(Day12::Edge edge) {
    for (auto& existingEdge: edges) {
        if (existingEdge.merge(edge))
            return;
    }

    edges.push_back(edge);
}

bool floatEqual(float a, float b) {
    return std::fabs(a - b) < std::numeric_limits<float>::epsilon();
}

bool Day12::Edge::merge(const Day12::Edge& other) {
    // they share a point
    if (other.p1 == p2) {
        // they point into the same direction
        if (floatEqual(other.p2.x, p1.x) || floatEqual(other.p2.y, p1.y)) {
            p2.x = other.p2.x;
            p2.y = other.p2.y;
            return true;
        }
    } else if (other.p2 == p1) {
        if (floatEqual(other.p1.x, p2.x) || floatEqual(other.p1.y, p2.y)) {
            p1.x = other.p1.x;
            p1.y = other.p1.y;
            return true;
        }
    }

    return false;
}
