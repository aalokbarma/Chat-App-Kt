package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtName : EditText;
    private lateinit var edtMobile : EditText;
    private lateinit var edtEmail : EditText;
    private lateinit var edtPassword : EditText;
    private lateinit var btnLogin : Button;
    private lateinit var btnSignUp : Button;

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name);
        edtMobile = findViewById(R.id.edt_mobile);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener{
            val intent = Intent(this@SignUp, Login::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener{
            val name = edtName.text.toString()
            val mobile = edtMobile.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (email == "" || password == "" || mobile == "" || name == ""){
                Toast.makeText(this@SignUp, "Kindly fill the details first.", Toast.LENGTH_SHORT).show()
            } else {
                signup(name, email, password, mobile)
            }
        }
    }

    private fun signup(name: String,email:String, password: String, mobile: String){
//        Logic of Creating User
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
//                    Code for jumping to home

                    addUserToDatabase(name, email, password, mobile, mAuth.currentUser?.uid!!)

                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Some error occured", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name:String, email: String, password: String, mobile: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name, email, password, mobile, uid))
    }

}