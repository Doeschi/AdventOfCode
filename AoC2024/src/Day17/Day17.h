//
// Created by dgern on 17.12.2024.
//

#pragma once

#include "../BaseDay/BaseDay.h"

class Day17 : public BaseDay {
public:
    Day17();

    void solvePartOne() override;

    void solvePartTwo() override;

    struct State {
        uint64_t regA{}, regB{}, regC{};
        int pointer{};

        bool operator==(const State& s) const = default;
    };

    struct StateHash {
        std::size_t operator()(const State& state) const {
            std::size_t h1 = std::hash<uint64_t>()(state.regA);
            std::size_t h2 = std::hash<uint64_t>()(state.regB);
            std::size_t h3 = std::hash<uint64_t>()(state.regC);
            std::size_t h4 = std::hash<int>()(state.pointer);

            return h1 ^ (h2 << 1) ^ (h3 << 2) ^ (h4 << 3);
        }
    };

private:


    struct Computer {
    public:
        uint64_t m_regA{}, m_regB{}, m_regC{};
        int m_pointer{};
        std::vector<uint64_t> m_program{};
        std::vector<uint64_t> m_output{};

        Computer() = default;

        explicit Computer(std::vector<uint64_t>& program, uint64_t regA = 0, uint64_t regB = 0, uint64_t regC = 0)
                : m_program{program},
                  m_regA{regA},
                  m_regB{regB},
                  m_regC{regC} {};

        void runProgram();

        bool runProgram2(std::unordered_set<State, StateHash>& knownStates);

        std::string getOutput() const;

    private:
        [[nodiscard]] uint64_t evalComboValue() const;

        [[nodiscard]] uint64_t getLiteral() const;

        void adv();

        void bxl();

        void bst();

        void jnz();

        void bxc();

        void out();

        void bdv();

        void cdv();
    };

    Computer m_computer;

    void initComputer();
};