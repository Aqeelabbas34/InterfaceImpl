package com.devsphere.interfaceimpl

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsphere.interfaceimpl.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects

class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {

    private lateinit var binding: ActivityMainBinding
    private val db by lazy { FirebaseFirestore.getInstance() }
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(emptyList(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        fetchUsers()
    }

    private fun fetchUsers() {
        db.collection("users")
            .orderBy("name")
            .get()
            .addOnSuccessListener { snapshot ->
                val users = snapshot.toObjects<User>()
                adapter.submitData(users)
            }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage ?: "Error fetching users", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onUserClicked(user: User) {
        Toast.makeText(this, "User clicked: ${user.name}", Toast.LENGTH_SHORT).show()

    }


}
