package com.blogspot.raulfmiranda.qsq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blogspot.raulfmiranda.qsq.R
import com.blogspot.raulfmiranda.qsq.model.Pesquisa
import com.blogspot.raulfmiranda.qsq.model.QuestaoContadora
import com.blogspot.raulfmiranda.qsq.model.Questionario
import com.blogspot.raulfmiranda.qsq.model.QuestionarioContador
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.startActivity

class StartActivity : AppCompatActivity() {

    var pesquisa: Pesquisa? = null
    var nomePaciente: String = ""
    var localPesquisa: String = ""
    var idadePaciente: String = ""

    companion object {
        val EXTRA_PESQUISA = "PESQUISA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar?.title = "Questionário de Saúde e Quedas"

        pesquisa = intent?.extras?.get(EXTRA_PESQUISA) as? Pesquisa

        btnIniciar.setOnClickListener {
            if(!edtNomePaciente.text.isNullOrBlank())
                nomePaciente = edtNomePaciente.text.toString()

            if(!edtLocal.text.isNullOrBlank())
                localPesquisa = edtLocal.text.toString()

            if(!edtIdade.text.isNullOrBlank())
                idadePaciente = edtIdade.text.toString()

            startActivity<FormActivity>(
                    FormActivity.EXTRA_NOME_PACIENTE to nomePaciente,
                    FormActivity.EXTRA_LOCAL_PESQUISA to localPesquisa,
                    FormActivity.EXTRA_IDADE_PACIENTE to idadePaciente,
                    FormActivity.EXTRA_PESQUISA to pesquisa)
        }

    }
}
