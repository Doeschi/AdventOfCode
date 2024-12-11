//
// Created by dgern on 11.12.2024.
//

#include "Day11.h"

#include <ranges>
#include <iostream>
#include <unordered_map>

#include "../util/Util.h"

Day11::Day11() : BaseDay{"day11.txt"} {
    auto stones = split<int64_t>(m_inputLines[0], ' ');

    for (const auto& number: stones)
        m_stones[number]++;
}

void Day11::solvePartOne() {
    std::cout << "Number of stones after 25 blinks: " << countStones(25) << "\n";
}

void Day11::solvePartTwo() {
    std::cout << "Number of stones after 75 blinks: " << countStones(75) << "\n";
}

int64_t Day11::countStones(const int numberOfBlinks) const {
    std::unordered_map<int64_t, int64_t> currentStones = m_stones;

    for (int i = 0; i < numberOfBlinks; ++i) {
        std::unordered_map<int64_t, int64_t> newStones;
        for (const auto& [number, amount]: currentStones) {
            if (number == 0) {
                newStones[number + 1] += amount;
            } else {
                auto digits = countDecimalDigits(number);

                if (digits % 2 == 0) {
                    auto first = number / powerOfTen[(digits / 2)];
                    auto second = number - (first * powerOfTen[(digits / 2)]);

                    newStones[first] += amount;
                    newStones[second] += amount;
                } else {
                    newStones[number * 2024] += amount;
                }
            }
        }

        currentStones = newStones;
    }

    auto nbrOfStones{0ll};

    for (const auto& [number, amount]: currentStones)
        nbrOfStones += amount;

    return nbrOfStones;
}
