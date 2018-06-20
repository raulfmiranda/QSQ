package com.blogspot.raulfmiranda.qsq

import android.Manifest
import android.app.Activity
import android.support.v7.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pesquisa.*
import org.jetbrains.anko.alert
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Util {
    companion object {
        val WRITE_EXTERNAL_STORAGE_CODE = 1

        // Impedir que o dialog seja fechado caso clique fora dele
        fun setFlagsNotTouchModal(dialog: AlertDialog) {
            val window = dialog.window
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        fun strictModeSetVmPolicy() {
            if(Build.VERSION.SDK_INT >= 24) {
                // Por causa da exceção: android.os.FileUriExposedException: file:///sdcard/QSQRel.pdf exposed beyond app through Intent.getData()
                val builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())
            }
        }

        fun onBackPressedAlert(activity: Activity) {
            activity.alert("Deseja fechar o aplicativo?") {
                positiveButton("Sim") {
                    activity.finishAffinity()
                }
                negativeButton("Não") { }
            }.show()
        }

        fun gerarPdf(context: Context, texto: String, path: String, qtdePaginas: Int) {
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
            for (i in 1..qtdePaginas) {
                pagesInfo.add(PdfDocument.PageInfo.Builder(widthA4, heightA4, i).create())
            }

            var textPaint = TextPaint()
            textPaint.isAntiAlias = true
//        textPaint.textSize = 12 * resources.displayMetrics.density
            textPaint.textSize = 10 * context.resources.displayMetrics.density
            textPaint.color = Color.BLACK

            var lines = texto.lines()
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
            val filePath = File(path)
            try {
                document.writeTo(FileOutputStream(filePath))
                Toast.makeText(context, "PDF gerado", Toast.LENGTH_LONG).show()
                abrirPdf(context, path)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Ocorreu um erro: " + e.toString(),
                        Toast.LENGTH_LONG).show()
            }
            // close the document
            document.close()
        }

        private fun abrirPdf(context: Context, path: String) {
            try {
//            val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Documents/" + fileName)
//            val file = Environment.getExternalStoragePublicDirectory("/sdcard/test.pdf")
                val targetPdf = path
                val file = File(targetPdf)
                val intent = Intent("com.adobe.reader")
                intent.type = "application/pdf"
                intent.action = Intent.ACTION_VIEW
                val uri = Uri.fromFile(file)
                intent.setDataAndType(uri, "application/pdf")
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Ocorreu um erro ao tentar abrir o arquivo", Toast.LENGTH_LONG).show()
            }
        }

        fun askUserPermission(activity: Activity) {
            ActivityCompat.requestPermissions(activity,
                    Array<String>(1){ Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_CODE)
        }

        fun verificarPermissao(activity: Activity, texto: String, path: String, qtdePaginas: Int) {
            if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    // TODO:Apenas copiei o mesmo que está no ELSE abaixo.
                    Toast.makeText(activity, "Você deve aceitar o pedido de permissão.", Toast.LENGTH_LONG).show()
                    askUserPermission(activity)

                } else {

                    // No explanation needed, we can request the permission.

                    askUserPermission(activity)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
            else {
                gerarPdf(activity, texto, path, qtdePaginas)
            }
        }
    }
}