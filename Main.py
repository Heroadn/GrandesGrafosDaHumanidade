from src.Edge import Edge
from src.Graph import Graph
from src.TreeLib.Node import Node

if __name__ == "__main__":
    edge1 = Edge("a", "b", 10)
    edge2 = Edge("b", None, 10)

    graph = Graph()
    graph.add_edge(edge1)
    graph.add_edge(edge2)

    graph.print_graph()
