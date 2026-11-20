# e-Olymp 1964 – What is left?

## Introduction

Problem: [https://www.eolymp.com/en/problems/1964](https://www.eolymp.com/en/problems/1964)

The numbers from `1` to `n` are written in a row (`1 ≤ n ≤ 10^8`). Then these steps are repeated until only one number is left:

1. Remove all numbers in **even** positions.
2. Number the rest again from 1 and remove all numbers in **odd** positions.
3. Number the rest again from 1 and remove all numbers in positions **divisible by 3**.
4. Go back to step 1.

The program prints the number that is left.

The task is solved in two languages:

| Folder | Language | Paradigm | e-Olymp score |
|--------|----------|----------|---------------|
| [`/Java`](Java) | Java | Imperative – the numbers are stored in an `ArrayList`, and a loop builds a new list on every step | 97 |
| [`/Haskell`](Haskell) | Haskell | Functional – the numbers are a list, and every step makes a new list with recursion (no variables, no loops) | 97 |

Each folder has its own `README.md` that explains how the code was written and how to run it.

## Example

For `n = 20`:

| Step | Rule | List after the step |
|------|------|---------------------|
| start | – | 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 |
| 1 | remove even positions | 1 3 5 7 9 11 13 15 17 19 |
| 2 | remove odd positions | 3 7 11 15 19 |
| 3 | remove positions divisible by 3 | 3 7 15 19 |
| 4 | remove even positions | 3 15 |
| 5 | remove odd positions | **15** |

Answer: `15`.

## Folder structure

```
/Java
    Main.java
    README.md
/Haskell
    Main.hs
    README.md
README.md
```
