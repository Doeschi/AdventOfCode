cmake_minimum_required(VERSION 3.30)
project(AoC2024)

set(CMAKE_CXX_STANDARD 20)

add_executable(AoC2024 main.cpp
        src/BaseDay/BaseDay.cpp
        src/BaseDay/BaseDay.h
        src/Day01/Day01.cpp
        src/Day01/Day01.h
        src/util/Util.cpp
        src/util/Util.h
)

file(COPY src/Day01/day01.txt DESTINATION ${CMAKE_BINARY_DIR}/Debug)
