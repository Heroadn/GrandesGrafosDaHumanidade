import { defineStore } from 'pinia'
import instance from "@/stores/axious"

//renomear para useAuthStore
export const usePostService = defineStore('postService', {
    state: () => ({ titulo: "", texto: ""}),
    getters: {
        getTitulo: (state) => state.titulo,
        getTexto: (state) => state.texto
    },
    actions:
    {
        async post(titulo:string, texto:string)
        {   
            const response = instance.post("http://localhost:8081/post", {
                titulo:  titulo,
                texto: texto
            });
            
            return response;
        },
        async get(id:string)
        {
            const response = await instance.get("http://localhost:8081/post/" + id);
            return response;
        },
        async allByUsuario(usuarioId:String, page:number = 0)
        {
            const response = await instance.get("http://localhost:8081/post/usuario/" + usuarioId);
            return response;
        },
        async all(page:number = 0)
        {
            const response = await instance.get("http://localhost:8081/post");
            return response;
        }
    }
})
  