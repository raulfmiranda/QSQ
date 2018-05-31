package br.edu.faculdadepaulopicanco.traumadeface

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import br.edu.faculdadepaulopicanco.traumadeface.model.Pesquisa
import br.edu.faculdadepaulopicanco.traumadeface.model.Questionario
import kotlinx.android.synthetic.main.activity_pesquisa.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import android.text.method.ScrollingMovementMethod
import br.edu.faculdadepaulopicanco.traumadeface.model.QuestaoContadora
import br.edu.faculdadepaulopicanco.traumadeface.model.QuestionarioContador


class PesquisaActivity : AppCompatActivity() {

    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>(), QuestionarioContador(mutableListOf<QuestaoContadora>()))
    val WRITE_EXTERNAL_STORAGE_CODE = 1

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
        var resumo = "Quantidade de questionários: $qtdQuestionarios\nQuantidade de questões por questionário: $qtdQuestPorQuestionario\nQuantidade total de questões: $qtdTotalQuest"


        var questoesContadoras = pesquisa.questionarioContador.questoesContadoras

        try {
            for(qc in questoesContadoras) {
                val perg = qc.pergunta
                resumo = resumo + "\n\n$perg"
                for(resp in qc.respostasContadoras) {
                    val key = resp.key
                    if(!key.isNullOrBlank()) {
                        val value = resp.value
                        resumo = resumo + "\n$key( $value )"
                    }
                }
            }
        }
        catch (e: Exception) {
            val erro = e.message
            Log.d("traumadeface", erro)
        }


        txtResumo.movementMethod = ScrollingMovementMethod()
        txtResumo.text = resumo

        var respostasEscolhidas = mutableListOf<Int>()
        for (questionario in pesquisa.questionarios) {
            for(questao in questionario.questoes) {
                respostasEscolhidas.add(questao.respostaEscolhida)
            }
        }

        btnGerarRelatorio.setOnClickListener {
            verificarPermissao()
        }
    }

    fun gerarPdf() {
        var document = PdfDocument()
//        var pageInfo = PdfDocument.PageInfo.Builder(100, 100, 1).create()

        // Start a page

//        var page = document.startPage(pageInfo)
//        var canvas = page.canvas
//        var paint = Paint()
//        paint.color = Color.RED
//        canvas.drawCircle(50f, 50f, 30f, paint)
//        document.finishPage(page)

        // Create Page 2
//        pageInfo = PdfDocument.PageInfo.Builder(500, 500, 2).create()
//        page = document.startPage(pageInfo)
//        canvas = page.canvas
//        paint = Paint()
//        paint.color = Color.BLUE
//        canvas.drawCircle(200f, 200f, 100f, paint)
//        document.finishPage(page)

        // Create Page 3
        var pageInfo = PdfDocument.PageInfo.Builder(1000, 2000, 1).create()
        var page = document.startPage(pageInfo)
        var content = txtResumo
        content.draw(page.canvas)
        document.finishPage(page)

        // write the document content
        val targetPdf = "/sdcard/test.pdf"
        val filePath = File(targetPdf)
        try {
            document.writeTo(FileOutputStream(filePath))
            Toast.makeText(this@PesquisaActivity, "PDF gerado", Toast.LENGTH_LONG).show()
            abrirPdf()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this@PesquisaActivity, "Ocorreu um erro: " + e.toString(),
                    Toast.LENGTH_LONG).show()
        }
        // close the document
        document.close()
    }

    fun verificarPermissao() {
        if (ContextCompat.checkSelfPermission(this@PesquisaActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@PesquisaActivity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this@PesquisaActivity,
                        Array<String>(1){Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else {
            gerarPdf()
        }
    }

    fun abrirPdf() {
        try {
//            val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Documents/" + fileName)
//            val file = Environment.getExternalStoragePublicDirectory("/sdcard/test.pdf")
            val targetPdf = "/sdcard/test.pdf"
            val file = File(targetPdf)
            val intent = Intent("com.adobe.reader")
            intent.type = "application/pdf"
            intent.action = Intent.ACTION_VIEW
            val uri = Uri.fromFile(file)
            intent.setDataAndType(uri, "application/pdf")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this@PesquisaActivity, "Ocorreu um erro ao tentar abrir o arquivo", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_CODE -> gerarPdf()
            else -> Log.d("traumadeface", "Usuario nao aceitou persmissao?")
        }
    }
}
