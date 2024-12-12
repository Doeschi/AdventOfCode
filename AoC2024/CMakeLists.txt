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

# Collect all source files
file(GLOB SOURCES
        main.cpp
        src/RandomTests.cpp
        src/util/*.cpp
        src/util/*.h
        src/BaseDay/*.cpp
        src/BaseDay/*.h
)

# Collect DayXX files
file(GLOB DAY_SOURCES "src/Day*/Day*.cpp" "src/Day*/Day*.h")

# Append DayXX files to the SOURCES list
list(APPEND SOURCES ${DAY_SOURCES})

# Create the executable
add_executable(AoC2024 ${SOURCES})

# Copy textfiles to bin directory
file(GLOB DAY_TXT "src/Day*/Day*.txt")

foreach(FILE ${DAY_TXT})
    file(COPY ${FILE} DESTINATION ${CMAKE_BINARY_DIR}/Debug)
    file(COPY ${FILE} DESTINATION ${CMAKE_BINARY_DIR}/Release)
endforeach()



