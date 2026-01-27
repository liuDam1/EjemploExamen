package es.etg.dam.pmdm.ejemploexamen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.etg.dam.pmdm.ejemploexamen.LoginActivity.Constantes.NOMBRE
import es.etg.dam.pmdm.ejemploexamen.databinding.ActivityLoginBinding
import kotlin.text.isNotEmpty

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    object Constantes {
        const val NOMBRE: String = "Nombre"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nombre = leer()
        if (nombre != null && nombre.isNotEmpty()) {
            binding.txtNombre.setText(nombre)
        }
    }

    fun guardar(view: View) {
        val nombre = binding.txtNombre.text.toString()
        val sharedPref = getPreferences(MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(NOMBRE, nombre)
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(NOMBRE, nombre)
        startActivity(intent)
    }

    private fun leer(): String? {
        val sharedPref = getPreferences(MODE_PRIVATE)
        val nombre = sharedPref.getString(NOMBRE, "")
        return nombre
    }
}