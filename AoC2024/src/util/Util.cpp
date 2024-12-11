//
// Created by dgern on 01.12.2024.
//
#include <sstream>
#include <vector>
#include <string>

#include "Util.h"

template<typename T>
std::vector<T> split(const std::string& str, char delimiter) {
    static_assert(std::is_same_v<T, void>, "Unsupported type for split function.");
    return {};
}

template<>
std::vector<std::string> split<std::string>(const std::string& str, char delimiter) {
    std::vector<std::string> result;
    std::stringstream ss(str);
    std::string token;

    while (std::getline(ss, token, delimiter)) {
        result.push_back(token);
    }

    return result;
}

template<>
std::vector<int> split<int>(const std::string& str, char delimiter) {
    std::vector<int> result;
    std::stringstream ss(str);
    std::string token;

    while (std::getline(ss, token, delimiter)) {
        result.push_back(std::stoi(token));
    }

    return result;
}

template<>
std::vector<long long> split<long long>(const std::string& str, char delimiter) {
    std::vector<long long> result;
    std::stringstream ss(str);
    std::string token;

    while (std::getline(ss, token, delimiter)) {
        result.push_back(std::stoll(token));
    }

    return result;
}

void trim(std::string& str) {
    size_t start = 0;
    size_t end = str.size();

    // Find the first non-whitespace character
    while (start < end && std::isspace(str[start])) {
        ++start;
    }

    // Find the last non-whitespace character
    while (end > start && std::isspace(str[end - 1])) {
        --end;
    }

    str = str.substr(start, end - start);
}