#include <iostream>
#include <memory>
#include <vector>
#include <format>

#include "src/BaseDay/BaseDay.h"
#include "src/Day01/Day01.h"
#include "src/Day02/Day02.h"

void runAllDays() {
    std::vector<std::unique_ptr<BaseDay>> allDays;

    allDays.push_back(std::make_unique<Day01>());
    allDays.push_back(std::make_unique<Day02>());

    for (auto i{0}; i < allDays.size(); ++i) {
        std::cout << "---------- DAY " << std::format("{:2}", i + 1) << " ----------" << std::endl;

        auto& day = allDays[i];
        day->solvePartOne();
        day->solvePartTwo();

        // new line to split days
        std::cout << std::endl;
    }
}

void runDay(std::unique_ptr<BaseDay> day) {
    std::cout << "Running " << typeid(*day).name() << std::endl;
    day->solvePartOne();
    day->solvePartTwo();
}

int main() {
    runDay(std::make_unique<Day02>());
    // runAllDays();
}
