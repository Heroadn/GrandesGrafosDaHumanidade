from src.edge import Edge
from src.graph import Graph

if __name__ == "__main__":
    edge1 = Edge("cidade_a", "cidade_b", 10)
    edge2 = Edge("cidade_a", "cidade_c", 20)
    edge3 = Edge("cidade_c", "cidade_d", 20)
    edge4 = Edge("cidade_c", "cidade_e", 10)

    graph = Graph()
    graph.add_edge(edge1)
    graph.add_edge(edge2)
    graph.add_edge(edge3)
    graph.add_edge(edge4)

    graph.print_graph()
    graph.dijkstra("cidade_a", "cidade_d")

    graph.dijkstra("cidade_a", "cidade_b")