'''classe responsavel por representar um nodo '''
from __future__ import annotations


class Node:
    index = None
    value = None

    def __init__(self, index, value):
        self.children = []
        self.parents = []
        self.index = index
        self.value = value

    def link(self, node: Node):
        self.children.append(node)

    def unlink(self, index):
        # TODO: search index and remove node
        raise NotImplementedError

    def accept(self, node: Node):
        self.parents.append(node)

    def contains(self, index):
        for child in self.children:
            if child.index is index:
                return child

        return None

    # TODO: better print
    def to_string(self):
        result = str()
        for parent in self.parents:
            result += ("Parent: " + parent + "\n")
        for child in self.children:
            result += ("Child: " + child + "\n")
        result += ("Node: " + self.value.to_string() + "\n")