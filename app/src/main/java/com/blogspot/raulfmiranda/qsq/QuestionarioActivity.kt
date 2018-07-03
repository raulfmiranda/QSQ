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
import java.text.SimpleDateFormat
import java.util.*

class QuestionarioActivity : AppCompatActivity() {

    var pesquisa: Pesquisa? = null
    val QTDE_PAGINAS = 5
//    var relatorioPath = Environment.getExternalStorageDirectory().path + "/QSQRelIndividual.pdf"
    var relatorioPath = Environment.getExternalStorageDirectory().path

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

        val sdfFileName = SimpleDateFormat("dd-MM-yyyy_hh-mm", Locale.ITALY)
        val dateFileName = sdfFileName.format(Date())
        relatorioPath += "/QSQIndiv_${dateFileName}.pdf"

        pesquisa = intent?.extras?.get(StartActivity.EXTRA_PESQUISA) as? Pesquisa
        val questionarioAtual = pesquisa?.questionarios?.last()
        var txtResumo = ""

        questionarioAtual?.let {

            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.ITALY)
            val currentDate = sdf.format(Date())

            txtResumo = " Data: ${currentDate}\n"
            txtResumo += " Nome do Paciente: ${it.nomePaciente}\n"
            txtResumo += " Idade do Paciente: ${it.idadePaciente}\n"
            txtResumo += " Local da Pesquisa: ${it.localPesquisa}\n\n"
            it.questoes.forEach {
                txtResumo += " \n ${it.pergunta}\n"
                txtResumo += " Resposta: ${it.respostas[it.respostaEscolhida]}\n"
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
