cmake_minimum_required(VERSION 3.30)
project(AoC2024)

set(CMAKE_CXX_STANDARD 20)

if(WIN32)
    if("${CMAKE_CXX_COMPILER_ID}" STREQUAL "MSVC")
        message("WIN32: Found Compiler: " ${CMAKE_CXX_COMPILER_ID} " / " ${CMAKE_CXX_COMPILER_VERSION})

        string(APPEND CMAKE_CXX_FLAGS " /W4 /wd4201")
        string(APPEND CMAKE_CXX_FLAGS " /Zc:__cplusplus")
    endif()
endif(WIN32)

add_executable(AoC2024 main.cpp
        src/RandomTests.cpp
        src/util/Util.cpp
        src/util/Util.h
        src/BaseDay/BaseDay.cpp
        src/BaseDay/BaseDay.h
        src/Day01/Day01.cpp
        src/Day01/Day01.h
        src/Day02/Day02.cpp
        src/Day02/Day02.h
        src/Day03/Day03.cpp
        src/Day03/Day03.h
        src/Day04/Day04.cpp
        src/Day04/Day04.h
        src/Day05/Day05.cpp
        src/Day05/Day05.h
        src/Day06/Day06.cpp
        src/Day06/Day06.h
)

file(COPY src/Day01/day01.txt DESTINATION ${CMAKE_BINARY_DIR}/Debug)
file(COPY src/Day02/day02.txt DESTINATION ${CMAKE_BINARY_DIR}/Debug)
file(COPY src/Day03/day03.txt DESTINATION ${CMAKE_BINARY_DIR}/Debug)
file(COPY src/Day04/day04.txt DESTINATION ${CMAKE_BINARY_DIR}/Debug)
file(COPY src/Day05/day05.txt DESTINATION ${CMAKE_BINARY_DIR}/Debug)
file(COPY src/Day06/day06.txt DESTINATION ${CMAKE_BINARY_DIR}/Debug)
