//
// Created by dgern on 01.12.2024.
//

#include "BaseDay.h"

#include <iostream>
#include <fstream>

BaseDay::BaseDay(const std::string filepath) {
    std::ifstream fileStream{filepath};

    if (!fileStream.is_open()) {
        std::cerr << "Error opening file " << filepath << std::endl;
        exit(69);
    }

    std::string line;
    while (getline(fileStream, line)) {
        m_inputLines.push_back(line);
    }

    fileStream.close();
}
