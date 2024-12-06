//
// Created by dgern on 03.12.2024.
//

#include <iostream>

#include "Day03.h"

Day03::Day03() : BaseDay("day03.txt") {}

void Day03::solvePartOne() {
    std::cout << "Sum of mul instructions: " << parseInput(false) << std::endl;
}

void Day03::solvePartTwo() {
    std::cout << "Sum of mul instructions, respecting do and dont: " << parseInput(true) << std::endl;
}

int Day03::parseInput(const bool parseDoAndDont) const {
    MulParser parser{parseDoAndDont};

    for (const auto& instructions: m_inputLines) {
        parser.parseInstructions(instructions);
    }

    return parser.getResult();
}

void Day03::MulParser::parseInstructions(std::string const& instructions) {
    m_instructions = instructions;
    m_offset = 0;

    while (m_offset < m_instructions.size()) {
        switch (getCurrentC()) {
            case 'm':
                parseMul();
                break;
            case 'd':
                if (m_parseDoAndDont) {
                    auto saveOffset = m_offset;

                    if (pareDo())
                        continue;

                    m_offset = saveOffset;
                    pareDont();
                } else
                    ++m_offset;
                break;
            default:
                ++m_offset;
                break;
        }
    }
}

void Day03::MulParser::parseMul() {
    if (!parseString("mul("))
        return;

    auto firstNumber = parseFirstNumber();
    if (firstNumber == m_invalidNumber)
        return;

    auto secondNumber = parseSecondNumber();
    if (secondNumber == m_invalidNumber)
        return;

    if (m_doMul)
        m_result += firstNumber * secondNumber;
}

int Day03::MulParser::parseNumber() {
    std::string s{};

    for (int i{0}; i < 3; ++i) {
        auto c = getCurrentC();

        if (isdigit(c))
            s += c;
        else
            break;

        ++m_offset;
    }

    if (!s.empty())
        return std::stoi(s);
    else
        return m_invalidNumber;
}

int Day03::MulParser::parseFirstNumber() {
    auto num = parseNumber();
    auto comma = getCurrentC();

    if (comma != ',')
        return m_invalidNumber;

    ++m_offset;
    return num;
}

int Day03::MulParser::parseSecondNumber() {
    auto num = parseNumber();

    auto closingBracket = getCurrentC();

    if (closingBracket != ')')
        return m_invalidNumber;

    ++m_offset;
    return num;
}

bool Day03::MulParser::parseString(const std::string& s) {
    for (const auto& c: s) {
        if (getCurrentC() == c)
            ++m_offset;
        else
            return false;
    }
    return true;
}

char Day03::MulParser::getCurrentC() {
    return m_instructions[m_offset];
}

bool Day03::MulParser::pareDo() {
    if (parseString("do()")) {
        m_doMul = true;
        return true;
    }

    return false;
}

bool Day03::MulParser::pareDont() {
    if (parseString("don't()")) {
        m_doMul = false;
        return true;
    }

    return false;
}

int Day03::MulParser::getResult() const {
    return m_result;
}
