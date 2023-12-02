import regex as re


def getValue(element: str) -> int:
    try:
        return int(element)

    except:
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


def solvePart2() -> int:
    lines = open('D:\Workspace\AdventOfCode\AoC2023\src\main\\resources\day01.txt').readlines()
    sum = 0

    for line in lines:
        match = re.findall(r'one|two|three|four|five|six|seven|eight|nine|\d', line, overlapped=True)
        first_dig = getValue(match[0])
        last_dig = getValue(match[len(match) - 1])

        sum += (first_dig * 10) + last_dig

    return sum


def main():
    print(f"sum is {solvePart2()}")


if __name__ == "__main__":
    main()
