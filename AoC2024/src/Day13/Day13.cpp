//
// Created by dgern on 13.12.2024.
//
#include <iostream>
#include <regex>
#include <string>

#include "Day13.h"

using namespace std::string_literals;

Day13::Day13() : BaseDay{"day13.txt"} {
    initClawMachines();
}

void Day13::solvePartOne() {
    auto tokens = 0;

    for (const auto& machine: m_clawMachines) {
        for (int aMultiplier = 1; aMultiplier < 101; ++aMultiplier) {
            for (int bMultiplier = 1; bMultiplier < 101; ++bMultiplier) {
                auto possibleX = machine.ax * aMultiplier + machine.bx * bMultiplier;
                auto possibleY = machine.ay * aMultiplier + machine.by * bMultiplier;

                if (possibleX == machine.prizeX && possibleY == machine.prizeY) {
                    tokens += aMultiplier * 3 + bMultiplier;
                }
            }
        }
    }

    std::cout << "Number of tokens: " << tokens << "\n";
}

void Day13::solvePartTwo() {

}

void Day13::initClawMachines() {
    ClawMachine machine{};
    std::regex numberRegex("\\d+");

    for (auto i{0}; const auto& line: m_inputLines) {
        // blank line
        if (i % 4 == 3) {
            ++i;
            continue;
        }

        std::sregex_iterator begin(line.begin(), line.end(), numberRegex);
        std::sregex_iterator end;

        int j = 0;
        for (auto it = begin; it != end; ++it) {
            std::smatch match = *it;
            if (i % 4 == 0) {
                if (j == 0) {
                    machine.ax = std::stoi(match.str());
                } else {
                    machine.ay = std::stoi(match.str());
                }
            } else if (i % 4 == 1) {
                if (j == 0) {
                    machine.bx = std::stoi(match.str());
                } else {
                    machine.by = std::stoi(match.str());
                }
            } else if (i % 4 == 2) {
                if (j == 0) {
                    machine.prizeX = std::stoi(match.str());
                } else {
                    machine.prizeY = std::stoi(match.str());
                }
            }
            ++j;
        }

        if (i % 4 == 2)
            m_clawMachines.push_back(machine);

        ++i;
    }
}
