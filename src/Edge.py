'''classe responsavel por representar o conteudo de um Edge '''


class Edge:
    def __init__(self, source_id: str, destination_id: str | None, weight):
        self.source_id = ''
        self.destination_id = ''
        self.weight = 0
