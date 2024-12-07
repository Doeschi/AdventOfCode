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