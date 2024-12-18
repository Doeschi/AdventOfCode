//
// Created by dgern on 15.12.2024.
//

#include <iostream>
#include <regex>

#include "Day07.h"

Day07::Day07() : BaseDay{"day07.txt"} {
    initPrograms();
}

void Day07::solvePartOne() {
    for (const auto& program: m_programs){
        for (const auto& otherProgram: m_programs){
            for (const auto& aboveProgram: otherProgram.abovePrograms){
                if (program.name == aboveProgram){
                    goto checkNext;
                }
            }
        }

        std::cout << "Bottom program: " << program.name << "\n";
        break;

        checkNext:;
    }
}

void Day07::solvePartTwo() {

}

void Day07::initPrograms() {
    std::regex weightPattern{"(\\d+)"};
    std::regex namePattern{"([a-z]+)"};

    for (const auto& inputLine: m_inputLines) {
        Program program{};

        std::smatch weightMatch;
        std::regex_search(inputLine, weightMatch, weightPattern);
        program.weight = std::stoi(weightMatch[0]);

        std::sregex_iterator it(inputLine.begin(), inputLine.end(), namePattern);
        std::sregex_iterator end;

        program.name = it->str();
        ++it;

        while(it != end) {
            program.abovePrograms.push_back(it->str());
            ++it;
        }
        m_programs.push_back(program);
    }
}


