package www.sanju.`in`.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.app.ProgressDialog
import com.google.firebase.database.DatabaseReference
import com.rengwuxian.materialedittext.MaterialEditText

import android.widget.Button
import android.content.Intent

import android.widget.Toast

import android.text.TextUtils
import www.sanju.`in`.MainActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var uEmail: MaterialEditText
   private lateinit var  uPassword: MaterialEditText
    private lateinit var mLoginBtn: Button
    private lateinit var mRegBtn: TextView
    private lateinit var mUsersDB: DatabaseReference
    private lateinit var mProgress: ProgressDialog
    private lateinit var mAuth: FirebaseAuth
    private lateinit var forgotpass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(www.sanju.`in`.R.layout.activity_login)



        uEmail = findViewById(www.sanju.`in`.R.id.emailInput)
        uPassword = findViewById(www.sanju.`in`.R.id.passwordInput)
        mLoginBtn = findViewById(www.sanju.`in`.R.id.signinBtn)
        mRegBtn = findViewById(www.sanju.`in`.R.id.signupBtn)




        mRegBtn.setOnClickListener {
finish()
        }


        mProgress = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()

        forgotpass = findViewById(www.sanju.`in`.R.id.forgotpass)
        forgotpass.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }


        mLoginBtn.setOnClickListener {

                startLogin()
            }

    }


    private fun startLogin() {
        val email = uEmail.text!!.toString().trim { it <= ' ' }
        val password = uPassword.text!!.toString().trim { it <= ' ' }

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgress.setMessage("Signing In...")
            mProgress.show()

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {



                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    finish()

                } else {

                    mProgress.dismiss()
                    Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_LONG).show()


                }
            }


        }


    }





}
