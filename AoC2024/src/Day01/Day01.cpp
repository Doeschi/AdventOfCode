//
// Created by dgern on 01.12.2024.
//
#include <iostream>
#include <algorithm>

#include "Day01.h"
#include "../util/Util.h"

Day01::Day01() : BaseDay{"day01.txt"} {}

void Day01::solvePartOne() {
    auto const lists = getLists();
    int totalDistance{0};

    for (int i = 0; i < lists.nbrOfLocations(); ++i) {
        totalDistance += std::abs(lists.first.at(i) - lists.second.at(i));
    }

    std::cout << "Total distance: " << totalDistance << std::endl;
}

void Day01::solvePartTwo() {
    auto const lists = getLists();
    int similarityScore{0};

    for (const auto& firstLocationId: lists.first) {
        int nbrOfAppearances{0};

        for (const auto& secondLocationId: lists.second) {
            if (firstLocationId == secondLocationId)
                nbrOfAppearances++;
            else if (firstLocationId < secondLocationId)
                break;
        }

        similarityScore += firstLocationId * nbrOfAppearances;
    }

    std::cout << "Similarity score: " << similarityScore << std::endl;
}

Day01::LocationLists Day01::getLists() const {
    LocationLists lists;

    for (auto& line: m_inputLines) {
        auto const splitted = split<std::string>(line, ',');

        if (splitted.size() != 2) {
            std::cerr << "Two locations IDs per line are required, but " << splitted.size() << " were given\n";
            exit(420);
        }

        try {
            int const first = std::stoi(splitted.at(0));
            int const second = std::stoi(splitted.at(1));

            lists.first.push_back(first);
            lists.second.push_back(second);
        } catch (const std::logic_error& e) {
            std::cerr << "Error while converting to int\n";
            exit(420);
        }
    }

    std::sort(lists.first.begin(), lists.first.end());
    std::sort(lists.second.begin(), lists.second.end());

    return lists;
}