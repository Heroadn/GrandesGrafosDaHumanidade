'''classe responsavel por abstrair os nodos e oferecer metodos de arvore '''
from TreeLib import Tree
from Edge import Edge

from TreeLib import Node


class Graph:
    def __init__(self):
        self.tree = Tree()

    def add_edge(self, edge: Edge):
        source = self.tree.search(edge.source_id)
        destination = self.tree.search(edge.destination_id)
        is_dead_end = edge.destination_id is None

        if source is None:
            source = self.tree.create_node(
                edge.source_id,
                edge.weight)

        #if node destination do not exists,
        #create a destination if is not a dead end
        if (destination is None and not is_dead_end):
            destination = self.tree.create_node(
                edge.destination_id,
                edge.weight)

        source.link(destination)
        self.tree.insert(source)

    def print_graph(self):
        raise NotImplementedError
