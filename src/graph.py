'''classe responsavel por abstrair os nodos e oferecer metodos de arvore '''

import heapq

from src.edge import Edge


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


    def print_graph(self):
        i = 1
        for k in self.adjacency_list.keys():
            for edge in self.adjacency_list[k]:
                print(f"vertex-{i} ({edge.source_id}) is connected to {edge.destination_id} with weight {edge.weight}")
            i+=1


    def dijkstra(self, start_vertex, end_vertex):
        priority_queue = [start_vertex]
        visited = {}
        distance = {}
        predecessor = {}  # To store the predecessor of each vertex in the shortest path

        for vertex in self.adjacency_list:
            distance[vertex] = float('inf')

        distance[start_vertex] = 0

        while priority_queue:
            current_vertex = heapq.heappop(priority_queue)

            if current_vertex in visited:
                continue

            visited[current_vertex] = True

            for edge in self.adjacency_list[current_vertex]:
                new_dist = distance[current_vertex] + edge.weight

                if new_dist < distance[edge.destination_id]:
                    distance[edge.destination_id] = new_dist
                    predecessor[edge.destination_id] = current_vertex  # Update predecessor
                    heapq.heappush(priority_queue, edge.destination_id)

        #Prints the shortest distance from the start vertex to each vertex
        for k in self.adjacency_list.keys():
            print(f"Shortest distance from vertex {start_vertex} to vertex {k}: {distance[k]}")

        print("#"*55)
        self.print_shortest_path(start_vertex, end_vertex, distance, predecessor)


    def print_along_with_dashes(self, number_of_dashes: int, content: str):
        print("#"*number_of_dashes)
        print(content)
        print("#"*number_of_dashes)


    def print_shortest_path(self, start_vertex, end_vertex, distance, predecessor):
        path = []
        current_vertex = end_vertex

        while current_vertex != start_vertex:
            path.insert(0, current_vertex)
            if current_vertex not in predecessor:
                break
            current_vertex = predecessor[current_vertex]

        path.insert(0, start_vertex)

        if end_vertex in distance and distance[end_vertex] != float('inf'):
            self.print_along_with_dashes(55, f"Shortest path from vertex {start_vertex} to vertex {end_vertex}: {' -> '.join(path)}")
            self.print_along_with_dashes(55, f"Shortest distance from vertex {start_vertex} to vertex {end_vertex}: {distance[end_vertex]}")
        else:
            self.print_along_with_dashes(55, f"Couldn't find a path from {start_vertex} to {end_vertex}!")
