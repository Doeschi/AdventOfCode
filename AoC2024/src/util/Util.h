//
// Created by dgern on 01.12.2024.
//
#pragma once

#include <string>
#include <vector>
#include <array>


/* FUNCTIONS */
template<typename T>
std::vector<T> split(const std::string& str, char delimiter) {
    static_assert(std::is_same_v<T, void>, "Unsupported type for split function.");
    return {};
}

template<>
std::vector<std::string> split<std::string>(const std::string& str, char delimiter);

template<>
std::vector<int> split<int>(const std::string& str, char delimiter);

template<>
std::vector<int64_t> split<int64_t>(const std::string& str, char delimiter);

template<>
std::vector<uint64_t> split<uint64_t>(const std::string& str, char delimiter);

template<typename T>
int countUDecimalDigits(T number) {
    if (number <= 0)
        return 1;

    int count{0};

    while (number > 0) {
        number /= 10;
        ++count;
    }

    return count;
};

template<typename T>
int countDecimalDigits(T number) {
    if (number <= 0)
        return 1;

    number = std::abs(number);
    int count{0};

    while (number > 0) {
        number /= 10;
        ++count;
    }

    return count;
};

void trim(std::string& str);


/* TYPES AND CONSTANTS */
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

static constexpr std::array<int64_t, 19> powerOfTen{
        1,
        10,
        100,
        1000,
        10000,
        100000,
        1000000,
        10000000,
        100000000,
        1000000000,
        10000000000,
        100000000000,
        1000000000000,
        10000000000000,
        100000000000000,
        1000000000000000,
        10000000000000000,
        100000000000000000,
        1000000000000000000,
};
