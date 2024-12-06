//
// Created by dgern on 05.12.2024.
//

#include <stdexcept>
#include <iostream>

#include "../util/Util.h"
#include "Day05.h"

Day05::Day05() : BaseDay("day05.txt") {
    initInput();
}

void Day05::solvePartOne() {
    auto middlePageSum{0};

    for (const auto& update: m_updates) {
        if (isCorrect(update))
            middlePageSum += update.pages[update.pages.size() / 2];
    }

    std::cout << "Sum of middle pages: " << middlePageSum << std::endl;
}

void Day05::solvePartTwo() {
    auto middlePageSum{0};

    for (auto& update: m_updates) {
        if (isCorrect(update))
            continue;

        reorderUpdate(update);
        middlePageSum += update.pages[update.pages.size() / 2];
    }

    std::cout << "Sum of middle pages after reordering: " << middlePageSum << std::endl;
}

void Day05::initInput() {
    int index{0};

    for (const auto& line: m_inputLines) {
        ++index;

        if (line.empty())
            break;

        auto pages = splitStringToInt(line, '|');
        if (pages.size() != 2)
            throw std::runtime_error("Only two pages per line allowed");

        m_rules.insert(Rule{.before = pages[0], .after = pages[1]});
    }

    for (auto i{index}; i < m_inputLines.size(); ++i) {
        auto pages = splitStringToInt(m_inputLines[i], ',');
        m_updates.push_back(Update{.pages = pages});
    }
}

bool Day05::isCorrect(const Day05::Update& update) const {
    for (auto i{0}; i < update.pages.size() - 1; ++i) {
        for (auto j{i + 1}; j < update.pages.size(); ++j) {
            Rule rule{.before = update.pages[j], .after = update.pages[i]};

            if (m_rules.contains(rule))
                return false;
        }
    }

    return true;
}

void Day05::reorderUpdate(Day05::Update& update) const {
    start:
    while (true) {
        for (auto i{0}; i < update.pages.size() - 1; ++i) {
            for (auto j{i + 1}; j < update.pages.size(); ++j) {
                Rule rule{.before = update.pages[j], .after = update.pages[i]};

                if (m_rules.contains(rule)) {
                    auto tmp = update.pages[i];
                    update.pages[i] = update.pages[j];
                    update.pages[j] = tmp;

                    goto start;
                }
            }
        }

        return;
    }
}
