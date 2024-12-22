//
// Created by dgern on 21.12.2024.
//

#include <iostream>

#include "Day21.h"

Day21::Day21() : BaseDay("day21.txt") {
    initCodes();
}

void Day21::solvePartOne() {
    int complexitySum = 0;

    for (const auto& code: m_codes) {
        auto numPadMoves = getPadMoves(code, NUMPAD);
        auto firstDirMoves = getPadMoves(numPadMoves, DIRPAD);
        auto myMoves = getPadMoves(firstDirMoves, DIRPAD);

        complexitySum += myMoves.size() * std::stoi(code.substr(0, code.size() - 1));
    }

    std::cout << "Complexity sum: " << complexitySum << "\n";
}

void Day21::solvePartTwo() {

}

void Day21::initCodes() {
    m_codes.insert(m_codes.begin(), m_inputLines.begin(), m_inputLines.end());
}

std::string Day21::getPadMoves(const std::string code, Day21::Pad& pad) {
    std::string moves{};

    auto current = pad['A'];
    for (const auto& btn: code) {
        auto next = pad[btn];

        moves += getMoves(current, next, moves, pad);
        moves += 'A';

        current = next;
    }

    return moves;;
}

std::string Day21::getMoves(const Vector2D& from, const Vector2D& to, const std::string& currentMoves, Pad& pad) {
    std::string moves{};
    auto offset = to - from;

    char x;
    char y;

    if (offset.x < 0)
        x = '<';
    else
        x = '>';

    if (offset.y < 0)
        y = '^';
    else
        y = 'v';

    auto emptyField = pad[EMPTY];
    if (from.y == emptyField.y) {
        for (int i = 0; i < std::abs(offset.y); ++i)
            moves += y;

        for (int i = 0; i < std::abs(offset.x); ++i)
            moves += x;
    } else if (from.x == emptyField.x) {
        for (int i = 0; i < std::abs(offset.x); ++i)
            moves += x;

        for (int i = 0; i < std::abs(offset.y); ++i)
            moves += y;
    } else {
        if (currentMoves.empty()) {
            for (int i = 0; i < std::abs(offset.x); ++i)
                moves += x;

            for (int i = 0; i < std::abs(offset.y); ++i)
                moves += y;
        } else {
            if (currentMoves[currentMoves.size() - 1] == x) {
                for (int i = 0; i < std::abs(offset.x); ++i)
                    moves += x;

                for (int i = 0; i < std::abs(offset.y); ++i)
                    moves += y;
            } else if(currentMoves[currentMoves.size() - 1] == y) {
                for (int i = 0; i < std::abs(offset.y); ++i)
                    moves += y;

                for (int i = 0; i < std::abs(offset.x); ++i) {
                    moves += x;
                }
            } else {

            }
        }
    }

    return moves;
}
