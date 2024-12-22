//
// Created by dgern on 17.12.2024.
//

#include <iostream>
#include <regex>
#include <unordered_set>

#include "Day17.h"
#include "../util/Util.h"

Day17::Day17() : BaseDay{"day17.txt"} {
    initComputer();
}

void Day17::solvePartOne() {
    auto computer = m_computer;
    computer.runProgram();

    std::cout << "Program output: " << computer.getOutput() << "\n";
}

void Day17::solvePartTwo() {
    auto computer = m_computer;
    std::unordered_set<State, StateHash> knownStates{};

//    for (size_t i = 0; i < 1'000'000'000'000; ++i) {
//        computer.m_regA = i;
//        computer.m_regB = 0;
//        computer.m_regC = 0;
//        computer.m_output.clear();
//        computer.m_pointer = 0;
//        auto found = computer.runProgram2(knownStates);
//
//        if (found) {
//            std::cout << "Value of register A: " << i << "\n";
//            return;
//        }
//    }

    std::cout << "Noting found :(" << "\n";
}

void Day17::initComputer() {
    std::regex numPattern{"\\d+"};
    auto regA = std::stoull((*std::sregex_iterator(m_inputLines[0].begin(), m_inputLines[0].end(), numPattern)).str());
    auto regB = std::stoull((*std::sregex_iterator(m_inputLines[1].begin(), m_inputLines[1].end(), numPattern)).str());
    auto regC = std::stoull((*std::sregex_iterator(m_inputLines[2].begin(), m_inputLines[2].end(), numPattern)).str());

    auto spaceIndex = m_inputLines[4].find(' ');
    auto program = split<uint64_t>(m_inputLines[4].substr(spaceIndex + 1), ',');

    m_computer = Computer(program, regA, regB, regC);
}

void Day17::Computer::runProgram() {
    while (m_pointer < m_program.size()) {
        switch (m_program[m_pointer]) {
            case 0:
                adv();
                break;
            case 1:
                bxl();
                break;
            case 2:
                bst();
                break;
            case 3:
                jnz();
                break;
            case 4:
                bxc();
                break;
            case 5:
                out();
                break;
            case 6:
                bdv();
                break;
            case 7:
                cdv();
                break;

        }

        m_pointer += 2;
    }
}

bool Day17::Computer::runProgram2(std::unordered_set<State, StateHash>& knownStates) {
    while (m_pointer < m_program.size()) {
        State s{m_regA, m_regB, m_regC, m_pointer};

        if (knownStates.contains(s))
            return false;

        auto instruction = m_program[m_pointer];
        if (instruction == 0)
            adv();
        else if (instruction == 1)
            bxl();
        else if (instruction == 2)
            bst();
        else if (instruction == 3)
            jnz();
        else if (instruction == 4)
            bxc();
        else if (instruction == 5) {
            out();
            auto idx = m_output.size() - 1;

            if (m_output.size() > m_program.size() || m_output[idx] != m_program[idx])
                return false;

        } else if (instruction == 6)
            bdv();

        else if (instruction == 7)
            cdv();

        m_pointer += 2;
        knownStates.insert(s);
    }

    return m_output == m_program;
}


uint64_t Day17::Computer::evalComboValue() const {
    auto comboVal = m_program[m_pointer + 1];

    if (comboVal < 4)
        return comboVal;
    else if (comboVal == 4)
        return m_regA;
    else if (comboVal == 5)
        return m_regB;
    else if (comboVal == 6)
        return m_regC;
    else if (comboVal == 7) {
        std::cout << "COMBO VAL 7 NOT SUPPORTED" << "\n";
        exit(420);
    }

    std::cout << "HOW DID WE GET HERE? 0_o" << "\n";
    exit(421);
}

uint64_t Day17::Computer::getLiteral() const {
    return m_program[m_pointer + 1];
}

void Day17::Computer::adv() {
    auto res = static_cast<uint64_t> (m_regA / std::pow(2, evalComboValue()));
    m_regA = res;
}

void Day17::Computer::bxl() {
    auto res = m_regB ^ getLiteral();
    m_regB = res;
}

void Day17::Computer::bst() {
    auto res = (evalComboValue() % 8) & 0b111ull;
    m_regB = res;
}

void Day17::Computer::jnz() {
    if (m_regA == 0)
        return;

    m_pointer = static_cast<int>(getLiteral());
    m_pointer -= 2;
}

void Day17::Computer::bxc() {
    auto res = m_regB ^ m_regC;
    m_regB = res;
}

void Day17::Computer::out() {
    m_output.push_back(evalComboValue() % 8);
}

void Day17::Computer::bdv() {
    auto res = static_cast<uint64_t> (m_regA / std::pow(2, evalComboValue()));
    m_regB = res;
}

void Day17::Computer::cdv() {
    auto res = static_cast<uint64_t> (m_regA / std::pow(2, evalComboValue()));
    m_regC = res;
}

std::string Day17::Computer::getOutput() const {
    std::string outStr;

    for (const auto& item: m_output) {
        outStr += std::to_string(item) + ",";
    }

    if (!outStr.empty())
        outStr.pop_back();

    return outStr;
}
