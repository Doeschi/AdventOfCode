//
// Created by dgern on 03.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"

class Day03 : public BaseDay {
public:
    Day03();

    void solvePartOne() const override;

    void solvePartTwo() const override;

private:
    [[nodiscard]] int parseInput(bool parseDoAndDont) const;

    struct MulParser {
    public:
        explicit MulParser(bool parseDoAndDont = false) : m_parseDoAndDont{parseDoAndDont} {};

        void parseInstructions(const std::string& instructions);

        [[nodiscard]] int getResult() const;

    private:
        static int const m_invalidNumber{1000};

        size_t m_offset{0};
        int m_result{0};
        bool m_doMul{true};
        bool m_parseDoAndDont;

        std::string_view m_instructions{};

        char getCurrentC();

        void parseMul();

        bool parseString(const std::string& s);

        bool pareDo();

        bool pareDont();

        int parseNumber();

        int parseFirstNumber();

        int parseSecondNumber();
    };
};