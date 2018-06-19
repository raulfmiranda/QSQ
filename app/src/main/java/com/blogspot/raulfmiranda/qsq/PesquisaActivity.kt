package com.blogspot.raulfmiranda.qsq


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.blogspot.raulfmiranda.qsq.model.Pesquisa
import com.blogspot.raulfmiranda.qsq.model.Questionario
import kotlinx.android.synthetic.main.activity_pesquisa.*
import android.os.Build
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import com.blogspot.raulfmiranda.qsq.model.QuestaoContadora
import com.blogspot.raulfmiranda.qsq.model.QuestionarioContador
import org.jetbrains.anko.*
import android.os.StrictMode


class PesquisaActivity : AppCompatActivity() {

    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>(), QuestionarioContador(mutableListOf<QuestaoContadora>()))
    val WRITE_EXTERNAL_STORAGE_CODE = 1
    val QTDE_PAGINAS = 8
//    val relatorioPath = "/sdcard/QSQRel.pdf"
    val relatorioPath = Environment.getExternalStorageDirectory().path + "/QSQRel.pdf"

    companion object {
        val EXTRA_PESQUISA = "PESQUISA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)

        // Para abrir o pdf API >= 24 (Nougat)
        if(Build.VERSION.SDK_INT >= 24) {
            // Por causa da exceção: android.os.FileUriExposedException: file:///sdcard/QSQRel.pdf exposed beyond app through Intent.getData()
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
        }

        val titulo = "Dados Estatísticos da Pesquisa"
        supportActionBar?.title = titulo

        pesquisa = intent.extras.get(EXTRA_PESQUISA) as Pesquisa

        txtResumo.movementMethod = ScrollingMovementMethod()
        txtResumo.text = gerarRelatorio()

        btnGerarRelatorio.setOnClickListener {
            Util.verificarPermissao(this@PesquisaActivity, txtResumo.text.toString(), relatorioPath, QTDE_PAGINAS)
        }

        btnCompartilhar.setOnClickListener {
            share(txtResumo.text.toString(), titulo)
        }
    }

    fun gerarRelatorio():String {

        val qtdQuestionarios = pesquisa.questionarios.size
        val qtdQuestPorQuestionario = pesquisa.questionarios[0].questoes.size
        val qtdTotalQuest = qtdQuestionarios * qtdQuestPorQuestionario
        var resumo = "Quantidade de questionários: $qtdQuestionarios\nQuantidade de questões por questionário: $qtdQuestPorQuestionario\nQuantidade total de questões: $qtdTotalQuest"

        var questoesContadoras = pesquisa.questionarioContador.questoesContadoras

        for(qc in questoesContadoras) {
            val perg = qc.pergunta
            resumo = resumo + "\n\n $perg\n"
            for(resp in qc.respostasContadoras) {
                val key = resp.key
                if(!key.isNullOrBlank()) {
                    val value = resp.value
                    var perc = (value.toDouble() / qtdQuestionarios.toDouble()) * 100
                    var percFormat = "%.1f".format(perc)
                    resumo = resumo + "\n · $key (${resp.value}/$qtdQuestionarios = $percFormat%)"
                }
            }
        }
        return resumo
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_CODE -> Util.gerarPdf(this@PesquisaActivity, txtResumo.text.toString(), relatorioPath, QTDE_PAGINAS)
            else -> {
                Log.d("QSQ", "Usuario nao aceitou permissao?")
                toast("Não foi possível gerar o PDF. É necessário permitir o acesso.")
            }
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        alert("Deseja fechar o aplicativo?") {
            positiveButton("Sim") {
                finishAffinity()
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
