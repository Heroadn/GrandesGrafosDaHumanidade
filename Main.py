from src.Edge import Edge
from src.Graph import Graph

if __name__ == "__main__":
    edge1 = Edge("cidade_a", "cidade_b", 10)
    edge2 = Edge("cidade_a", "cidade_c", 20)
    edge3 = Edge("cidade_b", None, 0)

    graph = Graph()
    graph.add_edge(edge1)
    graph.add_edge(edge2)
    graph.add_edge(edge3)

    print("xD")
    graph.print_graph()
