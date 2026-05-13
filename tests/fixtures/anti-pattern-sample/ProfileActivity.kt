// Anti-pattern sample. DO NOT use as a template.
//
// Violations:
// - extends AppCompatActivity + setContentView with XML
// - findViewById in a "Compose" project
// - !! everywhere
// - GlobalScope.launch (leaks)
// - LiveData with observe(this)
// - Manual ViewModel construction
// - No Hilt
// - log.Println-style android.util.Log
// - getName() / setName() Java-style accessors
package com.example.anti

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private val viewModel = ProfileViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val nameTv = findViewById<TextView>(R.id.name)!!
        val userId = intent.extras!!.getString("userId")!!

        GlobalScope.launch {
            val user = viewModel.getRepo().getUser(userId)
            runOnUiThread { nameTv.text = user.getName()!! }
        }

        viewModel.email.observe(this) { email ->
            nameTv.append(" / $email")
        }

        Log.d("Profile", "loaded $userId")
    }
}

class ProfileViewModel {
    val email: LiveData<String> = MutableLiveData("")
    fun getRepo(): Repo = Repo()
}

class Repo {
    suspend fun getUser(id: String): User = User().apply { setName("Alice") }
}

class User {
    private var name: String? = null
    fun getName(): String? = name
    fun setName(v: String) { name = v }
}
