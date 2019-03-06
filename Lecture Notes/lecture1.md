# Lecture 1: Mutual Exclusion Problem

## Definations 

- Critical section: Section of code that needs to be executed atomically.
- Properties of mutual exclusion:
  - Mutual exclusion: No more than one process in the critical section.
  - Progress: If one or more process wants to enter and if no one is in the critical section, then one of them can enter the critical section.
  - No starvation: If a process wants to enter, it eventually can always enter.
  - Need to consider the worst-case schedule/

## Software solutions

- Peterson's Algorithm:
  - General idea: Check if the other process wants to enter CS and it is the other process turn.

- Lamport's Bakery Algorithm:
  - For n processes
    - Get a number.
    - Get served when all people with lower number have been served.
  - Two shared arrays of n elements
    - One array to check if a process is choosing a number
    - One array to check if the process has the highest priority to enter the critical section.

## Hardware solutions

- Disabling interupts to prevent context switch.
- Special machine-level instructions
  - TestAndSet - Executed atomically, cannot be interrupted.