import { defineStore } from 'pinia'
import instance from "@/stores/axious"

export const useAlertService = defineStore('alertService', {
    state: () => ({ titulo: "", texto: ""}),
    getters: {
        getTitulo: (state) => state.titulo,
        getTexto: (state) => state.texto
    },
    actions:
    {
        
    }
})
  