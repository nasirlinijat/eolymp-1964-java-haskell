# Haskell – Functional Solution

## Introduction

This program solves e-Olymp problem [1964](https://www.eolymp.com/en/problems/1964) in Haskell, which is a pure functional language.

- There are **no variables and no loops**. Repetition is done with recursion.
- The numbers `1..n` are a list. Each step does not change the list – it builds a **new** list without the removed numbers.
- The position of each number is passed along as an argument and grows by one on every recursive call.

**e-Olymp score: 97**

## How to run

You need GHC (the Haskell compiler). Open a terminal in the `Haskell` folder.

Mac (with Homebrew):

```bash
brew install ghc
ghc -O2 Main.hs -o main
echo 20 | ./main
```

Linux:

```bash
# install GHC with GHCup: https://www.haskell.org/ghcup/
ghc -O2 Main.hs -o main
echo 20 | ./main
```

Windows (PowerShell, after installing GHCup):

```powershell
ghc -O2 Main.hs -o main.exe
echo 20 | .\main.exe
```

Output:

```
15
```

Without compiling you can also run `echo 20 | runghc Main.hs`, but it is much slower.

For e-Olymp, choose **Haskell** and submit `Main.hs`.

## How the file was written (step by step)

### Step 1 – The rule for each step

First I wrote `skip`. It takes the number of the step and a position, and says if the number at that position must be removed. Pattern matching on the first argument picks the rule.

```haskell
skip :: Int ->Int -> Bool
skip 0 n = even n
skip 1 n = odd n
skip _ n = n `mod` 3 == 0
```

Step `0` removes even positions, step `1` removes odd positions, and any other step (`2`) removes positions divisible by 3. This is the same as `shouldRemove` in the Java solution.

### Step 2 – One step of removing

`pass k` does one step with rule `k`. The real work is done by a local helper `go` (written under `where`), which goes through the list with recursion and keeps the current position `i`:

- an empty list gives an empty list;
- if `skip k i` is true, the number `x` is skipped and `go` continues with the rest `xs`;
- otherwise `x` is put in front of the result with `:`.

The position grows by one on every call.

```haskell
pass ::Int -> [a] -> [a]
pass k = go 1
  where
    go _ [] = []
    go i (x:xs)
      | skip k i = go (i+1) xs
      | otherwise = x : go (i+1)xs
```

`pass k` always starts `go` from position `1`, so the "number the rest again from 1" part of the problem is done by the starting value. `pass` works for a list of any type (`[a]`), because it only looks at positions, not at the numbers.

### Step 3 – Repeating until one number is left

`solve n` starts at step `0` with the list `[1..n]`. Another local helper, `iter`, repeats the steps:

- if the list is `[x]` (only one number), the answer is `x`;
- otherwise it does one `pass` and calls itself with the next step. ``(k+1) `mod` 3`` goes `0 → 1 → 2 → 0 → ...`.

```haskell
solve :: Int ->Int
solve n = iter 0 [1..n]
  where
    iter _ [x] = x
    iter k xs = iter ((k+1) `mod` 3) (pass k xs)
```

### Step 4 – Reading the input

`main` reads `n` and prints the answer of `solve n`.

```haskell
main :: IO ()
main =do
  n <- readLn
  print (solve n)
```

## Explaining the results

e-Olymp score: **97**.

| n | Answer |
|---|--------|
| 1 | 1 |
| 10 | 3 |
| 20 | 15 |
| 100 | 87 |
| 1000 | 519 |

- **Laziness.** Haskell does not build a list until somebody needs its elements. So `[1..n]` is never stored as one big list of `10^8` numbers. Numbers flow one by one through all the steps, and the ones that are removed are thrown away right away. Memory stays small, unlike the Java solution, which keeps the whole `ArrayList` (and the new list of each step) in memory.
- **Time.** The amount of work is the same as in Java: about `2n` numbers are checked in total (**O(n)**). Every new list element is a new object on the heap, and the extra work of laziness makes it slower than a simple loop. Even so, the solution scored 97 on e-Olymp, well above the 70 needed for the functional language.
- **Compared to Java.** `skip` is the same rule as `shouldRemove` in Java. Both build a new list on every step. The Java code does it with a `for` loop, an index `i` and a variable `list` that is reassigned inside a `while` loop. The Haskell code has no variables at all: the index becomes the argument `i` of `go`, and the loops become the recursive helpers `go` and `iter`.
