package www.sanju.`in`.Activity

import android.annotation.SuppressLint
import android.content.Intent
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
import www.sanju.`in`.Model.MyDonors
import www.sanju.`in`.R

class FindDonorsActivity : AppCompatActivity() {

    private lateinit var findDonorRV: RecyclerView
    lateinit var findDB: DatabaseReference

    lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<MyDonors, MyViewHolder>


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_donors)


        //init DB
        findDB = FirebaseDatabase.getInstance().reference.child("MyDonors")

        //init layouts
        findDonorRV = findViewById(R.id.find_rv)

        findDonorRV.layoutManager = LinearLayoutManager(
            this,
            LinearLayout.VERTICAL, false
        )


        val findDonor = intent.getStringExtra("blood")


        if (findDonor != null && !findDonor.isEmpty()) {


            fetchDonors(findDonor)
        } else {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
        }


    }

    private fun fetchDonors(findDonor: String?) {


        val option = FirebaseRecyclerOptions.Builder<MyDonors>()
            .setQuery(findDB.orderByChild("blood").equalTo("O+"), MyDonors::class.java)
            .setLifecycleOwner(this)
            .build()







        firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<MyDonors, MyViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@FindDonorsActivity)
                    .inflate(R.layout.find_donor_layout, parent, false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: MyDonors) {

                val placeid = getRef(position).key.toString()



                findDB.child(placeid).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(
                            this@FindDonorsActivity,
                            "Error Occurred " + p0.toException(),
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        holder.nametv.text = model.name
                        holder.age.text = model.age.toString()
                        holder.sex.text = model.mobile.toString()
                        holder.blood.text = model.blood


                        holder.itemView.setOnClickListener {


                            val intent =
                                Intent(this@FindDonorsActivity, FindDonorsActivity::class.java)
                            intent.putExtra("blood", firebaseRecyclerAdapter.getRef(position).key)
                            startActivity(intent)
                        }


                    }
                })
            }
        }
        findDonorRV.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()


    }


    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var nametv: TextView = itemView!!.findViewById<TextView>(R.id.fdonor_name)
        internal var age: TextView = itemView!!.findViewById<TextView>(R.id.fdonar_age)
        internal var sex: TextView = itemView!!.findViewById<TextView>(R.id.fdonar_sex)
        internal var blood: TextView = itemView!!.findViewById<TextView>(R.id.fdonar_blood)


    }
}
