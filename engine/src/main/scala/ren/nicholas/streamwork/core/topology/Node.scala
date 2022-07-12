package ren.nicholas.streamwork.core.topology

import ren.nicholas.streamwork.core.executor.Executor

case class Node(name: String, executors: Seq[Executor[? <: Any, ? <: Any]])
