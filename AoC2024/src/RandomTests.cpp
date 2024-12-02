//
// Created by dgern on 02.12.2024.
//
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

void testUniquePointer() {
    // create vector with raw pointers
    std::vector<Base*> vec1{new Derived()};

    std::vector<std::unique_ptr<Base>> vec2{ /*std::make_unique<Derived>()*/ };
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
