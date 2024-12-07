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

void runDay(std::unique_ptr<BaseDay> day) {
    auto startPartOne = std::chrono::high_resolution_clock::now();
    day->solvePartOne();
    auto endPartOne = std::chrono::high_resolution_clock::now();

    auto startPartTwo = std::chrono::high_resolution_clock::now();
    day->solvePartTwo();
    auto endPartTwo = std::chrono::high_resolution_clock::now();

    auto durationPartOne = std::chrono::duration_cast<std::chrono::milliseconds>(endPartOne - startPartOne);
    auto durationPartTwo = std::chrono::duration_cast<std::chrono::milliseconds>(endPartTwo - startPartTwo);

    std::cout << "Duration part one: " << durationPartOne.count() << "ms" << std::endl;
    std::cout << "Duration part two: " << durationPartTwo.count() << "ms" << std::endl;
}

void runAllDays() {
    std::vector<std::unique_ptr<BaseDay>> allDays;

    allDays.push_back(std::make_unique<Day01>());
    allDays.push_back(std::make_unique<Day02>());
    allDays.push_back(std::make_unique<Day03>());
    allDays.push_back(std::make_unique<Day04>());
    allDays.push_back(std::make_unique<Day05>());
//    allDays.push_back(std::make_unique<Day06>());
    allDays.push_back(std::make_unique<Day07>());

    for (auto i{0}; i < allDays.size(); ++i) {
        std::cout << "--------------- DAY " << std::format("{:2}", i + 1) << " ---------------" << std::endl;

        runDay(std::move(allDays[i]));

        // new line to split days
        std::cout << std::endl;
    }
}

int main() {
    runDay(std::make_unique<Day06>());
//     runAllDays();
}
