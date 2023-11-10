'''Classe responsável por representar o conteudo de um Edge '''


class Edge:
    def __init__(self, source_id: str, destination_id: str | None, weight):
        self.source_id = source_id
        self.destination_id = destination_id
        self.weight = weight

    def to_string(self):
        return str(
            self.source_id +
            ', ' +
            str(self.weight) +
            ', ' +
            self.destination_id)