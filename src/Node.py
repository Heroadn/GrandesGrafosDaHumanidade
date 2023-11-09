class Node:
    def __init__(self):
        self.id = 'xD'
        self.data = {}

    def link(self):
        raise NotImplementedError

    def unlink(self):
        raise NotImplementedError

    def to_string(self):
        print("TESTE: ", self.id)