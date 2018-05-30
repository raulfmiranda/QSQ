package br.edu.faculdadepaulopicanco.traumadeface

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import br.edu.faculdadepaulopicanco.traumadeface.model.Questao
import br.edu.faculdadepaulopicanco.traumadeface.model.Questionario
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        var respostas = mutableListOf(
                "trabalhador assalariado",
                "trabalhador autônomo",
                "desempregado",
                "aposentado",
                "não trabalha", "", "", "", "")
        val questao = Questao("Vínculo empregatício", respostas, -1)

        carregaQuestaoNaTela(questao)
    }

    fun carregaQuestaoNaTela(questao: Questao) {

        txtPergunta.text = questao.pergunta

        for (i in 0..8) {
            var rdResposta = radioGroup.getChildAt(i) as RadioButton
            if(!questao.respostas[i].isNullOrBlank()) {
                rdResposta.text = questao.respostas[i]
                rdResposta.visibility = RadioButton.VISIBLE
            } else {
                rdResposta.text = ""
                rdResposta.visibility = RadioButton.INVISIBLE
            }
        }
    }

    // TODO: criaNovoQuestionario
    fun criaNovoQuestionario(): Questionario {
        return Questionario(-1, mutableListOf())
    }

}
