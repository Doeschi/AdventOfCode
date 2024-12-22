#include <iostream>
#include <memory>
#include <vector>
#include <format>
#include <chrono>

#include "src/BaseDay/BaseDay.h"
#include "src/Day01/Day01.h"
#include "src/Day02/Day02.h"
#include "src/Day03/Day03.h"
#include "src/Day04/Day04.h"
#include "src/Day05/Day05.h"
#include "src/Day06/Day06.h"
#include "src/Day07/Day07.h"
#include "src/Day08/Day08.h"
#include "src/Day09/Day09.h"
#include "src/Day10/Day10.h"
#include "src/Day11/Day11.h"
#include "src/Day12/Day12.h"
#include "src/Day13/Day13.h"
#include "src/Day14/Day14.h"
#include "src/Day15/Day15.h"
#include "src/Day16/Day16.h"
#include "src/Day17/Day17.h"
#include "src/Day18/Day18.h"
#include "src/Day19/Day19.h"
#include "src/Day20/Day20.h"
#include "src/Day21/Day21.h"
#include "src/Day22/Day22.h"

std::string getDurationText(const std::chrono::time_point<std::chrono::steady_clock>& start,
                            const std::chrono::time_point<std::chrono::steady_clock>& end) {
    auto duration = end - start;

    if (duration.count() >= 1'000'000)
        return std::format("{} ms", std::chrono::duration_cast<std::chrono::milliseconds>(duration).count());
    else
        return std::format("{} µs", std::chrono::duration_cast<std::chrono::microseconds>(duration).count());
}

void runDay(BaseDay& day) {
    auto startPartOne = std::chrono::high_resolution_clock::now();
    day.solvePartOne();
    auto endPartOne = std::chrono::high_resolution_clock::now();

    auto startPartTwo = std::chrono::high_resolution_clock::now();
    day.solvePartTwo();
    auto endPartTwo = std::chrono::high_resolution_clock::now();

    std::cout << "Duration part one: " << getDurationText(startPartOne, endPartOne) << "\n";
    std::cout << "Duration part two: " << getDurationText(startPartTwo, endPartTwo) << "\n";
}

void runAllDays() {
    std::vector<std::shared_ptr<BaseDay>> allDays;

    allDays.push_back(std::make_shared<Day01>());
    allDays.push_back(std::make_shared<Day02>());
    allDays.push_back(std::make_shared<Day03>());
    allDays.push_back(std::make_shared<Day04>());
    allDays.push_back(std::make_shared<Day05>());
    allDays.push_back(std::make_shared<Day06>());
    allDays.push_back(std::make_shared<Day07>());
    allDays.push_back(std::make_shared<Day08>());
    allDays.push_back(std::make_shared<Day09>());
    allDays.push_back(std::make_shared<Day10>());
    allDays.push_back(std::make_shared<Day11>());
    allDays.push_back(std::make_shared<Day12>());
    allDays.push_back(std::make_shared<Day13>());
    allDays.push_back(std::make_shared<Day14>());
    allDays.push_back(std::make_shared<Day15>());
    allDays.push_back(std::make_shared<Day16>());
    allDays.push_back(std::make_shared<Day17>());
    allDays.push_back(std::make_shared<Day18>());
    allDays.push_back(std::make_shared<Day19>());
    allDays.push_back(std::make_shared<Day20>());

    auto start = std::chrono::high_resolution_clock::now();
    for (auto i{0}; i < allDays.size(); ++i) {
        std::cout << "--------------- DAY " << std::format("{:2}", i + 1) << " ---------------\n";

        runDay(*allDays[i]);

        // new line to split days
        std::cout << "\n";
    }
    auto end = std::chrono::high_resolution_clock::now();

    std::cout << "--------------------------------------\n";
    std::cout << "It took " << getDurationText(start, end) << " to solve " << std::to_string(allDays.size())
              << "/25 days\n";
}

int main() {
    auto d = std::make_shared<Day22>();
    runDay(*d);
//    runAllDays();
}