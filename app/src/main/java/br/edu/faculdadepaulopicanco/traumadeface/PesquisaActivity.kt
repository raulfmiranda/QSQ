package br.edu.faculdadepaulopicanco.traumadeface

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.edu.faculdadepaulopicanco.traumadeface.model.Pesquisa
import br.edu.faculdadepaulopicanco.traumadeface.model.Questionario

class PesquisaActivity : AppCompatActivity() {

    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>())

    companion object {
        val EXTRA_PESQUISA = "PESQUISA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)

        pesquisa = intent.extras.get(EXTRA_PESQUISA) as Pesquisa
        val size = pesquisa.questionarios.size
        Toast.makeText(this, size, Toast.LENGTH_LONG).show()
    }
}
