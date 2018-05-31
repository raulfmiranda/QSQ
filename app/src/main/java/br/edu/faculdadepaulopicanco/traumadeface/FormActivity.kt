package br.edu.faculdadepaulopicanco.traumadeface

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import br.edu.faculdadepaulopicanco.traumadeface.model.*
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    var questionarioAtual: Questionario = criarNovoQuestionario()
    var questionarioContador: QuestionarioContador = criarQuestionarioContador()
    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>(), questionarioContador)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        supportActionBar?.title = "Questionário"

        carregaQuestaoNaTela(questionarioAtual.questoes[0])

        btnProx.setOnClickListener {
            val rdId = radioGroup.checkedRadioButtonId
            if(rdId != -1) {
                var s = questionarioAtual.status
                when(rdId) {
                    R.id.rdResposta0 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 0

                        val key = questionarioAtual.questoes[s].respostas[0]
                        var value = questionarioContador.questoesContadoras[s].respostasContadoras.getValue(key)
                        value++
                        questionarioContador.questoesContadoras[s].respostasContadoras.set(key, value)
                    }
                    R.id.rdResposta1 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 1

                        val key = questionarioAtual.questoes[s].respostas[1]
                        var value = questionarioContador.questoesContadoras[s].respostasContadoras.getValue(key)
                        value++
                        questionarioContador.questoesContadoras[s].respostasContadoras.set(key, value)
                    }
                    R.id.rdResposta2 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 2

                        val key = questionarioAtual.questoes[s].respostas[2]
                        var value = questionarioContador.questoesContadoras[s].respostasContadoras.getValue(key)
                        value++
                        questionarioContador.questoesContadoras[s].respostasContadoras.set(key, value)
                    }
                    R.id.rdResposta3 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 3

                        val key = questionarioAtual.questoes[s].respostas[3]
                        var value = questionarioContador.questoesContadoras[s].respostasContadoras.getValue(key)
                        value++
                        questionarioContador.questoesContadoras[s].respostasContadoras.set(key, value)
                    }
                    R.id.rdResposta4 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 4

                        val key = questionarioAtual.questoes[s].respostas[4]
                        var value = questionarioContador.questoesContadoras[s].respostasContadoras.getValue(key)
                        value++
                        questionarioContador.questoesContadoras[s].respostasContadoras.set(key, value)
                    }
                    R.id.rdResposta5 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 5

                        val key = questionarioAtual.questoes[s].respostas[5]
                        var value = questionarioContador.questoesContadoras[s].respostasContadoras.getValue(key)
                        value++
                        questionarioContador.questoesContadoras[s].respostasContadoras.set(key, value)
                    }
                    R.id.rdResposta6 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 6

                        val key = questionarioAtual.questoes[s].respostas[6]
                        var value = questionarioContador.questoesContadoras[s].respostasContadoras.getValue(key)
                        value++
                        questionarioContador.questoesContadoras[s].respostasContadoras.set(key, value)
                    }
                    R.id.rdResposta7 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 7

                        val key = questionarioAtual.questoes[s].respostas[7]
                        var value = questionarioContador.questoesContadoras[s].respostasContadoras.getValue(key)
                        value++
                        questionarioContador.questoesContadoras[s].respostasContadoras.set(key, value)
                    }
                    R.id.rdResposta8 -> {
                        questionarioAtual.questoes[s].respostaEscolhida = 8

                        val key = questionarioAtual.questoes[s].respostas[8]
                        var value = questionarioContador.questoesContadoras[s].respostasContadoras.getValue(key)
                        value++
                        questionarioContador.questoesContadoras[s].respostasContadoras.set(key, value)
                    }
                }

                radioGroup.clearCheck()
                var questoesSize = questionarioAtual.questoes.size

                if(s < questoesSize - 1) {
                    s = ++questionarioAtual.status
                    carregaQuestaoNaTela(questionarioAtual.questoes[s])
                }
                else {
                    btnProx.isClickable = false
                    pesquisa.questionarios.add(questionarioAtual)
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
        val context = this@FormActivity
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Questionário Finalizado")
        builder.setMessage("Deseja realizar novo questionário ou finalizar a pesquisa?")

        builder.setPositiveButton("Finalizar Pesquisa") {dialog, which ->
            Log.d("traumadeface", questionarioContador.toString())
            val intent = Intent(context, PesquisaActivity::class.java)
            intent.putExtra(PesquisaActivity.EXTRA_PESQUISA, pesquisa)
            startActivity(intent)
        }
        builder.setNegativeButton("Novo Questionário") {dialog, which ->
            Toast.makeText(this, "Novo Questionário Gerado", Toast.LENGTH_SHORT).show()
            btnProx.isClickable = true
            questionarioAtual = criarNovoQuestionario()
            carregaQuestaoNaTela(questionarioAtual.questoes[0])
        }
        val dialog = builder.create()
        var window = dialog.window
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        dialog.show()
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


        return Questionario(0, mutableListOf<Questao>(q0, q1, q2, q3, q4, q5))
    }

    fun criarQuestionarioContador(): QuestionarioContador {

        var r0 = linkedMapOf("0 a 10" to 0, "11 a 20" to 0, "21 a 30" to 0, "31 a 40" to 0, "41 a 50" to 0, "51 a 60" to 0, "maior que 60" to 0, "" to 0, "" to 0)
        val q0 = QuestaoContadora("Faixa etária", r0)

        var r1 = linkedMapOf("masculino" to 0, "feminino" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0)
        val q1 = QuestaoContadora("Sexo", r1)

        var r2 = linkedMapOf("solteiro" to 0, "casado" to 0, "união estável" to 0, "viúvo" to 0, "divorciado" to 0, "" to 0, "" to 0, "" to 0, "" to 0)
        val q2 = QuestaoContadora("Estado civil", r2)

        var r3 = linkedMapOf("capital e região metropolitana" to 0, "interior" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0)
        val q3 = QuestaoContadora("Local da ocorrência", r3)

        var r4 = linkedMapOf(
                "trabalhador assalariado" to 0,
                "trabalhador autônomo" to 0,
                "desempregado" to 0,
                "aposentado" to 0,
                "não trabalha" to 0, "" to 0, "" to 0, "" to 0, "" to 0)
        val q4 = QuestaoContadora("Vínculo empregatício", r4)

        var r5 = linkedMapOf(
                "analfabeto" to 0,
                "ensino fundamental incompleto" to 0,
                "ensino fundamental completo" to 0,
                "ensino médio incompleto" to 0,
                "ensino médio completo" to 0,
                "ensino superior completo" to 0,
                "ensino superior incompleto" to 0, "" to 0, "" to 0)
        val q5 = QuestaoContadora("Escolaridade", r5)

        var r6 = linkedMapOf("" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0)
        val q6 = QuestaoContadora("", r6)

        var r7 = linkedMapOf("" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0)
        val q7 = QuestaoContadora("", r7)

        var r8 = linkedMapOf("" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0, "" to 0)
        val q8 = QuestaoContadora("", r8)


        return QuestionarioContador(mutableListOf<QuestaoContadora>(q0, q1, q2, q3, q4, q5))
    }

}
