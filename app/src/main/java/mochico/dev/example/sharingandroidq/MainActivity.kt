package mochico.dev.example.sharingandroidq

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shareButton.setOnClickListener {
            share()
        }

        SharingShortcutsManager().pushDirectShareTargets(this)
    }

    private fun share() {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, sharing_message_text.text.toString())
            type = "text/plain"

            putExtra(Intent.EXTRA_TITLE, getString(R.string.sharing_preview_title))

            val thumbnail = getClipDataThumbnail()
            if (thumbnail != null) {
                clipData = thumbnail
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }

    private fun getClipDataThumbnail(): ClipData? {
        return try {
            ClipData.newUri(contentResolver, null, saveImageThumbnail())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    @Throws(IOException::class)
    private fun saveImageThumbnail(): Uri {
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val cachePath = File(cacheDir, IMAGE_CACHE_DIR)
        cachePath.mkdirs()
        val stream = FileOutputStream("$cachePath/$IMAGE_FILE")
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
        val imagePath = File(cacheDir, IMAGE_CACHE_DIR)
        val newFile = File(imagePath, IMAGE_FILE)
        return FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, newFile)
    }
    
    companion object {
        private const val FILE_PROVIDER_AUTHORITY =
            BuildConfig.APPLICATION_ID + ".fileprovider"

        private const val IMAGE_CACHE_DIR = "images"

        private const val IMAGE_FILE = "image.png"
    }
}
