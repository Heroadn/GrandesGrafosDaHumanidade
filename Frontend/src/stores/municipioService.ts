import { defineStore } from 'pinia'
import instance from "@/stores/axious"

const HOST = 'localhost:8080'
const PATH = 'graph/nodes'

export class Municipio {
    nome: string;
    image: string;

    //
    source: string;
    destination: string;
    weight: string

    constructor(
        nome: string, 
        image: string,
        source: string,
        destination: string,
        weight: string ) 
    {
        this.nome = nome
        this.image = image
        this.source = source
        this.destination = destination
        this.weight = weight
    }
}

//renomear para useAuthStore
export const useMunicipioService = defineStore('municipioService', {
    state: () => (Municipio),
    getters: {
        getNome: (state) => state.prototype.nome,
        getImage: (state) => state.prototype.image,
        getSource: (state) => state.prototype.source,
        getDestination: (state) => state.prototype.destination,
        getWeight: (state) => state.prototype.weight,
    },
    actions:
    {
        async all(page:number = 0)
        {
            const response = await instance.get(`http://${HOST}/${PATH}`);
            return response;
        },
        printValues() {
            console.log(" NOME: " + this.getNome)
        },
    }
});
  