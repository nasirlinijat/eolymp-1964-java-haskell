# Java – Imperative Solution (list processing)

## Introduction

This program solves e-Olymp problem [1964](https://www.eolymp.com/en/problems/1964) by simulating the game on a list.

- The numbers `1..n` are put into an `ArrayList<Integer>`.
- Each step goes over the list once and copies the numbers that are **not** removed into a new list.
- The steps repeat in the order *even → odd → divisible by 3* until only one number is left.

There is a much shorter solution with a formula, but the task asks for the list processing approach, so this is a direct simulation.

**e-Olymp score: 97**

## How to run

You need Java 11 or newer. Open a terminal in the `Java` folder.

Mac / Linux:

```bash
javac Main.java
echo 20 | java Main
```

Windows (PowerShell):

```powershell
javac Main.java
echo 20 | java Main
```

Output:

```
15
```

You can also run `java Main` without `echo` and type the number yourself.

For e-Olymp, choose **Java** and submit `Main.java` as it is.

## How the file was written (step by step)

### Step 1 – The rule for each step

First I wrote a small function that says if a number at a given position must be removed. Positions start from 1, like in the problem.

```java
public static boolean shouldRemove(int position, int step) {
    if (step == 0) return position %2 == 0;
    if (step == 1) return position % 2 == 1;
    return position % 3 == 0;
}
```

Step `0` removes even positions, step `1` removes odd positions, step `2` removes positions divisible by 3.

### Step 2 – One step of removing

Next I wrote `removeStep`. It goes over the list with a `for` loop and adds every number that stays to a new list called `next`. The position of the number at index `i` is `i + 1`.

```java
public static ArrayList<Integer> removeStep(ArrayList<Integer> list, int step) {
    ArrayList<Integer> next = new ArrayList<>();
    for(int i = 0; i < list.size(); i++){
        if (!shouldRemove(i + 1, step)){
            next.add(list.get(i));
        }
    }
    return next;
}
```

Because `next` starts empty and is filled from index `0`, the "number the rest again from 1" part of the problem happens by itself.

### Step 3 – Main loop

Last I read `n`, filled the list with `1..n` and called `removeStep` until one number was left. `step = (step + 1) % 3` moves through the three rules in a cycle.

```java
ArrayList<Integer> list = new ArrayList<>();

for (int i = 1; i <= n; i++) list.add(i);
int step = 0;
while (list.size() > 1){
    list = removeStep(list, step);
    step = (step + 1) % 3;
}
System.out.println(list.get(0));
```

## Explaining the results

e-Olymp score: **97**.

Tested on my machine (`echo n | java Main`, time includes Java startup):

| n | Answer | Time |
|---|--------|------|
| 1 | 1 | – |
| 10 | 3 | – |
| 20 | 15 | – |
| 1000 | 519 | – |
| 10,000,000 | 4031079 | 0.23 s |

- **Time.** The first step reads all `n` numbers, the second reads `n/2`, the third `n/4`, the fourth `n/6`, and so on. One full cycle makes the list about 6 times shorter, so all steps together read about `2n` numbers. This is **O(n)**.
- **Memory.** `ArrayList<Integer>` keeps every number as a separate `Integer` object, and during a step the old list and the new list exist at the same time. So for big `n` this solution needs a lot of memory. This is the price of keeping the whole list in memory, which the formula solution does not need.
