import { defineStore } from 'pinia'

export class Municipio {
    id: string;
    nome: string;
    image: string;

    constructor(id: string, nome: string, image: string) {
        this.id = id
        this.nome = nome
        this.image = image
    }
}

//renomear para useAuthStore
export const useMunicipio = defineStore('municipio', {
    state: () => (Municipio),
    getters: {
        getId: (state) => state.prototype.id,
        getNome: (state) => state.prototype.nome,
        getImage: (state) => state.prototype.image
    },
    actions:
    {
        printValues() {
            console.log("ID: " + this.getId + " NOME: " + this.getNome)
        },
    }
});
  