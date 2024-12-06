//
// Created by dgern on 05.12.2024.
//

#pragma once

#include <unordered_set>

#include "../BaseDay/BaseDay.h"

class Day05 : public BaseDay {
public:
    Day05();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    struct Rule {
        int before;
        int after;

        bool operator==(const Rule& other) const {
            return before == other.before && after == other.after;
        }
    };

    struct RuleHash {
        std::size_t operator()(Rule const& v) const {
            std::size_t h1 = std::hash<int>{}(v.before);
            std::size_t h2 = std::hash<int>{}(v.after);

            // Combine hashes
            return h1 ^ (h2 << 1);
        }
    };

    struct Update {
        std::vector<int> pages;
    };

    std::unordered_set<Rule, RuleHash> m_rules;
    std::vector<Update> m_updates;

    void initInput();

    [[nodiscard]] bool isCorrect(const Update& update) const;

    void reorderUpdate(Update& update) const;
};