//
// Created by dgern on 21.12.2024.
//

#pragma once

#include <unordered_map>

#include "../BaseDay/BaseDay.h"
#include "../util/Util.h"

class Day21 : public BaseDay {
public:
    Day21();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Pad = std::unordered_map<char, Vector2D>;
    static constexpr char EMPTY = 'E';

    inline static Pad NUMPAD{
            {'7',   Vector2D{0, 0}},
            {'8',   Vector2D{1, 0}},
            {'9',   Vector2D{2, 0}},
            {'4',   Vector2D{0, 1}},
            {'5',   Vector2D{1, 1}},
            {'6',   Vector2D{2, 1}},
            {'1',   Vector2D{0, 2}},
            {'2',   Vector2D{1, 2}},
            {'3',   Vector2D{2, 2}},
            {EMPTY, Vector2D{0, 3}},
            {'0',   Vector2D{1, 3}},
            {'A',   Vector2D{2, 3}},
    };

    inline static Pad DIRPAD{
            {EMPTY, Vector2D{0, 0}},
            {'^',   Vector2D{1, 0}},
            {'A',   Vector2D{2, 0}},
            {'<',   Vector2D{0, 1}},
            {'v',   Vector2D{1, 1}},
            {'>',   Vector2D{2, 1}},
    };

    std::vector<std::string> m_codes;

    void initCodes();

    std::string getPadMoves(const std::string code, Pad& pad);

    std::string getMoves(const Vector2D& from, const Vector2D& to, const std::string& currentMoves, Pad& pad);
};