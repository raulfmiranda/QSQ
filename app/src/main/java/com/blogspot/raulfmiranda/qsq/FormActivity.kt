package com.blogspot.raulfmiranda.qsq

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.WindowManager
import android.widget.*
import com.blogspot.raulfmiranda.qsq.R
import com.blogspot.raulfmiranda.qsq.model.*
import kotlinx.android.synthetic.main.activity_form.*
import org.jetbrains.anko.alert

class FormActivity : AppCompatActivity() {

    var questionarioAtual: Questionario = criarNovoQuestionario()
    var questionarioContador: QuestionarioContador = criarQuestionarioContador(questionarioAtual)
    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>(), questionarioContador)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        txtPergunta.movementMethod = ScrollingMovementMethod()
//        supportActionBar?.title = "Questionário de Saúde e Quedas"

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
//                    showEndAlert()
                }
            }
            else {
                Toast.makeText(this, "Escolha uma opção.", Toast.LENGTH_SHORT).show()
            }

            txtMostrarImg.setOnClickListener {
                if(frameImg.visibility == FrameLayout.VISIBLE) {
                    txtMostrarImg.setText("Mostrar imagem")
                    frameImg.visibility = FrameLayout.GONE
                } else {
                    frameImg.visibility = FrameLayout.VISIBLE
                    txtMostrarImg.setText("Esconder imagem")
                }
            }

            frameImg.setOnClickListener {
                txtMostrarImg.setText("Mostrar imagem")
                it.visibility = FrameLayout.GONE
            }
        }

    }

    fun carregaQuestaoNaTela(questao: Questao) {

        txtPergunta.text = questao.pergunta

//        for(i in questao.respostas.indices) {
        for (i in 0..8) {
            var rdResposta = radioGroup.getChildAt(i) as RadioButton
            if(!questao.respostas[i].isNullOrBlank()) {
                rdResposta.text = questao.respostas[i]
                rdResposta.visibility = RadioButton.VISIBLE

            } else {
                rdResposta.text = ""
                rdResposta.visibility = RadioButton.GONE
//                rdResposta.visibility = RadioButton.INVISIBLE
            }
        }

        if(questao.respostas.size > 9) {
            rdResposta9.visibility = RadioButton.VISIBLE
            edtResposta.visibility = EditText.VISIBLE
            edtResposta.setText("")
            edtResposta.clearFocus()
            edtResposta.isCursorVisible = false
            rdResposta9.text = questao.respostas[9]
        } else {
            edtResposta.setText("")
            edtResposta.isCursorVisible = false
            rdResposta9.visibility = RadioButton.GONE
            edtResposta.visibility = EditText.GONE
        }

        if(questionarioAtual.status == 21) {
            txtMostrarImg.visibility = TextView.VISIBLE
        } else {
            txtMostrarImg.visibility = TextView.GONE
            frameImg.visibility = FrameLayout.GONE
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

    fun criarNovoQuestionario(): Questionario {
        val nsa = "não se aplica"

        var r0 = mutableListOf("Muito má", "Má", "Razoável", "Boa", "Excelente", "", "", "", "")
        val q0 = Questao("1. Estado de Saúde Geral\n1.1. Considera que, actualmente, a sua saúde é:", r0, -1)

        var r1 = mutableListOf("Muito má", "Má", "Razoável", "Boa", "Excelente", "", "", "", "")
        val q1 = Questao("1.1. Actualmente diria que a sua visão, usando ambos os olhos (com óculos ou lentes de contacto, se os utilizar) é:", r1, -1)

        var r2 = mutableListOf("Não", "Sim", "", "", "", "", "", "", "")
        val q2 = Questao("1.2. Usa lentes bifocais?", r2, -1)

        var r3 = mutableListOf("Muito má", "Má", "Razoável", "Boa", "Excelente", "", "", "", "")
        val q3 = Questao("1.3. Considera que, actualmente, a sua audição (com aparelho auditivo, se o utilizar) é", r3, -1)

        var r4 = mutableListOf("Não", "Sim", "", "", "", "", "", "", "")
        val q4 = Questao("1.4. Foi sujeito a alguma intervenção cirúrgica nos últimos 12 meses?", r4, -1)

        var r5 = mutableListOf(nsa, "", "", "", "", "", "", "", "", "Intervenção:")
        val q5 = Questao("1.4.1. Caso tenha sido sujeito a intervensão nos últimos 12 meses, especifique.", r5, -1)

        var r6 = mutableListOf("Não", "Sim", "", "", "", "", "", "", "")
        val q6 = Questao("2. Autonomia\n2.1. É autónomo em todas as tarefas diárias (ex: vestir-se, tomar banho, etc.)?", r6, -1)

        var r7 = mutableListOf("Não", "Sim", "", "", "", "", "", "", "")
        val q7 = Questao("2.2. Utiliza algum auxiliar de marcha (canadiana, bengala, etc)?", r7, -1)

        var r8 = mutableListOf("Não", "Sim", nsa, "", "", "", "", "", "")
        val q8 = Questao("2.2.1. Se sim, este equipamento permite que se desloque autonomamente?", r8, -1)

        var r9 = mutableListOf("Não", "Sim", "", "", "", "", "", "", "")
        val q9 = Questao("3. Doenças Crónicas e Medicação\n3.1. Toma medicamentos actualmente?", r9, -1)

        var r10 = mutableListOf(nsa, "", "", "", "", "", "", "", "", "Quantidade:")
        val q10 = Questao("3.1.1. Caso tome medicamentos actualmente, quantos toma?", r10, -1)

        var r11 = mutableListOf("Não", "Sim", nsa, "", "", "", "", "", "")
        val q11 = Questao("3.1.2. Caso tome medicamentos actualmente, toma medicamentos para doenças do foro psíquico?", r11, -1)

        var r12 = mutableListOf("Nunca", "Ocasionalmente", "Frequentemente", "Sempre", "", "", "", "", "")
        val q12 = Questao("4. Ocorrência de quedas (últimos 12 meses)\n4.1. Tem medo de cair?", r12, -1)

        var r13 = mutableListOf("Não", "Sim", nsa, "", "", "", "", "", "")
        val q13 = Questao("4.1.1. Esse medo de cair o impede-o de realizar alguma(s) das actividades diárias? que se seguem?", r13, -1)

        var r14 = mutableListOf(nsa, "", "", "", "", "", "", "", "", "Quantidade:")
        val q14 = Questao("4.2. No último ano (12 meses) quantas vezes caiu?", r14, -1)

        var r15 = mutableListOf(
                "Dentro da sua casa",
                "À entrada de casa ou no quintal",
                "Fora de casa no exterior",
                "Fora de casa num espaço fechado", nsa, "", "", "", "")
        val q15 = Questao("4.2.1. Em relação à pior queda (consequência mais grave).\nOnde caiu?", r15, -1)

        var r16 = mutableListOf(
                "Escorreguei",
                "Tropecei",
                "Perdi os sentidos",
                "Tive uma tontura",
                "Senti fraqueza nas pernas",
                nsa, "", "", "",
                "Outra:")
        val q16 = Questao("4.2.1. Em relação à pior queda (consequência mais grave).\nPorque caiu?", r16, -1)

        var r17 = mutableListOf(
                "Caminhar",
                "Caminhar a subir (rampa, ladeira, outro)",
                "Caminhar a descer (rampa, ladeira, outro)",
                "Subir escadas",
                "Descer escadas",
                "Baixar ou Levantar",
                "Ultrapassar Obstáculo (passeio, outro)",
                nsa, "",
                "Outra:")
        val q17 = Questao("4.2.1. Em relação à pior queda (consequência mais grave).\nO que estava a fazer?", r17, -1)

        var r18 = mutableListOf(nsa, "", "", "", "", "", "", "", "", "Dias:")
        val q18 = Questao("4.2.1. Em relação à pior queda (consequência mais grave).\n" +
                "Como resultado da queda, quanto tempo esteve impossibilitado de realizar as actividades normais do dia-a-dia?", r18, -1)

        var r19 = mutableListOf("Não", "Sim", nsa, "", "", "", "", "", "")
        val q19 = Questao("4.2.1. Em relação à pior queda (consequência mais grave).\nComo resultado da queda sofreu alguma lesão?", r19, -1)

        var r20 = mutableListOf("Não", "Sim", nsa, "", "", "", "", "", "")
        val q20 = Questao("4.2.1. Em relação à pior queda (consequência mais grave).\nSe sofreu lesão, fez alguma fractura?", r20, -1)

        var r21 = mutableListOf(
                "Pescoço",
                "Ombros",
                "Zona Dorsal",
                "Cotovelos",
                "Zona Lombar",
                "Pulso/Mãos",
                "Coxa/Anca",
                "Joelhos",
                "Tornozelos/Pés")
        val q21 = Questao("4.2.1. Em relação à pior queda (consequência mais grave).\nOnde? Assinale o local na imagem.", r21, -1)

        var r22 = mutableListOf("Positivo", "Negativo", "", "", "", "", "", "", "")
        val q22 = Questao("5.1. TESTE DE ROMBERG -  O médico orienta o paciente para que permaneça, por alguns segundos, em posição vertical, com os pés juntos, inicialmente olhando para a frente. Em seguida, pede para que ele feche os olhos. • A prova de Romberg é positiva quando o paciente apresenta, então, oscilações do corpo, com desequilíbrio e forte tendência à queda, que pode ser: - para qualquer lado e imediatamente após interromper a visão, indicando lesão das vias de sensibilidade proprioceptiva consciente. - sempre para o mesmo lado após pequeno período de latência, o que indica lesão do aparelho vestibular. • No indivíduo normal, nada é observado, mas em caso de labirintopatias, a prova de Romberg é positiva.", r22, -1)

        var r23 = mutableListOf("Positivo", "Negativo", "", "", "", "", "", "", "")
        val q23 = Questao("5.2. Teste de Unterberger-FUKUDA -  paciente marcha parado no mesmo lugar de olhos fechados. Observa-se a rotação para o lado hipoativo se houver lesão vestibular unilateral.", r23, -1)

        var r24 = mutableListOf("Positivo", "Negativo", "", "", "", "", "", "", "")
        val q24 = Questao("5.3. TIME GET UP AND GO (TUGT)  AVALIAÇÃO DE RISCO DE QUEDAS - O idoso deverá estar sentado em uma cadeira com apoio lateral de braço. Solicite ao idoso, que se levante sem apoiar nas laterais da cadeira, caminhe 3 metros, virando 180º e retornando ao ponto de partida, para sentar-se novamente.", r24, -1)

        return Questionario(0, mutableListOf<Questao>(q0, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16, q17, q18, q19, q20, q21, q22, q23, q24))
    }

    fun criarQuestionarioContador(questionario: Questionario): QuestionarioContador {

        var questionarioContador = QuestionarioContador(mutableListOf<QuestaoContadora>())

        for(questao in questionario.questoes) {
            var r = linkedMapOf<String, Int>()
            for(resp in questao.respostas) {
                r.put(resp, 0)
            }
            var questCont = QuestaoContadora(questao.pergunta, r)
            questionarioContador.questoesContadoras.add(questCont)
        }

        return questionarioContador
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        alert("Deseja reiniciar o aplicativo?") {
            positiveButton("Sim") {
                finish()
//                moveTaskToBack(true)
//                android.os.Process.killProcess(android.os.Process.myPid())
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    finishAndRemoveTask()
//                }
//                else {
//                    android.os.Process.killProcess(android.os.Process.myPid())
//                    moveTaskToBack(true)
//                }
            }
            negativeButton("Não") {

            }
        }.show()
    }

}
