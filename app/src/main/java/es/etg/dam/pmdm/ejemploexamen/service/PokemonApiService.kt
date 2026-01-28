package es.etg.dam.pmdm.ejemploexamen.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApiService {
    @GET()
    suspend fun getPokemon(@Url url:String): Response<PokemonResponse>
}