package com.example.gestaofinaceira.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaofinaceira.R
import com.example.gestaofinaceira.adpter.DespesaAdapter
import com.example.gestaofinaceira.adpter.DespesaListener
import com.example.gestaofinaceira.adpter.ReceitaAdapter
import com.example.gestaofinaceira.adpter.ReceitaListener
import com.example.gestaofinaceira.entity.UserWithDespesas
import com.example.gestaofinaceira.repository.DatabaseUtil
import com.example.gestaofinaceira.repository.ReceitaDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), DespesaListener, ReceitaListener {

    private lateinit var  mMainTaskListR: RecyclerView
    private lateinit var  mMainTaskListD: RecyclerView

    private lateinit var mDespesaAdapter: DespesaAdapter
    private lateinit var mReceitaAdapter: ReceitaAdapter

    private lateinit var mMainAddR: Button
    private lateinit var mMainAddD: Button

    private var mUserId =-1
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mUserId = intent.getIntExtra("userId", -1)


        mMainTaskListR = findViewById(R.id.Main_recyclerView_receitas)
        mMainTaskListD = findViewById(R.id.Main_recyclerView_despesas)





        mMainAddR = findViewById(R.id.Task_buttom_receita)
        mMainAddD= findViewById(R.id.Task_buttom_despesa)
        mMainAddD.setOnClickListener{
            val it =Intent(this, despesas::class.java)
            it.putExtra("userId",mUserId)
            startActivity(it)
        }


        mMainAddR.setOnClickListener {
            val it =Intent(this, Receitas::class.java)
            it.putExtra("userId",mUserId)
            startActivity(it)
        }



    }

    override fun onStart() {
        super.onStart()


        GlobalScope.launch {
            val DespesaDAO = DatabaseUtil
                .getInstance(applicationContext)
                .getDespesaDAO()

            val UserWithDespesas = DespesaDAO.findAllbyUserId(mUserId)

            handler.post{

                mDespesaAdapter = DespesaAdapter(UserWithDespesas.despesas.reversed().take(5))
                mDespesaAdapter.setOnItemListener(this@MainActivity)

                mMainTaskListD.layoutManager= LinearLayoutManager(this@MainActivity)
                mMainTaskListD.adapter = mDespesaAdapter
            }


            val ReceitaDAO = DatabaseUtil
                .getInstance(applicationContext)
                .getReceitaDAO()

            val userWithReceitas = ReceitaDAO.findAllbyUserId(mUserId)

            handler.post {

                mReceitaAdapter =  ReceitaAdapter(userWithReceitas.receitas.reversed().take(5))
                mReceitaAdapter.setOnItemListener(this@MainActivity)

                mMainTaskListR.layoutManager = LinearLayoutManager(this@MainActivity)
                mMainTaskListR.adapter = mReceitaAdapter
            }
        }

    }



    // Receita
    override fun onItemClickListenerR(view: View, position: Int) {
        val it = Intent(this, Receitas::class.java)
        it.putExtra("userId", mUserId)
        it.putExtra("receitasId", mReceitaAdapter.receitas[position].id )
        startActivity(it)

    }

    override fun onItemLongClickListenerR(view: View, position: Int) {
        val receitas = mReceitaAdapter.receitas[position]

        val dialog = AlertDialog.Builder(this)
            .setTitle("gestao finamceira")
            .setMessage("Excluir recieta?")
            .setCancelable(true)
            .setPositiveButton("Sim") { dialog, _ ->


                GlobalScope.launch {
                    val ReceitaDAO = DatabaseUtil
                        .getInstance(applicationContext)
                        .getReceitaDAO()

                    ReceitaDAO.delete(receitas)
                    val receitas = ReceitaDAO.findAll()

                    handler.post {
                        mReceitaAdapter.receitas = receitas
                        mReceitaAdapter.notifyItemRemoved(position)
                    }
                }


                dialog.dismiss()
            }

            .setNegativeButton("Não"){dialog, _ ->
                dialog.dismiss()

            }
            .create()
        dialog.show()


    }

    //Despesa
    override fun onItemClickListenerD(view: View, position: Int) {
        val it = Intent(this, despesas::class.java)
        it.putExtra("userId", mUserId)
        it.putExtra("despesasId", mDespesaAdapter.despesas[position].id )
        startActivity(it)
    }

    override fun onItemLongClickListenerD(view: View, position: Int) {

        val despesas = mDespesaAdapter.despesas[position]

        val dialog = AlertDialog.Builder(this)
            .setTitle("gestao finamceira")
            .setMessage("Excluir despesa?")
            .setCancelable(true)
            .setPositiveButton("Sim") { dialog, _ ->


                GlobalScope.launch {
                    val DespesaDao = DatabaseUtil
                        .getInstance(applicationContext)
                        .getDespesaDAO()

                    DespesaDao.delete(despesas)
                    val despesa = DespesaDao.findAll()

                    handler.post {
                        mDespesaAdapter.despesas = despesa
                        mDespesaAdapter.notifyItemRemoved(position)
                    }
                }


                dialog.dismiss()
            }
            .setNegativeButton("Não"){dialog, _ ->
                dialog.dismiss()

            }
            .create()
         dialog.show()

    }



}

