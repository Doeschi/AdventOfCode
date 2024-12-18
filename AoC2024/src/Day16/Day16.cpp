#pragma comment(linker, "/STACK:10000000000")

//
// Created by dgern on 16.12.2024.
//

#include <iostream>

#include "Day16.h"

static int hits = 0;
static int nohits = 0;

Day16::Day16() : BaseDay{"day16.txt"} {
    initMaze();
}

void Day16::solvePartOne() {
    std::unordered_set<Vector2D, Vector2DHash> seen{};
    Tile start{m_start, Vector2D(1, 0), 0, 0};
    auto minScore = getScoreToEnd(start, seen);

    std::cout << "Lowest score: " << minScore << "\n";
    std::cout << "Hits: " << hits << "\n";
    std::cout << "No Hits: " << nohits << "\n";
}

void Day16::solvePartTwo() {

}

void Day16::initMaze() {
    for (int y = 0; y < m_inputLines.size(); ++y) {
        std::vector<char> row;

        for (int x = 0; x < m_inputLines[y].size(); ++x) {
            auto& c = m_inputLines[y][x];

            if (c == 'S')
                m_start = Vector2D(x, y);

            if (c == 'E')
                m_end = Vector2D(x, y);

            row.push_back(c);
        }

        m_maze.push_back(row);
    }
}

int Day16::getScoreToEnd(Day16::Tile& tile, std::unordered_set<Vector2D, Vector2DHash>& seen) {
    if (m_maze[tile.pos.y][tile.pos.x] == 'E') {
        tile.scoreToEnd = 0;
        return tile.scoreToEnd;
    }

    auto cacheIt = m_tileCache.find(tile);

    if (cacheIt != m_tileCache.end()) {
        ++hits;
        auto cacheTile = *cacheIt;

        if (cacheTile.scoreToEnd == std::numeric_limits<int>::max()) {
            return cacheTile.scoreToEnd;
        }

        auto rotationDiff = getRotationDiff(tile.dir, cacheTile.dir);
        if (cacheTile.scoreToTile > tile.scoreToTile + rotationDiff)
            cacheTile.scoreToTile = tile.scoreToTile + rotationDiff;

        return cacheTile.scoreToEnd;
    } else {
        ++nohits;
    }

    int minScore = std::numeric_limits<int>::max() / 2;
    seen.insert(tile.pos);

    for (const auto& dir: directNeighborOffsets) {
        Tile checkTile{tile.pos + dir, dir, tile.scoreToTile + 1, 0};

        if (seen.contains(checkTile.pos))
            continue;

        if (m_maze[checkTile.pos.y][checkTile.pos.x] == '#')
            continue;

        if (checkTile.dir.x != tile.dir.x && checkTile.dir.y != tile.dir.y)
            checkTile.scoreToTile += 1000;

        auto score = getScoreToEnd(checkTile, seen);

        if (score < minScore)
            minScore = checkTile.scoreToTile - tile.scoreToTile + score;
    }

    tile.scoreToEnd = minScore;
    m_tileCache.insert(tile);

    seen.erase(tile.pos);
    return  minScore;
}

int Day16::getRotationDiff(Vector2D v1, Vector2D v2) {
    if (v1 == v2)
        return 0;
    else if (v1.x == v2.x || v1.y == v2.y)
        return 2000;
    else return 1000;
}
