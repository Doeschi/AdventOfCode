//
// Created by dgern on 15.12.2024.
//

#include "../BaseDay/BaseDay.h"

class Day05: public BaseDay{
public:
    Day05();

    void solvePartOne() override;

    void solvePartTwo() override;

private:
    std::vector<int> m_jumpList;

    void initJumpList();
};