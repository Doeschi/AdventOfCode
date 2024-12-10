//
// Created by dgern on 09.12.2024.
//

#include <iostream>
#include <vector>

#include "Day09.h"

Day09::Day09() : BaseDay{"day09.txt"} {
    initFilesystem();
}

void Day09::solvePartOne() {
    auto filesystem{m_filesystem};

    auto start{0};
    auto end{filesystem.size() - 1};

    while (start < end) {
        if (filesystem[end] == freeSpace)
            --end;
        else if (filesystem[start] != freeSpace)
            ++start;
        else {
            auto tmp = filesystem[start];
            filesystem[start] = filesystem[end];
            filesystem[end] = tmp;

            ++start;
            --end;
        }
    }

    std::cout << "Checksum moving individual blocks: " << calcChecksum(filesystem) << '\n';
}

void Day09::solvePartTwo() {
    auto filesystem{m_filesystem};
    auto& diskMap = m_inputLines[0];

    auto start{0};
    auto end{filesystem.size() - 1};

    // loop through all the files (starting from the right) and try to move them
    for (int i = static_cast<int>(diskMap.size()) - 1; i >= 0; i -= 2) {
        auto id = i / 2;
        auto nbrOfNeededBlocks = diskMap[i] - '0';

        // find the last block of that file
        while (filesystem[end] != id)
            --end;

        auto nbrOfFoundBlocks{0};
        auto firstFree{true};

        // search free space
        for (int j = start; j < end; ++j) {
            if (filesystem[j] == freeSpace) {
                ++nbrOfFoundBlocks;

                if (firstFree){
                    start = j;
                    firstFree = false;
                }
            } else {
                nbrOfFoundBlocks = 0;
            }

            // we have found a space with enough free blocks
            if (nbrOfFoundBlocks == nbrOfNeededBlocks) {
                // move the blocks to the free space
                for (int k = 0; k < nbrOfFoundBlocks; ++k) {
                    filesystem[j - k] = id;
                    filesystem[end] = freeSpace;
                    --end;
                }

                break;
            }
        }
    }

    std::cout << "Checksum moving entire files: " << calcChecksum(filesystem) << '\n';
}

void Day09::initFilesystem() {
    auto& diskMap = m_inputLines[0];

    for (int i = 0; i < diskMap.size(); ++i) {
        auto id{0};

        if (i % 2 == 0)
            id = i / 2;
        else
            id = freeSpace;

        // kinda smart way to convert the char digit to its actual int value
        auto nbrOfBlocks = diskMap[i] - '0';

        for (int j = 0; j < nbrOfBlocks; ++j)
            m_filesystem.push_back(id);
    }
}

int64_t Day09::calcChecksum(const Day09::Filesystem& filesystem) {
    auto checksum{0ll};

    for (int i = 0; i < filesystem.size(); ++i) {
        if (filesystem[i] != freeSpace)
            checksum += i * filesystem[i];
    }

    return checksum;
}

void Day09::printFilesystem(const Day09::Filesystem& filesystem) {
    for (const auto& item: filesystem) {
        if (item == freeSpace)
            std::cout << '.';
        else
            std::cout << item;
    }

    std::cout << std::endl;
}
