'''classe responsavel por abstrair os nodos e oferecer metodos de arvore indexada'''
from Node import Node


class Tree:
    def __init__(self):
        self.root = None

    def create_node(self, index, value) -> Node:
        return Node(index, value)

    # insere o nodo na arvore, e atualiza caso ja exista
    def insert(self, node: Node) -> Node:
        raise NotImplementedError

    # retorna o nodo com o valor, null quando nao encontado
    def search(self, index) -> Node | None:
        raise NotImplementedError

    def contains(self, index) -> bool:
        raise NotImplementedError
