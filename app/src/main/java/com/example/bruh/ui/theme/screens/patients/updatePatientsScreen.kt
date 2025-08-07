package com.example.bruh.ui.theme.screens.patients

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.bruh.R
import com.example.bruh.data.PatientViewModel
import com.example.bruh.models.Patient
import com.example.bruh.navigation.ROUTE_DASHBOARD
import com.example.bruh.ui.theme.screens.register.registerScreen
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import androidx.core.net.toUri
import com.example.bruh.navigation.ROUTE_VIEWPATIENTS

@Composable
fun UpdatePatientScreen(navController: NavController, patientId:String){
    val patientViewModel: PatientViewModel = viewModel()
    var patient by remember { mutableStateOf<Patient?>(null) }
    LaunchedEffect(patientId) {
        val ref = FirebaseDatabase.getInstance().getReference("Patients").child(patientId)
        val snapshot = ref.get().await()
        patient = snapshot.getValue(Patient::class.java)?.apply {
            id = patientId
        }

//        loadedPatient?.let {
//            patient = it
//
//            // ✅ Set imageUri if imageUrl is available
//            if (!it.imageUrl.isNullOrEmpty()) {
//                imageUri.value = it.imageUrl.toUri()
//            }
//        }
    }
    if (patient==null){
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
        return
    }




    var name by remember { mutableStateOf(patient!!.name ?:"") }
    var gender by remember { mutableStateOf(patient!!.gender ?:"") }
    var nationality by remember { mutableStateOf(patient!!.nationality ?:"")}
    var age by remember { mutableStateOf(patient!!.age ?:"") }
    var diagnosis by remember { mutableStateOf(patient!!.diagnosis?:"") }
    var phone_number by remember { mutableStateOf(patient!!.phone_number?:"") }
//    val imageUri = remember() { mutableStateOf<Uri?>(null) }
    val imageUri= remember{ mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri -> imageUri.value=uri }
    }


    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(text = "Update Patient",
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            color = Color.Magenta,
            modifier = Modifier.fillMaxWidth()
        )
        Card (shape = CircleShape, modifier = Modifier.padding(10.dp).size(200.dp)){
            AsyncImage(model = imageUri.value ?: patient!!.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clickable {
                    launcher.launch("image/*")
                })

        }
        Text(text = "Tap to change picture", color = Color.DarkGray,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline)
        OutlinedTextField(
            value = name,
            onValueChange = {name=it},
            label = { Text(text = "Enter Patient Name")},
            placeholder = { Text(text = "Please enter patient name")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = gender,
            onValueChange = {gender=it},
            label = { Text(text = "Enter Patient's Gender")},
            placeholder = { Text(text = "Please enter patient gender")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nationality,
            onValueChange = {nationality=it},
            label = { Text(text = "Enter Patient's Nationality")},
            placeholder = { Text(text = "Please enter patient nationality")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = age,
            onValueChange = {age=it},
            label = { Text(text = "Enter Patient's Age")},
            placeholder = { Text(text = "Please enter patient age")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = diagnosis,
            onValueChange = {diagnosis=it},
            label = { Text(text = "Enter Patient's Diagnosis")},
            placeholder = { Text(text = "Whats the patient's diagnosis?")},
            modifier = Modifier.fillMaxWidth().height(150.dp), singleLine = false
        )
        OutlinedTextField(
            value = phone_number,
            onValueChange = {phone_number=it},
            label = { Text(text = "Enter Patient's Phone Number")},
            placeholder = { Text(text = "Please enter patient's phone number")},
            modifier = Modifier.fillMaxWidth()
        )
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = {navController.popBackStack()}) { Text(text = "GO BACK") }
            Button(onClick = {
                patientViewModel.updatePatient(
                    patientId,
                    imageUri.value,
                    name,
                    gender,
                    nationality,
                    age,
                    diagnosis,
                    phone_number,
                    context,
                    navController)
//                navController.navigate(ROUTE_VIEWPATIENTS)
            })
            { Text(text = "UPDATE PATIENT")
            navController.navigate(ROUTE_DASHBOARD)}
        }
    }
}



