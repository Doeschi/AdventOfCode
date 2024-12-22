//
// Created by dgern on 22.12.2024.
//

#include <iostream>
#include <unordered_map>
#include <unordered_set>

#include "Day22.h"

Day22::Day22() : BaseDay("day22.txt") {
    initSecretNumbers();
}

void Day22::solvePartOne() {
    auto secretSum = 0ll;

    for (const auto& secretSequence: m_secrets)
        secretSum += secretSequence[2000];

    std::cout << "Secret sum: " << secretSum << "\n";
}

void Day22::solvePartTwo() {
    std::unordered_map<Sequence, int, SequenceHash> allSequences;

    for (const auto& secrets: m_secrets) {
        std::unordered_set<Sequence, SequenceHash> seen;

        for (int i = 0; i < secrets.size() - 4; ++i) {
            Sequence s{
                    static_cast<char>(secrets[i + 1] % 10 - secrets[i] % 10),
                    static_cast<char>(secrets[i + 2] % 10 - secrets[i + 1] % 10),
                    static_cast<char>(secrets[i + 3] % 10 - secrets[i + 2] % 10),
                    static_cast<char>(secrets[i + 4] % 10 - secrets[i + 3] % 10)
            };

            if (seen.contains(s))
                continue;

            allSequences[s] += secrets[i + 4] % 10;
            seen.insert(s);
        }
    }

    auto maxBananas = 0;

    for (auto& [_, bananas]: allSequences)
        if (bananas > maxBananas)
            maxBananas = bananas;

    std::cout << "Max bananas: " << maxBananas << "\n";
}

void Day22::initSecretNumbers() {
    for (const auto& inputLine: m_inputLines) {
        auto secret = std::stoll(inputLine);
        Secrets s{secret};

        for (int i = 0; i < 2000; ++i) {
            secret = getNext(secret);
            s[i + 1] = secret;
        }

        m_secrets.push_back(s);
    }
}

int64_t Day22::getNext(int64_t num) const {
    num = ((num * 64) ^ num) % 16777216;
    num = ((num / 32) ^ num) % 16777216;
    num = ((num * 2048) ^ num) % 16777216;

    return num;
}

