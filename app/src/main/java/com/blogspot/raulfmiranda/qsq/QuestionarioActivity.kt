package com.blogspot.raulfmiranda.qsq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import com.blogspot.raulfmiranda.qsq.model.Pesquisa
import com.blogspot.raulfmiranda.qsq.model.Questionario
import kotlinx.android.synthetic.main.activity_questionario.*
import org.jetbrains.anko.share
import org.jetbrains.anko.startActivity

class QuestionarioActivity : AppCompatActivity() {

    var pesquisa: Pesquisa? = null
    val QTDE_PAGINAS = 5
    val relatorioPath = Environment.getExternalStorageDirectory().path + "/QSQRelIndividual.pdf"

    companion object {
        val EXTRA_PESQUISA = "PESQUISA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionario)

        Util.strictModeSetVmPolicy()
        txtResumoIndividual.movementMethod = ScrollingMovementMethod()
        val titulo = "QSQ - Resultado Individual"
        supportActionBar?.title = titulo

        pesquisa = intent?.extras?.get(StartActivity.EXTRA_PESQUISA) as? Pesquisa
        val questionarioAtual = pesquisa?.questionarios?.last()
        var txtResumo = ""

        questionarioAtual?.let {
            txtResumo = "Nome do Paciente: ${it.nomePaciente}\n\n"
            it.questoes.forEach {
                txtResumo += "${it.pergunta}\n"
                txtResumo += "Resposta: ${it.respostas[it.respostaEscolhida]}\n\n"
            }
            txtResumoIndividual.text = txtResumo
        }

        btnFinalizarIndividual.setOnClickListener {
            startActivity<PesquisaActivity>(PesquisaActivity.EXTRA_PESQUISA to pesquisa)
        }

        btnNovoIndividual.setOnClickListener {
            startActivity<StartActivity>(StartActivity.EXTRA_PESQUISA to pesquisa)
        }

        btnGerarRelatorioIndividual.setOnClickListener {
            Util.verificarPermissao(this@QuestionarioActivity, txtResumoIndividual.text.toString(), this.relatorioPath, QTDE_PAGINAS)
        }

        btnCompartilharIndividual.setOnClickListener {
            share(txtResumoIndividual.text.toString(), titulo)
        }
    }

    override fun onBackPressed() {
        Util.onBackPressedAlert(this@QuestionarioActivity)
    }
}
