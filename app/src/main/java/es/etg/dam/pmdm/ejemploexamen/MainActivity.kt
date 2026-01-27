package es.etg.dam.pmdm.ejemploexamen

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.etg.dam.pmdm.ejemploexamen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mostrarNombre()
    }

    private fun mostrarNombre(){
        val nombreTxt = binding.tvNombre
        val extras = intent.extras

        if (extras != null) {
            val nombre = extras.getString(LoginActivity.Constantes.NOMBRE, "")
            nombreTxt.text = nombre
        }
    }
}