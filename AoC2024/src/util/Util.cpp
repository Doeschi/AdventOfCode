//
// Created by dgern on 01.12.2024.
//
#include <sstream>
#include <vector>
#include <string>

#include "Util.h"

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
std::vector<int64_t> split<int64_t>(const std::string& str, char delimiter) {
    std::vector<int64_t> result;
    std::stringstream ss(str);
    std::string token;

    while (std::getline(ss, token, delimiter)) {
        result.push_back(std::stoll(token));
    }

    return result;
}

template<>
std::vector<uint64_t> split<uint64_t>(const std::string& str, char delimiter) {
    std::vector<uint64_t> result;
    std::stringstream ss(str);
    std::string token;

    while (std::getline(ss, token, delimiter)) {
        result.push_back(std::stoull(token));
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