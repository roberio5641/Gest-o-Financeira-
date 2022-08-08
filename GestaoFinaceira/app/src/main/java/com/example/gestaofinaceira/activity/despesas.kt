package com.example.gestaofinaceira.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.gestaofinaceira.R
import com.example.gestaofinaceira.entity.Despesa
import com.example.gestaofinaceira.repository.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class despesas : AppCompatActivity() {

    private lateinit var mDespesaName: EditText
    private lateinit var mDespesaValor: EditText
    private lateinit var mDespesaButton: Button

    private var mUserId = -1
    private var mDespesaId = -1

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesas)

        mUserId = intent.getIntExtra("userId", -1)
        mDespesaId = intent.getIntExtra("despesasId", -1)

        mDespesaName = findViewById(R.id.despesas_editTextTextPersonName)
        mDespesaValor = findViewById(R.id.despesas_editTextNumberDecimal2)

        mDespesaButton = findViewById(R.id.despesas_button)
        mDespesaButton.setOnClickListener {

            val name = mDespesaName.text.toString().trim()
            val valor = mDespesaValor.text.toString().trim()

            if (mDespesaId != -1) {

                GlobalScope.launch {
                    val DespesasDAO = DatabaseUtil
                        .getInstance(applicationContext)
                        .getDespesaDAO()


                    val despesas = Despesa(mDespesaId, name, valor, mUserId)
                    DespesasDAO.update(despesas)

                    handler.post {
                        val dialog = AlertDialog.Builder(this@despesas)
                            .setTitle("gestao finamceira")
                            .setMessage("Despesa foi atualizada")
                            .setCancelable(false)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                                finish()
                            }.create()
                        dialog.show()

                    }
                }

            } else {

                if (valor.isBlank()) {
                    mDespesaValor.error = "campo obrigatorio"
                    return@setOnClickListener
                }
                GlobalScope.launch {
                    val DespesasDAO = DatabaseUtil
                        .getInstance(applicationContext)
                        .getDespesaDAO()

                    val despesa = Despesa(name = name, valor = valor, userId = mUserId)


                    DespesasDAO.insert(despesa)

                    handler.post {
                        val dialog = AlertDialog.Builder(this@despesas)
                            .setTitle("gestao finamceira")
                            .setMessage("Despesa foi registrada")
                            .setCancelable(false)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                                finish()
                            }.create()
                        dialog.show()

                    }


                }
            }

        }

    }


    override fun onResume() {
        super.onResume()

        if (mDespesaId != -1) {

            GlobalScope.launch {

                val despesaDAO = DatabaseUtil
                    .getInstance(applicationContext)
                    .getDespesaDAO()
                val despesa = despesaDAO.findById(mDespesaId)

                handler.post {
                    mDespesaButton.text = "Atualizar"

                    mDespesaName.text = Editable.Factory.getInstance().newEditable(despesa.name)
                    mDespesaValor.text = Editable.Factory.getInstance().newEditable(despesa.valor)
                }
            }

            mDespesaButton.text = "atulizar"
        } else {
            mDespesaButton.text = "cadastrar"
        }

    }
}
