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
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.method.ScrollingMovementMethod
import br.edu.faculdadepaulopicanco.traumadeface.model.QuestaoContadora
import br.edu.faculdadepaulopicanco.traumadeface.model.QuestionarioContador
import org.jetbrains.anko.*


class PesquisaActivity : AppCompatActivity() {

    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>(), QuestionarioContador(mutableListOf<QuestaoContadora>()))
    val WRITE_EXTERNAL_STORAGE_CODE = 1
    val relatorioPath = "/sdcard/TraumaDeFaceRel.pdf"

    companion object {
        val EXTRA_PESQUISA = "PESQUISA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)

        val titulo = "Dados Estatísticos da Pesquisa"
        supportActionBar?.title = titulo

        pesquisa = intent.extras.get(EXTRA_PESQUISA) as Pesquisa

        txtResumo.movementMethod = ScrollingMovementMethod()
        txtResumo.text = gerarRelatorio()

        btnGerarRelatorio.setOnClickListener {
            verificarPermissao()
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
            resumo = resumo + "\n\n # $perg\n"
            for(resp in qc.respostasContadoras) {
                val key = resp.key
                if(!key.isNullOrBlank()) {
                    val value = resp.value
                    var perc = (value.toDouble() / qtdQuestionarios.toDouble()) * 100
                    var percFormat = "%.1f".format(perc)
                    resumo = resumo + "\n · $key ($percFormat%)"
                }
            }
        }
        return resumo
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

        // Create Page A4 size
        val widthA4 = 595
        val heightA4 = 842
        var pagesInfo = mutableListOf<PdfDocument.PageInfo>()
        for (i in 1..11) {
            pagesInfo.add(PdfDocument.PageInfo.Builder(widthA4, heightA4, i).create())
        }

        var textPaint = TextPaint()
        textPaint.isAntiAlias = true
        textPaint.textSize = 12 * resources.displayMetrics.density
        textPaint.color = Color.BLACK

        var lines = txtResumo.text.toString().lines()
        var i = 0

        for (pgInf in pagesInfo) {
            var page = document.startPage(pgInf)
            var canvas = page.canvas
            val paint = Paint()
            paint.color = Color.WHITE
            paint.style = Paint.Style.FILL
            canvas.drawPaint(paint)

            paint.color = Color.BLACK
            paint.textSize = 14f

            while(i < lines.size) {
                if(i > 25 * pgInf.pageNumber) {
                    break
                }
                else {
                    var staticLayout = StaticLayout(lines[i], textPaint, canvas.width, Layout.Alignment.ALIGN_NORMAL, 0.8f, 0f, false)
                    canvas.save()
                    canvas.translate(0f, 30f)
                    staticLayout.draw(canvas)
                    i++
                }
            }
            document.finishPage(page)
        }

        // write the document content
        val targetPdf = relatorioPath
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
            val targetPdf = relatorioPath
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
            else -> {
                Log.d("traumadeface", "Usuario nao aceitou permissao?")
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
