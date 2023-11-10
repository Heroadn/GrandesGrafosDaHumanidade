'''classe responsavel por abstrair os nodos e oferecer metodos de arvore indexada'''
from typing import Optional
from src.treelib.node import Node


class Tree:
    def __init__(self):
        self.root = None
        self.nodes = set()

    def create_node(self, index, value) -> Node:
        return Node(index, value)

    # insere o nodo na arvore, e atualiza caso ja exista
    def insert(self, node: Node) -> Node:
        if self.root is None:
            self.root = node

        self.nodes.add(node)
        return node

    # retorna o nodo com o valor, null quando nao encontado
    def search(self, index) -> Optional[Node]:
        raise NotImplementedError

    def contains(self, index) -> bool:
        raise NotImplementedError

    def to_string(self):
        result = str()
        for node in self.nodes:
            result += node.to_string()

        return result