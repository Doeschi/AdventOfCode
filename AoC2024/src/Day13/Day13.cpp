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
    auto tokens = 0ll;

    for (const auto& machine: m_clawMachines) {
        auto btnPresses = getButtonPresses(machine);

        if (btnPresses.btnA <= 100 && btnPresses.btnB <= 100)
            tokens += btnPresses.btnA * 3 + btnPresses.btnB;

    }

    std::cout << "Number of tokens: " << tokens << "\n";
}

void Day13::solvePartTwo() {
    auto tokens = 0ll;
    auto offset = 10000000000000;

    for (auto& machine: m_clawMachines) {
        machine.prizeX += offset;
        machine.prizeY += offset;

        auto btnPresses = getButtonPresses(machine);
        tokens += btnPresses.btnA * 3 + btnPresses.btnB;
    }

    std::cout << "Number of tokens after correction: " << tokens << "\n";
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

Day13::ButtonPresses Day13::getButtonPresses(const Day13::ClawMachine& machine) {
    auto det = machine.ax * machine.by - machine.bx * machine.ay;

    if (det == 0)
        return ButtonPresses{};

    auto detA = (machine.prizeX * machine.by - machine.bx * machine.prizeY);
    auto detB = (machine.ax * machine.prizeY - machine.prizeX * machine.ay);

    if (detA % det == 0 && detB % det == 0)
        return ButtonPresses{detA / det, detB / det};
    else
        return ButtonPresses{};
}
