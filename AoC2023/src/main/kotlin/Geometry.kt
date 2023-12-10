/*
 * Copyright (c) 2023. by Dominic Gernert
 */

data class Point2d(val x: Int, val y: Int)

data class Edge(val start: Point2d, val end: Point2d)

data class Polygon(val edges: MutableList<Edge> = mutableListOf())