# Lecture 3: Consistency Condition

## Definations

- *Shared abstract data type*
  - Can be accessed by multiple processes
  - Shorthand: Shared object
- *Consistency*
  - Specifies what behavior is allowed when a shared object is accessed by multiple processes.
  - When something is consistent, it satisfies the specification.
  - No right or wrong, anything can be a specification.
  - Should be sufficiently strong
    - Otherwise the shared object cannot be used in a program
  - Can be implemented efficiently.
    - Otherwise remains a theory
- *Operation*: A single invocation/response pair of a single method of a single shared object by a process.
- *Sequential*: A history is sequencial if:
  - Any invocation is always immediately followed by its response
  - No interleaving
  - Opposite: Concurrent
- *Legal*: A sequential history H is legal if:
  - All responses satisfies the sequential semantics of the data type
  - Sequential semantics: The semantics you would get if there is only one process accessing that data type.
  - It is possible for a sequential history not to be legal.
- *Equivalency*
  - Two histories are equivalent if they have exactly the same set of events.
    - Same events imply all responses are the same
    - Ordering of events may be different
    - User only cares about responses

## Sequential Consistency

- A history H is sequentially consistent if it is equivalent to some legal sequential history S that preserves process order
  - Use sequential history as a comparison point
  - Require that program order be preserved
- __External order__: o1 `<` o2 iff response of o1 appears in H before invocation of o2.

## Linearizability

- Formal defination: A history H is linearizable if
  1. It is equivalent to some legal sequential history S, and
  2. The external order induced by H is a subset of the external order induced by S.
- Intuitive defination: The execution is equivalent to some execution such that each operation happens instantaneously at some point between the invocation and response.
- A linearizable history must be sequentially consistent.
- Local property: H is linearizable iff for any object x, H|x is linearizable.

## Consistency definatins for registers

- Register is a kind of abstract data type: A single value that can be read and written.
- *Atomic register*: A register whose implementation always ensures linearizability of the history.
- *Regular register*
  - When a read does not overlap with a write, the read returns the value written by one of the most recent writes.
  - When the read overlaps with a write, the read returns the value written by one of the most recent writes or the value written by one of the overlapping writes.
- *Safe register*
  - A register is called safe if the implementation always ensures that 
    - When a read does not overlap with any write, then it returns the value written by one of the most recent writes.
    - When a read overlaps with one or more writes, it can return anything.

- Atomic register -> Regular register -> Safe register
