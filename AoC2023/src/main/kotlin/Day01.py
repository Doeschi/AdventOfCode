import regex as re


def get_value(element: str) -> int:
    try:
        return int(element)

    except ValueError:
        if element == "one":
            return 1
        elif element == "two":
            return 2
        elif element == "three":
            return 3
        elif element == "four":
            return 4
        elif element == "five":
            return 5
        elif element == "six":
            return 6
        elif element == "seven":
            return 7
        elif element == "eight":
            return 8
        elif element == "nine":
            return 9
        else:
            raise RuntimeError(f"CANT PARSE NUMBER: {element}")


def solve_part2() -> int:
    lines = open("D:\Workspace\AdventOfCode\AoC2023\src\main\\resources\day01.txt").readlines()
    total = 0

    for line in lines:
        match = re.findall(r'one|two|three|four|five|six|seven|eight|nine|\d', line, overlapped=True)
        first_dig = get_value(match[0])
        last_dig = get_value(match[len(match) - 1])

        total += (first_dig * 10) + last_dig

    return total


def main():
    print(f"sum is {solve_part2()}")


if __name__ == "__main__":
    main()
