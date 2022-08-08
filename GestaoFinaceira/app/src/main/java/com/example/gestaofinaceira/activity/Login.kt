package com.example.gestaofinaceira.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.gestaofinaceira.R
import com.example.gestaofinaceira.repository.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Login : AppCompatActivity(), View.OnClickListener {


    private lateinit var mLoginEmail: EditText
    private lateinit var mLoginPassword: EditText

    private lateinit var mLoginButton: Button
    private lateinit var mLoginregister: TextView

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLoginEmail = findViewById(R.id.login_text_editEmail)
        mLoginPassword = findViewById(R.id.login_text_editPassword)

        mLoginButton = findViewById(R.id.login_button_singin)
        mLoginButton.setOnClickListener(this)

        mLoginregister = findViewById(R.id.Login_registrar)
        mLoginregister.setOnClickListener(this)
    }

    override fun onClick(view: View?) {


        when (view?.id) {
            R.id.Login_registrar -> {
                val it = Intent(this, register::class.java)
                startActivity(it)
            }

            R.id.login_button_singin -> {

                val email = mLoginEmail.text.toString().trim()
                val password = mLoginPassword.text.toString().trim()
                var isfilled = true

                if (email.isBlank()) {
                    mLoginEmail.error = "campo obrigatorio"
                    isfilled = false
                }
                if (password.isBlank()) {
                    mLoginPassword.error = "campo obrigatorio"
                    isfilled = false
                }

                if (isfilled) {

                    GlobalScope.launch {
                        val userDAO = DatabaseUtil
                            .getInstance(applicationContext)
                            .getUserDao()

                        val user = userDAO.findByEmail(email)
                        if (user != null) {

                            if (password == user.password) {
                                val it = Intent(this@Login, MainActivity::class.java)
                                it.putExtra("userId", user.id)
                                startActivity(it)
                                finish()
                            } else {
                                handler.post{
                                    val dialog = AlertDialog.Builder(this@Login)
                                        .setTitle("gestao finamceira")
                                        .setMessage("email ou senhas errados")
                                        .setCancelable(false)
                                        .setPositiveButton("OK"){dialog,_ ->
                                            dialog.dismiss()

                                        }
                                    dialog.show()

                                    mLoginEmail.text.clear()
                                    mLoginPassword.text.clear()
                                }
                            }


                        } else {
                            handler.post{
                                val dialog = AlertDialog.Builder(this@Login)
                                    .setTitle("gestao finamceira")
                                    .setMessage("email ou senhas errados")
                                    .setCancelable(false)
                                    .setPositiveButton("OK"){dialog,_ ->
                                        dialog.dismiss()

                                    }
                                dialog.show()

                                mLoginEmail.text.clear()
                                mLoginPassword.text.clear()
                            }
                        }


                    }

                }


            }

        }
    }
}

