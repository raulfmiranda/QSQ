package com.blogspot.raulfmiranda.qsq

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.blogspot.raulfmiranda.qsq.model.Pesquisa
import com.blogspot.raulfmiranda.qsq.model.Questionario
import kotlinx.android.synthetic.main.activity_pesquisa.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.method.ScrollingMovementMethod
import com.blogspot.raulfmiranda.qsq.R
import com.blogspot.raulfmiranda.qsq.model.QuestaoContadora
import com.blogspot.raulfmiranda.qsq.model.QuestionarioContador
import org.jetbrains.anko.*
import android.os.StrictMode




class PesquisaActivity : AppCompatActivity() {

    var pesquisa: Pesquisa = Pesquisa(mutableListOf<Questionario>(), QuestionarioContador(mutableListOf<QuestaoContadora>()))
    val WRITE_EXTERNAL_STORAGE_CODE = 1
    val relatorioPath = "/sdcard/QSQRel.pdf"

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
        for (i in 1..8) {
            pagesInfo.add(PdfDocument.PageInfo.Builder(widthA4, heightA4, i).create())
        }

        var textPaint = TextPaint()
        textPaint.isAntiAlias = true
//        textPaint.textSize = 12 * resources.displayMetrics.density
        textPaint.textSize = 10 * resources.displayMetrics.density
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
//            paint.textSize = 14f
            paint.textSize = 10f

            while(i < lines.size) {
//                if(i > 25 * pgInf.pageNumber) {
                if(i > 29 * pgInf.pageNumber) {
                    break
                }
                else {
                    var staticLayout = StaticLayout(lines[i], textPaint, canvas.width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true)
                    canvas.save()
//                    canvas.translate(0f, 30f)
                    canvas.translate(0f, 25f)
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

                // TODO:Apenas copiei o mesmo que está no ELSE abaixo.
                ActivityCompat.requestPermissions(this@PesquisaActivity,
                        Array<String>(1){Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_CODE);


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
