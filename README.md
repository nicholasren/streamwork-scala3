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
  - [x] construct stream 
    - [x]source and sink should be connected via queues
    - [x]source and operator should be connected via queues
  - [x] start streams
  - [ ] use thread safe queue
  - [ ] stop streams