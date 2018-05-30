package br.edu.faculdadepaulopicanco.traumadeface

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Window
import android.widget.Toast
import br.edu.faculdadepaulopicanco.traumadeface.model.Pesquisa
import br.edu.faculdadepaulopicanco.traumadeface.model.Questionario
import kotlinx.android.synthetic.main.activity_pesquisa.*

class PesquisaActivity : AppCompatActivity() {

    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>())

    companion object {
        val EXTRA_PESQUISA = "PESQUISA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)

        supportActionBar?.title = "Resumo"

        pesquisa = intent.extras.get(EXTRA_PESQUISA) as Pesquisa
        val qtdQuestionarios = pesquisa.questionarios.size
        val qtdQuestPorQuestionario = pesquisa.questionarios[0].questoes.size
        val qtdTotalQuest = qtdQuestionarios * qtdQuestPorQuestionario
        val resumo = "Quantidade de questionários: $qtdQuestionarios\nQuantidade de questões por questionário: $qtdQuestPorQuestionario\nQuantidade total de questões: $qtdTotalQuest"
        txtResumo.text = resumo
    }
}
