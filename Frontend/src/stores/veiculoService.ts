import { defineStore } from 'pinia'
import instance from "@/stores/axious"

const HOST = 'localhost:5000'
const PATH = 'info/vehicles'

export class Veiculo {
    nome: string;
    fuelType: string;
    kilometersPerLiter: string;
    speedInKmH: string;
    image: string;

    constructor(
        nome: string,
        fuelType: string,
        kilometersPerLiter: string,
        speedInKmH: string,
        image: string) 
    {
        this.nome = nome
        this.fuelType = fuelType
        this.kilometersPerLiter = kilometersPerLiter;
        this.speedInKmH = speedInKmH;
        this.image = image
    }
}

//renomear para useAuthStore
export const useVeiculoService = defineStore('veiculoService', {
    state: () => (Veiculo),
    getters: {
        getNome: (state) => state.prototype.nome,
        getImage: (state) => state.prototype.image,
        getFuelType: (state) => state.prototype.image,
        getKilometersPerLiter: (state) => state.prototype.kilometersPerLiter,
        getSpeedInKmH: (state) => state.prototype.speedInKmH
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
  