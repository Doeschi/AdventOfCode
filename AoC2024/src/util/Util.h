//
// Created by dgern on 01.12.2024.
//
#pragma once

#include <string>
#include <vector>
#include <array>

template<typename T>
std::vector<T> split(const std::string& str, char delimiter);

template<>
std::vector<std::string> split<std::string>(const std::string& str, char delimiter);

template<>
std::vector<int> split<int>(const std::string& str, char delimiter);

template<>
std::vector<long long> split<long long>(const std::string& str, char delimiter);

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

static constexpr std::array<Position, 4> directNeighborOffsets{Position{-1, 0},
                                                               Position{0, -1},
                                                               Position{1, 0},
                                                               Position{0, 1}};


static constexpr std::array<Position, 8> allNeighborOffsets{Position{-1, -1},
                                                            Position{0, -1},
                                                            Position{1, -1},
                                                            Position{-1, 0},
                                                            Position{1, 0},
                                                            Position{-1, 1},
                                                            Position{0, 1},
                                                            Position{1, 1}};