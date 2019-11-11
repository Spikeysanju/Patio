package www.sanju.`in`

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.FirebaseApp
import android.content.Intent

import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import android.widget.ImageButton
import com.rengwuxian.materialedittext.MaterialEditText
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Button
import android.widget.LinearLayout
import www.sanju.`in`.Activity.*


class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListner: FirebaseAuth.AuthStateListener
    private lateinit var mCurrentUsers: FirebaseUser
    private lateinit var addRecordLay: LinearLayout

    private lateinit var patientLay: LinearLayout
    private lateinit var signout: Button
    private lateinit var doctorLay: LinearLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        FirebaseApp.getApps(this)
        mAuth = FirebaseAuth.getInstance()
        mCurrentUsers = mAuth!!.currentUser!!


        if (mCurrentUsers != null) {


            Toast.makeText(this@MainActivity,"Hello Welcome Back",Toast.LENGTH_SHORT).show()

        } else {
            val loginIntent = Intent(this@MainActivity, SignUpActivity::class.java)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(loginIntent)
            finish()

        }



        mAuthListner = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                val loginIntent = Intent(this@MainActivity, SignUpActivity::class.java)
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(loginIntent)
                finish()


            }
        }



        addRecordLay = findViewById(R.id.addRecordLay)
        patientLay = findViewById(R.id.patientLay)
        doctorLay = findViewById(R.id.doctorLay)


        signout = findViewById(R.id.signoutBtn);


        signout.setOnClickListener {


            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }


        patientLay.setOnClickListener {

            startActivity(Intent(this@MainActivity, PatientActivity::class.java))

        }



        addRecordLay.setOnClickListener {


            startActivity(Intent(this@MainActivity, AddRecordActivity::class.java))
        }



        doctorLay.setOnClickListener {

            startActivity(Intent(this@MainActivity, DoctorActivity::class.java))

        }











    }
}
