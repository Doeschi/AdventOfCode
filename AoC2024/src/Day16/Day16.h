//
// Created by dgern on 16.12.2024.
//

#pragma once

#include <unordered_set>

#include "../BaseDay/BaseDay.h"
#include "../util/Util.h"

class Day16 : public BaseDay {
public:
    Day16();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Maze = std::vector<std::vector<char>>;

    struct Tile {
        Vector2D pos{};
        Vector2D dir{};
        int scoreToTile{};
        int scoreToEnd{};

        bool operator==(const Tile& other) const {
            return pos.x == other.pos.x && pos.y == other.pos.y;
        };
    };

    struct TileHash {
        std::size_t operator()(const Tile& tile) const {
            std::size_t h1 = std::hash<int>()(tile.pos.x);
            std::size_t h2 = std::hash<int>()(tile.pos.y);

            return h1 ^ (h2 << 1);
        }
    };

    Maze m_maze{};
    std::unordered_set<Tile, TileHash> m_tileCache;
    Vector2D m_start{};
    Vector2D m_end{};

    void initMaze();

    [[nodiscard]] int getScoreToEnd(Tile& tile, std::unordered_set<Vector2D, Vector2DHash>& seen);

    void printMaze() const;

    static int getRotationDiff(Vector2D v1, Vector2D v2);
};