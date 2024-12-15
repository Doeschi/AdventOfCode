//
// Created by dgern on 15.12.2024.
//

#include <iostream>
#include <deque>

#include "Day15.h"

Day15::Day15() : BaseDay{"day15.txt"} {
    initWarehouse();
}

void Day15::solvePartOne() {
    auto warehouse = m_initialWarehouse;
    warehouse.applyMoves(m_moves);

    std::cout << "GPS sum: " << warehouse.getGpsSum() << "\n";
}

void Day15::solvePartTwo() {
    auto warehouse = m_initialWarehouse;
    warehouse.expandMap();
    warehouse.applyMoves(m_moves);

    std::cout << "GPS sum in the expanded warehouse: " << warehouse.getGpsSum() << "\n";
}

void Day15::initWarehouse() {
    auto moveStart = 0;

    // map
    for (int y = 0; y < m_inputLines.size(); ++y) {
        const auto& inputrow = m_inputLines[y];

        if (inputrow.empty()) {
            moveStart = y + 1;
            break;
        }

        std::vector<char> row;
        for (int x = 0; x < inputrow.size(); ++x) {
            if (inputrow[x] == '@') {
                m_initialWarehouse.robotPos.x = x;
                m_initialWarehouse.robotPos.y = y;
            }

            row.push_back(inputrow[x]);
        }
        m_initialWarehouse.map.push_back(row);
    }

    // moves
    for (int i = moveStart; i < m_inputLines.size(); ++i) {
        m_moves += m_inputLines[i];
    }
}

Point2D Day15::getMoveOffset(char move) {
    switch (move) {
        case '^':
            return Point2D(0, -1);
        case '>':
            return Point2D(1, 0);
        case 'v':
            return Point2D(0, 1);
        case '<':
            return Point2D(-1, 0);
        default:
            std::cout << "UNKNOWN MOVE: " << move << "\n";
            exit(420);
    }
}

void Day15::Warehouse::applyMoves(const std::string& moves) {
    for (const auto& m: moves) {
        auto offset = getMoveOffset(m);

        if (isMoveable(offset))
            move(offset);
    }
}

bool Day15::Warehouse::isMoveable(const Point2D& offset) {
    std::deque<Point2D> checkPos;

    checkPos.push_back(robotPos + offset);

    while (!checkPos.empty()) {
        auto check = checkPos.front();
        checkPos.pop_front();

        auto tile = map[check.y][check.x];

        if (tile == '#') {
            return false;
        } else if (tile == 'O' || tile == '[' || tile == ']') {
            checkPos.push_back(check + offset);

            if (offset.y != 0 && tile == '[')
                checkPos.push_back(check + offset + Point2D{1, 0});
            else if (offset.y != 0 && tile == ']')
                checkPos.push_back(check + offset + Point2D{-1, 0});
        }
    }

    return true;
}

void Day15::Warehouse::move(const Point2D& offset) {
    move(robotPos, offset);
    robotPos += offset;
}

void Day15::Warehouse::move(const Point2D& pos, const Point2D& offset) {
    auto check = pos + offset;
    auto tile = map[check.y][check.x];

    if (tile == 'O' || tile == '[' || tile == ']') {
        move(check, offset);

        if (offset.y != 0 && tile == '[')
            move(check + Point2D{1, 0}, offset);
        else if (offset.y != 0 && tile == ']')
            move(check + Point2D{-1, 0}, offset);
    }

    map[check.y][check.x] = map[pos.y][pos.x];
    map[pos.y][pos.x] = '.';
}


int Day15::Warehouse::getGpsSum() const {
    auto gpsSum = 0;

    for (int y = 0; y < map.size(); ++y) {
        for (int x = 0; x < map[y].size(); ++x) {
            if (map[y][x] == 'O' || map[y][x] == '[')
                gpsSum += x + 100 * y;
        }
    }

    return gpsSum;
}

void Day15::Warehouse::expandMap() {
    for (int y = 0; y < map.size(); ++y) {
        auto& row = map[y];
        for (int x = 0; x < row.size(); ++x) {
            auto tile = row[x];

            if (tile == '#') {
                row.insert(row.begin() + x + 1, '#');
            } else if (tile == 'O') {
                row[x] = '[';
                row.insert(row.begin() + x + 1, ']');
            } else {
                row.insert(row.begin() + x + 1, '.');
            }
            ++x;
        }
    }

    robotPos.x *= 2;
}

void Day15::Warehouse::printMap() const {
    for (const auto& row: map) {
        for (const auto& c: row) {
            std::cout << c;
        }

        std::cout << "\n";
    }

    std::cout << "\n";
}

