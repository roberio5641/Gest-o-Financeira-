package com.example.gestaofinaceira.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.gestaofinaceira.R
import com.example.gestaofinaceira.entity.Receita
import com.example.gestaofinaceira.repository.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Receitas : AppCompatActivity() {

    private lateinit var mTextName: TextView
    private lateinit var mValorReceita: TextView
    private lateinit var mButtonConfirmar: Button

    private var mUserId = -1
    private var mReceitaId = -1

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receitas)

        mUserId = intent.getIntExtra("userId", -1)
        mReceitaId = intent.getIntExtra("receitasId", -1)

        mTextName = findViewById(R.id.receita_editTextTextPersonName)
        mValorReceita = findViewById(R.id.receita_editTextNumberDecimal)


        mButtonConfirmar = findViewById(R.id.receita_button)
        mButtonConfirmar.setOnClickListener {
            val name = mTextName.text.toString().trim()
            val valor = mValorReceita.text.toString().trim()


            if (mReceitaId != -1) {

                GlobalScope.launch {
                    val ReceitaDAO = DatabaseUtil
                        .getInstance(applicationContext)
                        .getReceitaDAO()

                    val receitas = Receita(mReceitaId, name, valor, mUserId)
                    ReceitaDAO.update(receitas)

                    handler.post {
                        val dialog = AlertDialog.Builder(this@Receitas)
                            .setTitle("gestao finamceira")
                            .setMessage("Receita foi atualizada")
                            .setCancelable(false)
                            .setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss()
                                finish()
                            }.create()
                        dialog.show()
                    }
                }

            } else {

                if (valor.isBlank()) {
                    mValorReceita.error = "campo obrigatorio"
                    return@setOnClickListener
                }
                GlobalScope.launch {
                    val receitaDAO = DatabaseUtil
                        .getInstance(applicationContext)
                        .getReceitaDAO()

                    var receita = Receita(name = name, valor = valor, userId = mUserId)

                    receitaDAO.insert(receita)

                    handler.post {
                        val dialog = AlertDialog.Builder(this@Receitas)
                            .setTitle("gestão financeira")
                            .setMessage("Receita foi registrada")
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

        if (mReceitaId != -1) {
            GlobalScope.launch {
                val ReceitaDAO = DatabaseUtil
                    .getInstance(applicationContext)
                    .getReceitaDAO()
                val receita = ReceitaDAO.findById(mReceitaId)

                handler.post {
                    mButtonConfirmar.text = "Atualizar"

                    //mTextName.text = Editable.Factory.getInstance().newEditable(receita.name)
                    //mValorReceita.text = Editable.Factory.getInstance().newEditable(receita.valor)
                }
            }

            mButtonConfirmar.text = "Atualizar"
        } else {
            mButtonConfirmar.text = "Cadastrar"
        }

    }

}