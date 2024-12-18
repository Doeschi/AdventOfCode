//
// Created by dgern on 15.12.2024.
//

#pragma once

#include <array>

#include "../BaseDay/BaseDay.h"

class Day06 : public BaseDay {
public:
    Day06();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    static constexpr const int bankSize = 16;
    using Banks = std::array<int, bankSize>;

    struct BankConfig {
        Banks banks{};
        int cycle{};

        bool operator==(const BankConfig& other) const {
            for (int i = 0; i < bankSize; ++i) {
                if (banks[i] != other.banks[i])
                    return false;
            }
            return true;
        };
    };

    struct BankConfigHash {
        std::size_t operator()(const BankConfig& bankConfig) const {
            size_t hash{std::hash<int>()(bankConfig.banks[0])};

            for (int i = 1; i < bankSize; ++i) {
                hash ^= std::hash<int>()(bankConfig.banks[i]) << i;
            }

            return hash;
        }
    };

    BankConfig m_bankConfig{};

    int m_lastCycle{0};
    int m_loopLength{0};

    void initBankConfig();

    void findLoop();
};