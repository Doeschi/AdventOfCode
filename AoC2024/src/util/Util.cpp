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
            std::cerr << "Error while converting to int" << std::endl;
            exit(420);
        }
    }

    return result;
}