//
// Created by dgern on 15.12.2024.
//

#pragma once

#include <string>

#include "../BaseDay/BaseDay.h"
#include "../util/Util.h"

class Day15 : public BaseDay {
public:
    Day15();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Map = std::vector<std::vector<char>>;

    struct Warehouse {
        Map map{};
        Point2D robotPos{};

        void applyMoves(const std::string& moves);

        [[nodiscard]] bool isMoveable(const Point2D& offset);

        void move(const Point2D& offset);

        void move(const Point2D& pos, const Point2D& offset);

        [[nodiscard]] int getGpsSum() const;

        void expandMap();

        void printMap() const;
    };

    Warehouse m_initialWarehouse{};
    std::string m_moves{};

    void initWarehouse();

    static Point2D getMoveOffset(char move);
};