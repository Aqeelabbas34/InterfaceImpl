package com.devsphere.interfaceimpl

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsphere.interfaceimpl.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: MainViewModel   // 👈 Declare variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Initialize ViewModel manually
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Setup RecyclerView
        adapter = UserAdapter(emptyList(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observe user list
        viewModel.users.observe(this) { list ->

            adapter.submitData(list)
        }

        // Observe success status
        viewModel.isSuccess.observe(this) { success ->
            if (!success) {
                Toast.makeText(this, "Error fetching users!", Toast.LENGTH_SHORT).show()
            }
        }

        // Load data
        viewModel.loadUsers()
    }

    override fun onUserClicked(user: User) {
        Toast.makeText(this, "User clicked: ${user.name}", Toast.LENGTH_SHORT).show()
    }
}
