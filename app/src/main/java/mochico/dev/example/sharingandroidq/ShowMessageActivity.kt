package mochico.dev.example.sharingandroidq

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_send_message.*

class ShowMessageActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)
        setTitle(R.string.sending_message)

        button_ok.setOnClickListener {
            finish()
        }

        val handled = handleIntent(intent)
        if (!handled) {
            setupContents()
        }
    }

    private fun handleIntent(intent: Intent): Boolean {
        if (Intent.ACTION_SEND == intent.action && "text/plain" == intent.type) {
            val body = intent.getStringExtra(Intent.EXTRA_TEXT)
            val targetId = if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.P &&
                intent.hasExtra(Intent.EXTRA_SHORTCUT_ID)
            ) {
                intent.getStringExtra(Intent.EXTRA_SHORTCUT_ID)?.toInt() ?: Target.INVALID_ID
            } else {
                Target.INVALID_ID
            }
            setupContents(targetId, body)
            return true
        }
        return false
    }

    private fun setupContents(targetId: Int = Target.INVALID_ID, body: String? = null) {
        if (targetId != Target.INVALID_ID) {
            val target = Target.byId(targetId)
            message_body.text = target.name
            contact_name.setCompoundDrawablesRelativeWithIntrinsicBounds(
                target.icon,
                0,
                0,
                0
            )
        }
        message_body.text = body
    }
}
