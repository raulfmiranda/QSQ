package br.edu.faculdadepaulopicanco.traumadeface

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import br.edu.faculdadepaulopicanco.traumadeface.model.Pesquisa
import br.edu.faculdadepaulopicanco.traumadeface.model.Questao
import br.edu.faculdadepaulopicanco.traumadeface.model.Questionario
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    var questionarioAtual: Questionario = criarNovoQuestionario()
    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        carregaQuestaoNaTela(questionarioAtual.questoes[0])
        questionarioAtual.status = 0

        btnProx.setOnClickListener {
            val rdId = radioGroup.checkedRadioButtonId
            if(rdId != -1) {
                var s = questionarioAtual.status
                when(rdId) {
                    R.id.rdResposta0 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 0
                    }
                    R.id.rdResposta1 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 1
                    }
                    R.id.rdResposta2 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 2
                    }
                    R.id.rdResposta3 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 3
                    }
                    R.id.rdResposta4 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 4
                    }
                    R.id.rdResposta5 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 5
                    }
                    R.id.rdResposta6 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 6
                    }
                    R.id.rdResposta7 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 7
                    }
                    R.id.rdResposta8 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 8
                    }
                }

                radioGroup.clearCheck()
                var questoesSize = questionarioAtual.questoes.size

                if(s < questoesSize - 1) {
                    s = ++questionarioAtual.status
                    carregaQuestaoNaTela(questionarioAtual.questoes[s])
                }
                else {
                    pesquisa.questionarios.add(questionarioAtual)
                    questionarioAtual = criarNovoQuestionario()
                    showEndAlert()
                }
            }
            else {
                Toast.makeText(this, "Escolha uma opção.", Toast.LENGTH_SHORT).show()
            }
        }

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

    fun showEndAlert() {
        val builder = AlertDialog.Builder(this@FormActivity)
        builder.setTitle("Questionário Finalizado")
        builder.setMessage("Deseja realizar novo questionário ou finalizar a pesquisa?")

        builder.setPositiveButton("Finalizar Pesquisa") {dialog, which ->
            Toast.makeText(this, "Pesquisa Finalizada", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Novo Questionário") {dialog, which ->
            Toast.makeText(this, "Novo Questionário Gerado", Toast.LENGTH_SHORT).show()
        }
        builder.create().show()
    }

    // TODO: criaNovoQuestionario
    fun criarNovoQuestionario(): Questionario {

        var r0 = mutableListOf("0 a 10", "11 a 20", "21 a 30", "31 a 40", "41 a 50", "51 a 60", "maior que 60", "", "")
        val q0 = Questao("Faixa etária", r0, -1)

        var r1 = mutableListOf("masculino", "feminino", "", "", "", "", "", "", "")
        val q1 = Questao("Sexo", r1, -1)

        var r2 = mutableListOf("solteiro", "casado", "união estável", "viúvo", "divorciado", "", "", "", "")
        val q2 = Questao("Estado civil", r2, -1)

        var r3 = mutableListOf("capital e região metropolitana", "interior", "", "", "", "", "", "", "")
        val q3 = Questao("Local da ocorrência", r3, -1)

        var r4 = mutableListOf(
                "trabalhador assalariado",
                "trabalhador autônomo",
                "desempregado",
                "aposentado",
                "não trabalha", "", "", "", "")
        val q4 = Questao("Vínculo empregatício", r4, -1)

        var r5 = mutableListOf(
                "analfabeto",
                "ensino fundamental incompleto",
                "ensino fundamental completo",
                "ensino médio incompleto",
                "ensino médio completo",
                "ensino superior completo",
                "ensino superior incompleto", "", "")
        val q5 = Questao("Escolaridade", r5, -1)

        var r6 = mutableListOf("", "", "", "", "", "", "", "", "")
        val q6 = Questao("", r6, -1)

        var r7 = mutableListOf("", "", "", "", "", "", "", "", "")
        val q7 = Questao("", r7, -1)

        var r8 = mutableListOf("", "", "", "", "", "", "", "", "")
        val q8 = Questao("", r8, -1)


        return Questionario(-1, mutableListOf<Questao>(q0, q1, q2, q3, q4, q5))
    }

}
