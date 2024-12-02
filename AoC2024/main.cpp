#include <iostream>
#include <memory>
#include <vector>
#include <utility>
#include <format>


#include "src/BaseDay/BaseDay.h"
#include "src/Day01/Day01.h"
#include "src/Day02/Day02.h"


void runAllDays() {
    std::vector<std::unique_ptr<BaseDay>> allDays;
    allDays.push_back(std::make_unique<Day01>());

    for (auto i{0}; i < allDays.size(); ++i) {
        std::cout << "---------- DAY " << std::format("{:2}", i + 1) << " ----------" << std::endl;

        auto& day = allDays[i];
        day->solvePartOne();
        day->solvePartTwo();
    }
}

int main() {
    //runAllDays();
    Day02 day02{};

    day02.solvePartOne();
    day02.solvePartTwo();
}
