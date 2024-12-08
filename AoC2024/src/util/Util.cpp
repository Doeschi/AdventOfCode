//
// Created by dgern on 01.12.2024.
//
#include <sstream>
#include <vector>
#include <string>
#include <iostream>

#include "Util.h"

std::vector<std::string> splitString(const std::string& input, const char delim) {
    std::vector<std::string> result;
    std::stringstream ss(input);
    std::string token;

    while (std::getline(ss, token, delim)) {
        result.push_back(token);
    }

    return result;
}

std::vector<int> splitStringToInt(const std::string& input, char const delim) {
    std::vector<int> result;
    std::stringstream ss(input);
    std::string token;

    while (std::getline(ss, token, delim)) {
        try {
            auto const intVal = std::stoi(token);
            result.push_back(intVal);
        } catch (const std::logic_error& e) {
            std::cerr << "Error while converting " << token << " to int: " << e.what() << std::endl;
            exit(420);
        }
    }

    return result;
}

std::vector<long long> splitStringToLongLong(const std::string& input, char delim) {
    std::vector<long long> result;
    std::stringstream ss(input);
    std::string token;

    while (std::getline(ss, token, delim)) {
        try {
            auto const val = std::stoll(token);
            result.push_back(val);
        } catch (const std::logic_error& e) {
            std::cerr << "Error while converting " << token << " to long long: " << e.what() << std::endl;
            exit(420);
        }
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
