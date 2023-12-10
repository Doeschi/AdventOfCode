/*
 * Copyright (c) 2023. by Dominic Gernert
 */

data class Point2d(val x: Int, val y: Int)

data class Edge(val start: Point2d, val end: Point2d)

data class Polygon(val edges: MutableList<Edge> = mutableListOf()) {
    fun contains(p: Point2d): Boolean {
        // check if the point is inside the loop with the
        // ray casting algorithm
        var count = 0

        edges.forEach { edge ->
            if ((p.y < edge.start.y) != (p.y < edge.end.y) &&
                p.x < edge.start.x + (((p.y - edge.start.y) / (edge.end.y - edge.start.y)) * (edge.end.x - edge.start.x))
            )
                count++
        }

        return count % 2 == 1

    }
}