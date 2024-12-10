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

std::string getDurationText(const std::chrono::time_point<std::chrono::steady_clock>& start,
                            const std::chrono::time_point<std::chrono::steady_clock>& end) {

    auto duration = end - start;
    if (duration.count() >= 1'000'000)
        return std::to_string(std::chrono::duration_cast<std::chrono::milliseconds>(duration).count()) + "ms";
    else
        return std::to_string(std::chrono::duration_cast<std::chrono::microseconds>(duration).count()) + "µs";
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
    std::vector<std::unique_ptr<BaseDay>> allDays;

    allDays.push_back(std::make_unique<Day01>());
    allDays.push_back(std::make_unique<Day02>());
    allDays.push_back(std::make_unique<Day03>());
    allDays.push_back(std::make_unique<Day04>());
    allDays.push_back(std::make_unique<Day05>());
    allDays.push_back(std::make_unique<Day06>());
    allDays.push_back(std::make_unique<Day07>());
    allDays.push_back(std::make_unique<Day08>());
    allDays.push_back(std::make_unique<Day09>());
    allDays.push_back(std::make_unique<Day10>());

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
    auto d = std::make_shared<Day04>();
//    runDay(*d);
    runAllDays();
}
