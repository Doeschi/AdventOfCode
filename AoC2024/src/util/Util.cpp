//
// Created by dgern on 01.12.2024.
//
#include <sstream>
#include <vector>
#include <string>

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