package es.etg.dam.pmdm.ejemploexamen.service

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)