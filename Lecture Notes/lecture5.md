# Lecture 5: Global snapshot

- Goal: Take a photo of the global computation. 
- Used for
  - debugging
  - backup/check-pointing
  - calculating global predicate

## Definitions

- Consistent snapshot
  - A snapshot of local states on n processes s.t. global snapshot *could have happened in the past*
  - *Global snapshot*: A set of events s.t. if e2 is in the set and e1 is before e2 in process order, then e1 must be in the set.
- Formal defination
  - *Consistent global snapshot*: A global snapshot such that if e2 is in the set and e1 is before e2 in send-receive order, then e1 must be in the set. 
    - Intuitively: For a send-receive order, snapshot should not be capturing a receive event but not a send event.

## Requirements

- Snapshot should not interfere with normal process actions
- Should not require processes to stop sending messages
- Each process is able to record its own state (could be heap, registers, program counter, etc)
- Global state is colelcted in a distributed manner
- Any process may initiate the snapshot

### Chandy-Lamport global snapshot algorithm

- Notes
  - Let white process be a process that has not recorded its own state, and red process be a process that has recorded its own state. Each process fires a marker of the same colour as itself.
  - We assume that the system only has one snapshot run. For multiple snapshot runs, we will need to include a unique ID for each snapshot initiated.

1. Initiator P<sub>i</sub> records its own state
2. Initiator process creates "marker" messages
3. P<sub>i</sub> sends out a "marker" to all outgoing channel (there are N - 1 markers). We assume all process to be fully inter-connected via channels.
4. Pi start recording the incoming messages for each channel that is connected to Pi.
5. Whenever a process Pj receives a marker message on an incoming channel,
    1. If P<sub>j</sub> is white
        1. Pj record its own state
        2. Mark the state of the channel that it receives the marker from, as empty. We can do this as the channel is FIFO, hence any message that was previously sent using the channel should reach P<sub>j</sub> before the marker.
        3. P<sub>j</sub> sends a red marker to all other channel. All channels C<sub>kj</sub> should be open as no marker has been sent through it before.
        4. Start recording the incoming messages on each of the incoming channels at P<sub>j</sub> (except i and k).
    2. If P<sub>j</sub> is red
        1. Mark the state of the channel C<sub>kj</sub> as empty as all the messages that was sent through the channel has been received.
6. The algorithm terminates when
    1. All processes have received a marker to record their own state
        1. To ensure that all process has taken a local snapshot
    2. All process have received a marker on all the (N - 1) incoming channels
        1. To record the state of all channels to capture "on-the-fly" messages
7. The resultant snapshot is a consistent cut. i.e. For all events in the snapshot, if e<sub>i</sub> -> e<sub>j</sub> and e<sub>j</sub> is in the cut, then e<sub>i</sub> is in the cut.
    1. Prove by contradiction. Assume e<sub>i</sub> -> e<sub>j</sub>, e<sub>j</sub> is in the global snapshot but e<sub>i</sub> is not.
        1. Case 1: Process j initiates the snapshot.
            1. Either a marker will be sent from P<sub>j</sub> to P<sub>i</sub>, or there is a series of processes that sends a marker within each other before being sent to P<sub>i</sub>. In either case, when the marker reaches P<sub>i</sub>, it will be after e<sub>i</sub> in process order. Hence P<sub>i</sub> would have taken its snapshot after e<sub>i</sub> and hence contradiction.
        2. Case 2: P<sub>j</sub> did not initiates the snapshot.
            1. P<sub>i</sub> took its snapshot before e<sub>i</sub>, which happened before e<sub>j</sub>. Since the channel is FIFO, if P<sub>i</sub> sends a marker to P<sub>j</sub>, the marker will only be received after e<sub>i</sub> has sent its message. Hence P<sub>i</sub> cannot be the process that triggers the snapshot of P<sub>j</sub> after e<sub>j</sub>.
            2. Assume P<sub>k</sub> is the process that trigger the snapshot of P<sub>j</sub>. From assumption, snapshot for P<sub>j</sub> is taken after e<sub>k</sub>. For the channel from P<sub>i</sub> to P<sub>k</sub> to be empty, it needs to receive the marker from P<sub>i</sub>. Since channel is FIFO, P<sub>j</sub> will receive the message from e<sub>i</sub> before the marker and the message will be recorded. Hence, e<sub>i</sub> will also be in the global snapshot, contradiction.
    2. By contradiction law, if e<sub>i</sub> -> e<sub>j</sub> and e<sub>j</sub> is in the snapshot, both e<sub>i</sub> and e<sub>j</sub> are in the global snapshot.