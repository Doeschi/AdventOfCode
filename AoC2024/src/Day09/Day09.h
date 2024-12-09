//
// Created by dgern on 09.12.2024.
//

#pragma once

#include <vector>

#include "../BaseDay/BaseDay.h"

class Day09 : public BaseDay {
public:
    Day09();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    using Filesystem = std::vector<int64_t>;
    Filesystem m_filesystem;

    void initFilesystem();

    static constexpr const auto freeSpace{-1};

    static int64_t calcChecksum(const Filesystem& filesystem);

    static void printFilesystem(const Filesystem& filesystem);
};