import { defineStore } from 'pinia'

//renomear para useAuthStore
export const useUsuario = defineStore('usuario', {
    state: () => ({ id: "", nome: "", email:"", descricao:""}),
    getters: {
        getId: (state) => state.id,
        getNome: (state) => state.nome,
        getEmail: (state) => state.email,
        getDescricao: (state) => state.descricao
    },
    actions:
    {
        printValues() {
            console.log("ID: " + this.getId + " NOME: " + this.getNome + " EMAIL: " + this.email)
        },
    }
})
  