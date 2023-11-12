'''Classe responsável por representar o conteudo de um Edge '''


class Edge:
    def __init__(self, source_id: str, destination_id: str | None, weight, is_pedagio = False):
        self.source_id = source_id
        self.destination_id = destination_id
        self.weight = weight
        self.is_pedagio = is_pedagio

    def to_string(self):
        destination = self.destination_id
        source = self.source_id
        weight = self.weight

        return str(f"({source}) is connected to {destination} with weight {weight}")