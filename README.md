## sbt project compiled with Scala 3

### Goals

- Simple stream processing engine that:
    - have basic concepts in stream processing engine
        - source, operator, sink
    - support operations
        - transform
        - parallelism
        - fan-out
        - fan-in
        - delivery semantics
        - windowed computation
        - joining operation
        - backpressure
        - stateful operation
    - can run on multiple machines

### Tasks

- [x] Sink & SinkExecutor
- [x] Source and SourceExecutor
- [x] Operator and OperatorExecutor
- [x] Topology that connect above components
    - [x] construct stream
      - [x]source and sink should be connected via queues
      - [x]source and operator should be connected via queues
    - [x] start streams
    - [x] use thread-safe queue
    - [ ] stop streams
- [x] filter
- [ ] parallelism
### Future

is there an way to build it without mutable state?


Example code:
```sh
$HOME/projects/lab/GrokkingStreamingSystems
```