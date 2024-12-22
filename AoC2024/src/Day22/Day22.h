//
// Created by dgern on 22.12.2024.
//

#pragma once

#include <array>
#include <unordered_map>

#include "../BaseDay/BaseDay.h"

class Day22 : public BaseDay {
public:
    Day22();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Sequence = std::array<char, 4>;
    using Secrets = std::array<int64_t , 2001>;

    struct SequenceHash {
        std::size_t operator()(const Sequence& s) const {
            std::size_t h1 = std::hash<char>()(s[0]);
            std::size_t h2 = std::hash<char>()(s[1]);
            std::size_t h3 = std::hash<char>()(s[2]);
            std::size_t h4 = std::hash<char>()(s[3]);

            return h1 ^ (h2 << 1) ^ (h3 << 2) ^ (h4 << 3);
        }
    };

    std::vector<Secrets> m_secrets;

    void initSecretNumbers();
    int64_t getNext(int64_t num) const;
};