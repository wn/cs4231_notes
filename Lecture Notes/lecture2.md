# Lecture 2: Synchronization Primatives

## Notes

- Peterson's algorithm and Lamport's Bakery Algorithm results in busy waiting.
  - Wastes CPU cycles
  - **Need OS support**
- Circumventing deadlock:
  - Avoid cycles or
  - Have a total ordeirng of the wait-free graph (graph where wait < free)

## Semaphores

- `P()`
  - Increase semaphore count
- `V()`
  - Decrease semaphore count
- When count reaches 0, any process that executes `P()` the semaphore will be blocked until `V()` is called by another process.
- Process choosen to be unblocked is arbitary.
- No busy waiting.

## Monitors

- Two queues required
  - Queue of process waiting for monitor's lock, which is required to enter synchronized functions.
  - Queue of process waiting to be notified from a condition. Enters queue when `wait()` is called.
- Two kind of monitors
  - *Hoare monitor*
    - One of the threads that was waiting on the condition continues execution.
  - *Java semantics*
    - The thread that makes the notify call continue its execution.
- `synchronized`
  - Only enter monitor if no one is in.
  - Else, block.
  - When thread leaves the method, notify (or notifyAll, depending on semantics) waiting threads.
- Nested monitors in Java
  - Do not use nested `synchronized` code as when the inner `synchronized` code is blocked, the outer `synchronized` code may still be blocked.
