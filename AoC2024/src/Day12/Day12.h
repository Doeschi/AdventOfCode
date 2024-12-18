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
    struct EdgePiece {
        Vector2D inner{};
        Vector2D outer{};
        std::shared_ptr<int> identifier{std::make_shared<int>(invalidIdentifier)}; // not part of '==' or hash

        static constexpr int invalidIdentifier = -1;

        bool operator==(const EdgePiece& other) const {
            return inner == other.inner && outer == other.outer;
        };
    };

    struct EdgePieceHash {
        std::size_t operator()(const EdgePiece& e) const {
            std::size_t h1 = std::hash<int>()(e.inner.x);
            std::size_t h2 = std::hash<int>()(e.inner.y);
            std::size_t h3 = std::hash<int>()(e.outer.x);
            std::size_t h4 = std::hash<int>()(e.outer.y);

            return h1 ^ (h2 << 1) ^ (h3 << 2) ^ (h4 << 3);
        }
    };

    struct GardenPlots {
        std::vector<std::vector<char>> plots;
        size_t width;
        size_t height;
    };

    struct Region {
    public:
        std::unordered_set<Vector2D, Vector2DHash> plots;
        std::unordered_set<EdgePiece, EdgePieceHash> edgePieces;

        explicit Region(const GardenPlots& gardenPlots, Vector2D pos);

    private:
        void searchPlots(const Day12::GardenPlots& gardenPlots, Vector2D currentPlot);

        void matchEdges();
    };

    GardenPlots m_gardenPlots;
    std::vector<Region> m_regions;

    void initGardenPlots();

    void initRegions();
};