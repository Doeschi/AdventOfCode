
#include "src/BaseDay/BaseDay.h"
#include "src/Day01/Day01.h"

//
//void runAllDays() {
//    std::vector<std::unique_ptr<BaseDay>> allDays = { std::make_unique<Day01>() };
//
//    for (const auto& [name, day]: allDays) {
//        std::cout << "---------- " << name << " ----------" << std::endl;
//
//        day.solvePartOne();
//        day.solvePartTwo();
//    }
//}
#include <iostream>
#include <memory>
#include <vector>
#include <utility>

class Base {
public:
    virtual void print() const = 0;

    virtual ~Base() = default;
};

class Derived : public Base {
    void print() const override {
        std::cout << "TEST" << std::endl;
    }
};

int main() {
    // create vector with raw pointers
    std::vector<Base*> vec1{new Derived()};

    std::vector<std::unique_ptr<Base>> vec2{ std::make_unique<Derived>() };
    vec2.push_back(std::make_unique<Derived>());

    // print
    for (const auto& item: vec1) {
        item->print();
    }

    for (const auto& item: vec2) {
        item->print();
    }

    // delete objects
    for (const auto& item: vec1) {
        delete item;
    }
}
