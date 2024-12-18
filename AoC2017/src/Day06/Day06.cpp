//
// Created by dgern on 15.12.2024.
//

#include <iostream>
#include <unordered_set>

#include "Day06.h"
#include "../util/Util.h"

Day06::Day06() : BaseDay{"day06.txt"} {
    initBankConfig();
    findLoop();
}

void Day06::solvePartOne() {
    std::cout << "Cycles: " << m_lastCycle << "\n";
}

void Day06::solvePartTwo() {
    std::cout << "Loop length: " << m_loopLength << "\n";
}

void Day06::initBankConfig() {
    auto inputBanks = split<int>(m_inputLines[0], ' ');

    for (int i = 0; i < m_bankConfig.banks.size(); ++i)
        m_bankConfig.banks[i] = inputBanks[i];
}

void Day06::findLoop() {
    std::unordered_set<BankConfig, BankConfigHash> seenConfigs;
    seenConfigs.insert(m_bankConfig);

    while (true) {
        auto bankIdx = 0;
        auto maxBlocks = 0;

        // find bank with the highest number of blocks
        for (int i = 0; i < bankSize; ++i) {
            if (m_bankConfig.banks[i] > maxBlocks) {
                bankIdx = i;
                maxBlocks = m_bankConfig.banks[i];
            }
        }

        m_bankConfig.banks[bankIdx] = 0;

        // distribute blocks
        for (int i = 0; i < maxBlocks; ++i) {
            bankIdx = (bankIdx + 1) % bankSize;
            ++m_bankConfig.banks[bankIdx];
        }

        ++m_bankConfig.cycle;

        if (seenConfigs.contains(m_bankConfig)) {
            m_lastCycle = m_bankConfig.cycle;
            m_loopLength = m_bankConfig.cycle - (*seenConfigs.find(m_bankConfig)).cycle;
            break;
        }

        seenConfigs.insert(m_bankConfig);
    }
}
