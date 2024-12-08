//
// Created by dgern on 01.12.2024.
//
#pragma once

#include <string>
#include <vector>

std::vector<std::string> splitString(const std::string& input, char delim);

std::vector<int> splitStringToInt(const std::string& input, char delim);

std::vector<long long> splitStringToLongLong(const std::string& input, char delim);

void trim(std::string& str);

struct Position {
    int x;
    int y;

    bool operator==(const Position& other) const = default;
};

struct PositionHash {
    std::size_t operator()(const Position& p) const {
        std::size_t h1 = std::hash<int>()(p.x);
        std::size_t h2 = std::hash<int>()(p.y);

        return h1 ^ (h2 << 1);
    }
};