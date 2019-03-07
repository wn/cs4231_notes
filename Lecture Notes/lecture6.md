# Lecture 6: Message Ordering

## Definitions

## Causal Order

- if a "send" event s<sub>1</sub> caused a "send" event s<sub>2</sub>.
- Causal ordering requires the corresponding receive event r<sub>1</sub> to be before r<sub>2</sub> (where r<sub>1</sub> and r<sub>2</sub> are on the same process)
- If a "send" event s1 happened before a "send" event s<sub>2</sub>:
  - Then s1 *may* have caused s<sub>2</sub> 
  - Pessimistic choice: Assume s<sub>1</sub>  indeed caused s<sub>2</sub>.
- Formal defination of *causal order*: s<sub>1</sub> → s<sub>2</sub> Λ P<sub>r<sub>1</sub></sub> = P<sub>r<sub>2</sub></sub> ⇒ r<sub>1</sub> → r<sub>2</sub>.

### Ensuring causal ordering

- Piggyback all message
  - Works but overhead of piggybacking all messages is too large.

### Matrix Algorithm
  
### Birman-Schiper-Stephenson Protocol

### Schiper-Eggli-Sandoz Algorithm