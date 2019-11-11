package www.sanju.`in`.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import www.sanju.`in`.Model.Patient
import www.sanju.`in`.R

class PatientActivity : AppCompatActivity() {

    private lateinit var patientRv: RecyclerView
    lateinit var patientDB: DatabaseReference


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)



        //init DB
        patientDB = FirebaseDatabase.getInstance().reference.child("Records")

        //init layouts
        patientRv = findViewById(R.id.patient_rv)

        patientRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayout.VERTICAL, false
        )




        getPatienRecords()






    }

    private fun getPatienRecords() {


        val option = FirebaseRecyclerOptions.Builder<Patient>()
            .setQuery(patientDB, Patient::class.java)
            .setLifecycleOwner(this)
            .build()


        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Patient, MyViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@PatientActivity).inflate(R.layout.patient_layout,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Patient) {

                val placeid = getRef(position).key.toString()



                patientDB.child(placeid).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(this@PatientActivity, "Error Occurred "+ p0.toException(), Toast.LENGTH_SHORT).show()

                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        holder.nametv.text = model.name
                        holder.age.text = model.age
                        holder.sex.text = model.sex
                        holder.symptoms.text = model.symptoms



                    }
                })
            }
        }
        patientRv.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()

    }


    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var nametv: TextView = itemView!!.findViewById<TextView>(R.id.name_tv)
        internal var age: TextView = itemView!!.findViewById<TextView>(R.id.age_tv)
        internal var sex: TextView = itemView!!.findViewById<TextView>(R.id.sex_tv)
        internal var symptoms: TextView = itemView!!.findViewById<TextView>(R.id.symptoms_tv)



    }

}
