## sbt project compiled with Scala 3

Goals: no mutable state in stream work

### TODO

first implementation engine
is there an way to build it without mutable state?

### Tasks

- [x] Sink & SinkExecutor
- [x] Source and SourceExecutor
- [x] Operator and OperatorExecutor
- [ ] Topology that connect above components
    - [ ] start streams
    - [ ] stop streams