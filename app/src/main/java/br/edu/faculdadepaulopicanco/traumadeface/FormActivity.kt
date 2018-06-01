package br.edu.faculdadepaulopicanco.traumadeface

import android.content.Intent
import android.os.Build
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
import org.jetbrains.anko.alert

class FormActivity : AppCompatActivity() {

    var questionarioAtual: Questionario = criarNovoQuestionario()
    var questionarioContador: QuestionarioContador = criarQuestionarioContador(questionarioAtual)
    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>(), questionarioContador)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        supportActionBar?.title = "Questionário da Pesquisa"

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

    fun criarNovoQuestionario(): Questionario {
        val nsa = "não se aplica"

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

        var r6 = mutableListOf("acidente automobilístico", "agressão física", "outros", "", "", "", "", "", "")
        val q6 = Questao("Agente etiológico", r6, -1)

        var r7 = mutableListOf("automóvel", "motocicleta", "atropelamento", nsa, "", "", "", "", "")
        val q7 = Questao("Tipo de acidente", r7, -1)

        var r8 = mutableListOf("dia útil", "final de semana", nsa, "", "", "", "", "", "")
        val q8 = Questao("Dia acidente", r8, -1)

        var r9 = mutableListOf("madrugada", "manhã", "tarde", "noite", nsa, "", "", "", "")
        val q9 = Questao("Horário acidente", r9, -1)

        var r10 = mutableListOf("violência doméstica", "violência interpessoal", "assalto", nsa, "", "", "", "", "")
        val q10 = Questao("Tipo de agressão", r10, -1)

        var r11 = mutableListOf("masculino", "feminino", nsa, "", "", "", "", "", "")
        val q11 = Questao("Sexo do agressor", r11, -1)

        var r12 = mutableListOf("companheiro", "ex-companheiro", "familiar", "conhecido", "estranho", nsa, "", "", "")
        val q12 = Questao("Agressor", r12, -1)

        var r13 = mutableListOf("arma branca", "arma de fogo", "objeto contuso", "não informado", nsa, "", "", "", "")
        val q13 = Questao("Arma utilizada", r13, -1)

        var r14 = mutableListOf("dia útil", "final de semana", nsa, "", "", "", "", "", "")
        val q14 = Questao("Data agressão", r14, -1)

        var r15 = mutableListOf("madrugada", "manhã", "tarde", "noite", nsa, "", "", "", "")
        val q15 = Questao("Horário agressão", r15, -1)

        var r16 = mutableListOf("sim", "não", "", "", "", "", "", "", "")
        val q16 = Questao("Lesão facial tecido mole", r16, -1)

        var r17 = mutableListOf("sim", "não", "", "", "", "", "", "", "")
        val q17 = Questao("Lesão facial fratura óssea", r17, -1)

        var r18 = mutableListOf("sim", "não", "", "", "", "", "", "", "")
        val q18 = Questao("Lesão facial fratura dentoalveolar", r18, -1)

        var r19 = mutableListOf("sim", "não", "", "", "", "", "", "", "")
        val q19 = Questao("Lesão sem interesse odontolegal", r19, -1)

        var r20 = mutableListOf("contundente", "perfuro-contundente", "cortante",
                "perfuro-cortante", "perfurante", "corto-contusa", "não informado", "", "")
        val q20 = Questao("Tipo Lesão Tecido Mole", r20, -1)

        var r21 = mutableListOf("ferida contusa", "escoriação", "equimose", "hematoma",
                "mordida humana", "laceração", "edema", "cicatriz", "")
        val q21 = Questao("Lesão tecido mole", r21, -1)

        var r22 = mutableListOf("lábil superior", "lábil inferior", "periorbitária",
                "mentoniana", "mandibular", "bucinadora maceterina", "nasal",
                "mucosa oral", "zigomática")
        val q22 = Questao("Localização da lesão no tecido mole", r22, -1)

        var r23 = mutableListOf("mandibula", "maxila", "complexo zigomático orbitário",
                "OPN", "frontal", "NOE", "", "", "")
        val q23 = Questao("Localização de fratura na face", r23, -1)

        var r24 = mutableListOf("sim", "não", "", "", "", "", "", "", "")
        val q24 = Questao("Lesão dentária", r24, -1)

        var r25 = mutableListOf("coronária", "avulsão", "mobilidade", "luxação lateral",
                "extraído por causa do trauma", "não informado", "subluxação", "fratura de prótese", "extrusão")
        val q25 = Questao("Tipo de Lesão Dentária", r25, -1)

        var r26 = mutableListOf("0 a 8", "9 a 16", "17 a 24", "25 a 32", "", "", "", "", "")
        val q26 = Questao("Quantidade de dentes traumatizados", r26, -1)

        var r27 = mutableListOf("sim", "não", "", "", "", "", "", "", "")
        val q27 = Questao("Há ofensa à integridade corporal ou à saúde do paciente?", r27, -1)

        var r28 = mutableListOf("Contundente", "corto-contundente", "perfuro-cortante",
                "perfuro-contundente", "não informado", "", "", "", "")
        val q28 = Questao("Qual o instrumento ou meio que produziu a ofensa?", r28, -1)

        var r29 = mutableListOf("sim", "não", "sem elementos para afirmar ou negar", "prejudicado", "", "", "", "", "")
        val q29 = Questao("Foi produzido por meio de veneno, fogo, explosivo ou tortura, ou outro meio insidioso ou cruel?", r29, -1)

        var r30 = mutableListOf("sim", "não", "sem elementos para afirmar ou negar",
                "prejudicado", "retornar para novo exame com laudo profissional e/ou exame complementar", "", "", "", "")
        val q30 = Questao("Resultou em incapacidade para as ocupações habituais por mais de trinta dias?", r30, -1)

        var r31 = mutableListOf("sim", "não", "sem elementos para afirmar ou negar", "prejudicado", "", "", "", "", "")
        val q31 = Questao("Houve perigo de vida?", r31, -1)

        var r32 = mutableListOf("sim, causou debilidade permanente da função mastigatória",
                "não", "sem elementos para afirmar ou negar", "prejudicado",
                "retornar para novo exame com o laudo profissional e/ou exame complementar", "", "", "", "")
        val q32 = Questao("Resultou em debilidade permanente, perda ou inutilização de membro, sentido ou função?", r32, -1)

        var r33 = mutableListOf("sim, causou deformidade permanente da função mastigatória",
                "não", "sem elementos para afirmar ou negar", "prejudicado",
                "retornar para novo exame com o laudo profissional e/ou exame complementar", "", "", "", "")
        val q33 = Questao("Resultou em incapacidade permanente para o trabalho, enfermidade incurável ou deformidade permanente?", r33, -1)

        return Questionario(0, mutableListOf<Questao>(q0, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16, q17, q18, q19, q20, q21, q22, q23, q24, q25, q26, q27, q28, q29, q30, q31, q32, q33))
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
