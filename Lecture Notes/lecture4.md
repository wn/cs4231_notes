# Lecture 4: Models & Clocks

## Definations

- Process can perform three kinds of atomic actions/events
  - Local computation
  - Send a single message to a single process
  - Receive a single message from a single process
  - No atomic broadcast

## Software clocks

- Software "clocks" can incur much lower overhead than maintaining (sufficiently accurate) physical clock.
- Use protocol to inter ordering among events
- Possible ordering to users without physical clocks
  - process-order
  - send-receive order
  - transitivity
- *Happened-before* relation captures the ordering that is visible to users when there is no physical clock
  - A partial order among events
  - Captures process order, send-receive order and transivity

### Logical clocks

#### Protocol

- Single integer as its logical clock value
  - Increment clock value at each "local computation" and "send" event
  - At each receive event, newValue = max(oldValue,sentValue) + 1.

- Theorem: Event s happens before t -> logical clock value of s is smaller than the logical clock value of t.
- **Converse is not true**

### Vector clocks

#### Protocol

- Each process i has a local vector C
- Increment C[i] at each "local computation" and "send" event
- When receive message, newVector = pairwise-max(oldVector, sendVector); C[i] ++

- Theorem: Event s happens before event t <-> vector clock value of s is "smaller" than the vector clock value of t.

### Matrix clock

- Each event has n vector clocks, one for each process
- The *i*th vector on process i is called process *i*'s principle vector
- Principle vector is the same as vector clock before
- Non-principle vectors are just piggybacked on messages to update "knowledge"

#### Protocol

- Increment C[i] at each "local computation" and "send" event
- When sending a message, all n vectors are attached to the message
- At each "receive" event, let V be the principle vector of the sender. C = pairwise-max(C, V); C[i]++.
  - Note that we use pairwise-max on all vector of the process sending the message, with the *principle vector* of the sender, and not pairwise-max of all elements in the two matrices.
