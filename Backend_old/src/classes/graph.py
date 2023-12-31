'''classe responsavel por abstrair os nodos e oferecer metodos de arvore '''

import heapq
from functools import reduce

from src.classes.trajeto import Trajeto
from src.classes.edge import Edge


class Graph:
    def __init__(self):
        self.adjacency_list = {}


    def add_edge(self, edge: Edge):
        if edge.source_id not in self.adjacency_list:
            self.adjacency_list[edge.source_id] = []

        if edge.destination_id not in self.adjacency_list:
            self.adjacency_list[edge.destination_id] = []

        self.adjacency_list[edge.source_id].append(edge)

        #Will also add the same path in reverse order, in order for the path to be bidirectional
        self.adjacency_list[edge.destination_id].append(Edge(edge.destination_id, edge.source_id, edge.weight))


    def get_nodes(self):
        return [str(k) for k in self.adjacency_list.keys()]


    def get_graph(self):
        graph_content = {}

        for k in self.adjacency_list.keys():
            if k not in graph_content:
                graph_content[k] = []

            for edge in self.adjacency_list[k]:
                graph_content[k].append(edge.to_dict())

        return graph_content


    def print_graph(self):
        i = 1
        text = ""
        for k in self.adjacency_list.keys():
            for edge in self.adjacency_list[k]:
                text+=f"vertex-{i} {edge.to_string()}\n"
            i+=1

        print(text)

        return text


    def dijkstra(self, start_vertex, end_vertex) -> Trajeto:
        priority_queue = [(0, start_vertex)]
        visited = {}
        distance = {}
        predecessor = {}  # To store the predecessor of each vertex in the shortest path

        for vertex in self.adjacency_list:
            distance[vertex] = float('inf')

        distance[start_vertex] = 0

        while priority_queue:
            current_distance, current_vertex = heapq.heappop(priority_queue)

            if current_vertex in visited:
                continue

            visited[current_vertex] = True

            if current_vertex in self.adjacency_list:
                for edge in self.adjacency_list[current_vertex]:
                    new_distance = current_distance + edge.weight

                    if new_distance < distance[edge.destination_id]:
                        distance[edge.destination_id] = new_distance
                        predecessor[edge.destination_id] = current_vertex  # Update predecessor
                        heapq.heappush(priority_queue, (new_distance, edge.destination_id))

        #Prints the shortest distance from the start vertex to each vertex
        for k in self.adjacency_list.keys():
            print(f"Shortest distance from vertex {start_vertex} to vertex {k}: {distance[k]}")


        print("#"*55)

        trajeto = self.get_shortest_path(start_vertex, end_vertex, distance, predecessor)

        if trajeto:
            self.print_along_with_dashes(
                55,
                f"Shortest path from vertex {start_vertex} to vertex {end_vertex}: {' -> '.join(trajeto.paths)}")
            self.print_along_with_dashes(
                55,
                f"Shortest distance from vertex {start_vertex} to vertex {end_vertex}: {trajeto.distance}")
        else:
            self.print_along_with_dashes(
                55,
                f"Couldn't find a path from {start_vertex} to {end_vertex}!")

        return trajeto


    def print_along_with_dashes(self, number_of_dashes: int, content: str):
        print("#"*number_of_dashes)
        print(content)
        print("#"*number_of_dashes)


    def get_shortest_path(self, start_vertex, end_vertex, distance, predecessor):
        path = []
        current_vertex = end_vertex

        while current_vertex != start_vertex:
            path.insert(0, current_vertex)
            if current_vertex not in predecessor:
                break
            current_vertex = predecessor[current_vertex]

        path.insert(0, start_vertex)

        if end_vertex in distance and distance[end_vertex] != float('inf'):
            shortest_distance = distance[end_vertex]
            return Trajeto(path, shortest_distance)
        else:
            return None