package ren.nicholas.streamwork.core.topology

import ren.nicholas.streamwork.core.executor.Executor

type AnyExecutor = Executor[_, _]

case class Node(name: String, executors: Seq[AnyExecutor])
