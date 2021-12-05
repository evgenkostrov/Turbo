package com.example.turbo.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.example.turbo.R
import com.example.turbo.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .setRequestedSize(1024, 1024)
                .setAspectRatio(1, 1)
                .getIntent(this@ProfileActivity)
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth

    private val imageReference = Firebase.storage.reference
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            updateState(user)
        }

        binding.ivProfile.setOnClickListener {
            cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
                it?.let { uri ->
                    currentImageUri = uri
                }
            }
            cropActivityResultLauncher.launch(null)
        }
        binding.btnChooseUpdate.setOnClickListener {
            updateProfile()
                uploadImageToStorage(user?.uid!!)
        }

        binding.signOutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnEnter.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateProfile() {
        auth.currentUser?.let { firebaseUser ->
            val displayName = binding.etName.text.toString()
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(currentImageUri)
                .setDisplayName(displayName)
                .build()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    firebaseUser.updateProfile(profileUpdates).await()
                    withContext(Dispatchers.Main) {
                        updateState(firebaseUser)
                        Toast.makeText(
                            this@ProfileActivity,
                            "Successfully updated user profile",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun updateState(user: FirebaseUser) {
        val creationTimestamp = SimpleDateFormat("dd/MM/yyyy", Locale("ru"))
            .format(Date(user.metadata?.creationTimestamp ?: 0L))
        val lastSignInTimestamp = SimpleDateFormat("dd/MM/yyyy", Locale("ru"))
            .format(Date(user.metadata?.lastSignInTimestamp ?: 0L))
        binding.idTxt.text = user.uid
        binding.dateTxt.text = "$creationTimestamp......$lastSignInTimestamp"
        if(user.photoUrl==null) {currentImageUri= Uri.parse("android.resource://" + packageName + "/" + R.drawable.profile)
          binding.ivProfile.setImageResource(R.drawable.profile)
        }
        else{downloadImage(user.uid)}
        binding.emailTxt.text = user.email
        binding.etName.setText(user.displayName)
    }

    private fun uploadImageToStorage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        val path = imageReference.child("userimage/$filename")
        try {
            currentImageUri?.let {
                path.putFile(it).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Successfully uploaded image",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun downloadImage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val maxDownloadSize = 1L * 1024 * 1024
            val bytes =
                imageReference.child("userimage/$filename").getBytes(maxDownloadSize).await()
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            withContext(Dispatchers.Main) {
                binding.ivProfile.setImageBitmap(bitmap)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}