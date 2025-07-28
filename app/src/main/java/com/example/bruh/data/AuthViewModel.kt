package com.example.bruh.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.bruh.models.UserModel
import com.example.bruh.navigation.ROUTE_DASHBOARD
import com.example.bruh.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AuthViewModel:ViewModel(){
    private val auth: FirebaseAuth=FirebaseAuth.getInstance()
    fun signup(username:String, email:String,password:String,confirm_password:String,navController: NavController,context: Context) {
        if (username.isBlank() || email.isBlank() || password.isBlank() || confirm_password.isBlank()) {
            Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_LONG).show()
            return
        }
        if (password != confirm_password){
             Toast.makeText(context,"Password does not match",Toast.LENGTH_LONG).show()
            return
        }
    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
        task ->
        if (task.isSuccessful){
            val userId = auth.currentUser?.uid ?: ""
            val user = UserModel(username=username,email=email,userId=userId)

            saveUserToDatabase(user,navController,context)
        } else{
            Toast.makeText(context,task.exception?.message?:"Registration Failed.", Toast.LENGTH_LONG).show()
        }
    }
    }
    private fun saveUserToDatabase(user:UserModel,navController: NavController,context: Context){
        val dbRef = FirebaseDatabase.getInstance().getReference("User/${user.userId}")
        dbRef.setValue(user).addOnCompleteListener{
            task->
            if (task.isSuccessful){
                Toast.makeText(context,"User Registered Successfully",
                    Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_LOGIN){
                    popUpTo(0)
                }
            }else{
                Toast.makeText(context,task.exception?.message?:"Failed to save user",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
    fun login(email: String,password: String,navController: NavController,context: Context){
        if (email.isBlank() || password.isBlank()){
            Toast.makeText(context,"Email and Password required",Toast.LENGTH_LONG).show()
            return
        }
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            task ->
            if (task.isSuccessful){
                Toast.makeText(context,"Login Successful",Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_DASHBOARD){
                    popUpTo(0)
                }
            }else{
                Toast.makeText(context,task.exception?.message?:"Login Failed",Toast.LENGTH_LONG).show()
            }
        }

    }
}