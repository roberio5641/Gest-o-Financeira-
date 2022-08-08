package com.example.gestaofinaceira.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.gestaofinaceira.R
import com.example.gestaofinaceira.entity.User
import com.example.gestaofinaceira.repository.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class register : AppCompatActivity(), View.OnClickListener {

    private lateinit var mRegisterName: EditText
    private lateinit var mRegisterEmail: EditText
    private lateinit var mRegisterTelefone: EditText
    private lateinit var mRegisterPassword:EditText
    private lateinit var mRegisterPasswordConfirmation:EditText
    private lateinit var mRegisterSave: Button

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegisterName=findViewById(R.id.Register_editTextx_name)
        mRegisterEmail=findViewById(R.id.Register_editTextx_email)
        mRegisterTelefone=findViewById(R.id.Register_editeNumber_telefone)
        mRegisterPassword=findViewById(R.id.Register_editTextx_password)
        mRegisterPasswordConfirmation=findViewById(R.id.Register_editTextx_passwordConfirmation)


        mRegisterSave=findViewById(R.id.Register_button)
        mRegisterSave.setOnClickListener(this)





    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.Register_button -> doRegisterAction()
        }
    }

    private fun doRegisterAction() {
        val name = mRegisterName.text.toString().trim()
        val email = mRegisterEmail.text.toString().trim()
        val telefone = mRegisterTelefone.text.toString().trim()
        val password = mRegisterPassword.text.toString().trim()
        val passwordConfirmation = mRegisterPasswordConfirmation.text.toString().trim()

        var isFormFilled = true

        if (name.isBlank()){
            mRegisterName.error = "campo obrigatorio"
            isFormFilled = false
        }

        if (email.isBlank()){
            mRegisterEmail.error = "campo obrigatorio"
            isFormFilled = false
        }

        if (telefone.isBlank()){
            mRegisterTelefone.error = "campo obrigatorio"
            isFormFilled = false
        }

        if (password.isBlank()){
            mRegisterPassword.error = "campo obrigatorio"
            isFormFilled = false
        }

        if (passwordConfirmation.isBlank()){
            mRegisterPasswordConfirmation.error = "campo obrigatorio"
            isFormFilled = false
        }

        if(isFormFilled){
            if (password == passwordConfirmation){

                GlobalScope.launch {
                    val user= User(name = name, email = email, telefone = telefone, password = password)

                    val userDAO = DatabaseUtil.getInstance(applicationContext).getUserDao()
                    userDAO.incert(user)

                    handler.post{
                        val dialog = AlertDialog.Builder(this@register)
                            .setTitle("gestao financeira")
                            .setTitle("A pessoas usario do nome $name foi criado com sucesso!")
                            .setPositiveButton("ok"){ dialog, _ ->
                                dialog.dismiss()
                                finish()
                            }
                            .setCancelable(false)
                            .create()

                        dialog.show()
                    }
                }

            }else{
                mRegisterPassword.error = " as senhas nao sao iguis"
                mRegisterPasswordConfirmation.error = "as senhas nao sao iguis"
                return
            }

        }

    }
}