//
// Created by dgern on 12.12.2024.
//

#pragma once

#include <vector>

#include "../BaseDay/BaseDay.h"
#include "../util/Util.h"

class Day12 : public BaseDay {
public:
    Day12();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    struct Edge {
        FPoint2D p1;
        FPoint2D p2;

        bool merge(const Edge& edge);
    };

    struct Region {
        std::unordered_set<Point2D, Point2DHash> plots;
        std::vector<Edge> edges;

        [[nodiscard]] int getPerimeter() const;

        void findEdges();

        void insertEdge(Edge edge);
    };

    size_t m_width;
    size_t m_height;
    std::vector<std::vector<char>> m_gardenPlots;
    std::vector<Region> m_regions;

    void initGardenPlots();

    void initRegions();

    void getPlotsOfRegion(const Point2D& currentPlot, std::unordered_set<Point2D, Point2DHash>& plots) const;
};