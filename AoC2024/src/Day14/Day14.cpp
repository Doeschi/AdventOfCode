//
// Created by dgern on 14.12.2024.
//

#include <iostream>
#include <regex>

#include "Day14.h"
#include "../util/Util.h"

Day14::Day14() : BaseDay{"day14.txt"} {
    m_width = 101;
    m_height = 103;

    initRobots();
    initCells();

    // create json for javascript visualization
//    toJson();
}

void Day14::solvePartOne() {
    moveRobots(100);
    std::cout << "Safety factor: " << getSafetyFactor() << "\n";
    moveRobots(-100);
}

void Day14::solvePartTwo() {
    // credit: https://www.reddit.com/r/adventofcode/comments/1he39ou/2024_day_14_part_2_visualizing_tea_time_for_robots/

    auto peakNeighbors = 0;
    auto peakSecond = 0;

    for (int second = 1; second < 10000; ++second) {
        moveRobots(1);

        // count number of neighbors
        auto neighbors = 0;
        for (auto y = 0; y < m_height; ++y) {
            for (auto x = 0; x < m_width; ++x) {
                // the cell has at least one robot on it
                if (m_cells[y][x] != 0) {

                    // check all direct neighbors
                    for (const auto& offset: directNeighborOffsets) {
                        auto testX = x + offset.x;
                        auto testY = y + offset.y;

                        if (testX < 0 || testX > m_width - 1 || testY < 0 || testY > m_height - 1)
                            continue;

                        if (m_cells[testY][testX] != 0)
                            neighbors += m_cells[testY][testX];
                    }
                }
            }
        }

        if (neighbors > peakNeighbors) {
            peakNeighbors = neighbors;
            peakSecond = second;
        }
    }

    std::cout << "Highest number of neighbors after: " << peakSecond << "\n";
}

void Day14::initRobots() {
    using namespace std::string_literals;

    Robot robot{};

    for (const auto& line: m_inputLines) {
        std::regex pattern("(-{0,1}\\d+)");

        std::sregex_iterator begin(line.begin(), line.end(), pattern);
        std::sregex_iterator end;

        int j = 0;
        for (auto it = begin; it != end; ++it) {
            auto val = stoi((*it).str());

            if (j == 0) {
                robot.pos.x = val;
            } else if (j == 1) {
                robot.pos.y = val;
            } else if (j == 2) {
                robot.vel.x = val;
            } else if (j == 3) {
                robot.vel.y = val;
            }

            ++j;
        }

        m_robots.push_back(robot);
    }
}

void Day14::initCells() {
    for (auto y = 0; y < m_height; ++y) {
        std::vector<int> line{};
        for (auto x = 0; x < m_width; ++x) {
            line.push_back(0);
        }
        m_cells.push_back(line);
    }

    for (const auto& robot: m_robots) {
        ++m_cells[robot.pos.y][robot.pos.x];
    }
}


void Day14::moveRobots(int seconds) {
    for (auto& robot: m_robots) {
        --m_cells[robot.pos.y][robot.pos.x];

        robot.pos.x = (robot.pos.x + robot.vel.x * seconds) % m_width;
        if (robot.pos.x < 0)
            robot.pos.x += m_width;

        robot.pos.y = (robot.pos.y + robot.vel.y * seconds) % m_height;
        if (robot.pos.y < 0)
            robot.pos.y += m_height;

        ++m_cells[robot.pos.y][robot.pos.x];
    }
}

int Day14::getSafetyFactor() const {
    auto safetyFactor = 1;

    auto yOffset = 0;

    for (int y = 0; y < 2; ++y) {
        auto xOffset = 0;
        for (int x = 0; x < 2; ++x) {
            auto counter{0};

            for (const auto& robot: m_robots) {
                if (robot.pos.x >= xOffset && robot.pos.x < xOffset + m_width / 2 && robot.pos.y >= yOffset &&
                    robot.pos.y < yOffset + m_height / 2)
                    ++counter;
            }

            safetyFactor *= counter;

            xOffset += m_width / 2 + 1;
        }
        yOffset += m_height / 2 + 1;
    }

    return safetyFactor;
}

void Day14::toJson() const {
    std::string out = "[";

    for (const auto& robot: m_robots) {
        out += "{";
        out += R"("x":")" + std::to_string(robot.pos.x) + "\",";
        out += R"("y":")" + std::to_string(robot.pos.y) + "\",";
        out += R"("xVel":")" + std::to_string(robot.vel.x) + "\",";
        out += R"("yVel":")" + std::to_string(robot.vel.y) + "\"";

        out += "},";
    }

    out.pop_back();

    out += "]";

    std::cout << out << "\n";
}
