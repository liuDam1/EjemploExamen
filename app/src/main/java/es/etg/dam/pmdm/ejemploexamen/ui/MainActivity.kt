package es.etg.dam.pmdm.ejemploexamen.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import es.etg.dam.pmdm.ejemploexamen.ui.viewModel.TituloFragment
import es.etg.dam.pmdm.ejemploexamen.databinding.ActivityMainBinding
import es.etg.dam.pmdm.ejemploexamen.ui.viewModel.DataAdapter
import es.etg.dam.pmdm.ejemploexamen.ui.viewModel.ItemViewModel
import es.etg.dam.pmdm.ejemploexamen.service.PokemonApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DataAdapter

    companion object {
        const val BASE_URL: String = "https://pokeapi.co/api/v2/"
        const val URL: String = "pokemon?limit=100"
        const val MSG_ERROR: String = "Error al consultar el servicio"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(binding.fgTitulo.id, TituloFragment())
            }
        }
    }

    fun mostrar(View : View){
        // Inicializamos el adapter con una lista vacía
        val data = ArrayList<ItemViewModel>()
        adapter = DataAdapter(data)

        // Configuramos el RecyclerView
        val recyclerview = binding.recyclerView
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

        // Llamamos a la API para obtener los datos
        consultarPokemon()
    }

    private fun consultarPokemon() {
        CoroutineScope(Dispatchers.IO).launch {
            // Consulto los Pokémon
            val call = getRetrofit().create(PokemonApiService::class.java).getPokemon(url = URL)
            // A la respuesta le pido que me de el PokemonResponse
            val pokemonResponse = call.body()

            // Este código se ejecuta en el thread principal (ui)
            runOnUiThread {
                // Se ejecutó correctamente el servicio
                if (call.isSuccessful) {
                    // Verificamos que la respuesta no sea null
                    pokemonResponse?.let { response ->
                        // Convertimos los resultados a ItemViewModel
                        val pokemonList = response.results.map { pokemonResult ->
                            ItemViewModel(pokemonResult.name, pokemonResult.url)
                        }

                        // Actualizamos el adapter con los datos obtenidos
                        adapter.updateData(pokemonList)
                    }
                } else {
                    Toast.makeText(this@MainActivity, MSG_ERROR, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getRetrofit(): retrofit2.Retrofit {
        return retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
    }
}