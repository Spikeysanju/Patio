package www.sanju.`in`.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rengwuxian.materialedittext.MaterialEditText
import www.sanju.`in`.MainActivity
import www.sanju.`in`.R


class AddRecordActivity : AppCompatActivity() {

    private lateinit var name: MaterialEditText
    private lateinit var age: MaterialEditText
    private lateinit var sex: MaterialEditText
    private lateinit var location: MaterialEditText
    private lateinit var weight: MaterialEditText
    private lateinit var symptoms: MaterialEditText
    private lateinit var doctor: MaterialEditText
    private lateinit var upid: MaterialEditText
    private lateinit var checkuptype: MaterialEditText
    private lateinit var blood: MaterialEditText
    private lateinit var mobile: MaterialEditText

    private lateinit var uploadRecord:Button
    private lateinit var mRecordDB:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)

        name = findViewById(R.id.aName)
        age = findViewById(R.id.aAge)
        sex = findViewById(R.id.aSex)
        location = findViewById(R.id.aLocation)
        weight = findViewById(R.id.aWeight)
        symptoms = findViewById(R.id.aSymp)
        doctor = findViewById(R.id.aDoc)
        upid = findViewById(R.id.aPID)
        checkuptype = findViewById(R.id.aCheck)
        uploadRecord = findViewById(R.id.upload)

        blood = findViewById(R.id.aBlood)
        mobile = findViewById(R.id.amob)



        mRecordDB = FirebaseDatabase.getInstance().reference.child("Records")



        uploadRecord.setOnClickListener {

            uploadRecordDetails()

        }



    }

    private fun uploadRecordDetails() {


        val dialog = ProgressDialog(this)
        dialog.setMessage("Uploading New Breed")
        dialog.show()


        val pname = name.text.toString().trim()
        val page = age.text.toString().trim()
        val psex = sex.text.toString().trim()
        val ploc = location.text.toString().trim()
        val pweight = weight.text.toString().trim()
        val psymp = symptoms.text.toString().trim()
        val pdoc = doctor.text.toString().trim()
        val pupid = upid.text.toString().trim()
        val pcheck = checkuptype.text.toString().trim()
        val pmob = mobile.text.toString().trim()
        val pblood = blood.text.toString().trim()





        if (!TextUtils.isEmpty(pname) && !TextUtils.isEmpty(page) && !TextUtils.isEmpty(psex)
            && !TextUtils.isEmpty(pcheck)
            && !TextUtils.isEmpty(ploc)
            && !TextUtils.isEmpty(pweight)
            && !TextUtils.isEmpty(psymp)
            && !TextUtils.isEmpty(pdoc)
            && !TextUtils.isEmpty(pupid)
            && !TextUtils.isEmpty(pmob)
            && !TextUtils.isEmpty(pblood)

        ) {

                    val newAct = mRecordDB.push()
                    newAct.child("name").setValue(pname)
                    newAct.child("age").setValue(page)
                    newAct.child("sex").setValue(psex)
                    newAct.child("location").setValue(ploc)
                    newAct.child("weight").setValue(pweight)
                    newAct.child("symptoms").setValue(psymp)
                    newAct.child("doctor").setValue(pdoc)
                    newAct.child("puid").setValue(pupid)
                    newAct.child("checkup").setValue(pcheck)
            newAct.child("blood").setValue(pblood)
            newAct.child("no").setValue(pmob)



            dialog.dismiss()

                    startActivity(Intent(this@AddRecordActivity, MainActivity::class.java))
                    finish()
                }
        }

    }

