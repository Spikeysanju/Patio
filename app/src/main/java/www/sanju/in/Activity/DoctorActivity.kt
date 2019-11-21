package www.sanju.`in`.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import www.sanju.`in`.Model.Doctor
import www.sanju.`in`.R

class DoctorActivity : AppCompatActivity() {


    private lateinit var doctorRv: RecyclerView
    lateinit var doctorDB: DatabaseReference

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)


        //init DB
        doctorDB = FirebaseDatabase.getInstance().reference.child("Doctors")

        //init layouts
        doctorRv = findViewById(R.id.doctor_rv)

        doctorRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayout.VERTICAL, false
        )

        getDoctorRecords()


    }

    private fun getDoctorRecords() {


        val option = FirebaseRecyclerOptions.Builder<Doctor>()
            .setQuery(doctorDB, Doctor::class.java)
            .setLifecycleOwner(this)
            .build()


        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Doctor, DoctorActivity.MyViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorActivity.MyViewHolder {
                val itemView = LayoutInflater.from(this@DoctorActivity).inflate(R.layout.doctor_layout,parent,false)
                return DoctorActivity.MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: DoctorActivity.MyViewHolder, position: Int, model: Doctor) {

                val placeid = getRef(position).key.toString()



                doctorDB.child(placeid).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(this@DoctorActivity, "Error Occurred "+ p0.toException(), Toast.LENGTH_SHORT).show()

                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        holder.namedoc.text = model.name
                        holder.mobile.text = model.mobile.toString()
                        holder.exp.text = model.experience



                    }
                })
            }
        }
        doctorRv.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()



    }



    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var mobile: TextView = itemView!!.findViewById<TextView>(R.id.doc_mob_tv)
        internal var exp: TextView = itemView!!.findViewById<TextView>(R.id.doc_exp_tv)
        internal var namedoc: TextView = itemView!!.findViewById<TextView>(R.id.doc_name_tv)



    }
}
