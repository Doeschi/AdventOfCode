//
// Created by dgern on 15.12.2024.
//
#include <iostream>
#include <unordered_set>
#include <algorithm>

#include "Day04.h"
#include "../util/Util.h"

Day04::Day04() : BaseDay{"day04.txt"} {}

void Day04::solvePartOne() {
    auto nbrOfValidPassphrases = 0;

    for (const auto& passphrase: m_inputLines) {
        auto allWords = split<std::string>(passphrase, ' ');

        std::unordered_set<std::string> uniqueWords{};
        uniqueWords.insert(allWords.begin(), allWords.end());

        if (uniqueWords.size() == allWords.size())
            ++nbrOfValidPassphrases;
    }

    std::cout << "Number of valid passphrases: " << nbrOfValidPassphrases << "\n";
}

void Day04::solvePartTwo() {
    auto nbrOfValidPassphrases = 0;

    for (const auto& passphrase: m_inputLines) {
        auto allWords = split<std::string>(passphrase, ' ');

        for (auto& word: allWords)
            std::sort(word.begin(), word.end());

        std::unordered_set<std::string> uniqueWords{};
        uniqueWords.insert(allWords.begin(), allWords.end());

        if (uniqueWords.size() == allWords.size())
            ++nbrOfValidPassphrases;
    }

    std::cout << "Number of valid passphrases with additional policy: " << nbrOfValidPassphrases << "\n";
}
