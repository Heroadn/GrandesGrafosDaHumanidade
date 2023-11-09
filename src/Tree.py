'''classe responsavel por abstrair os nodos e oferecer metodos de arvore '''
class Tree:
    def __init__(self):
        self.id = 'xD'

    def insert(self):
        raise NotImplementedError

    def search(self, id):
        raise NotImplementedError