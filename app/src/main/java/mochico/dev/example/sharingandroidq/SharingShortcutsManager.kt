package mochico.dev.example.sharingandroidq

import android.content.Context
import android.content.Intent
import androidx.core.app.Person
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat

import java.util.ArrayList
import java.util.HashSet

class SharingShortcutsManager {

    fun pushDirectShareTargets(context: Context) {
        val shortcuts = ArrayList<ShortcutInfoCompat>()

        val contactCategories = HashSet<String>()
        contactCategories.add(CATEGORY_TEXT_SHARE_TARGET)

        for (id in 0 until MAX_SHORTCUTS) {
            val contact = Target.byId(id)

            shortcuts.add(
                ShortcutInfoCompat.Builder(context, id.toString())
                    .setShortLabel(contact.name)
                    .setIcon(IconCompat.createWithResource(context, contact.icon))
                    .setIntent(Intent(Intent.ACTION_DEFAULT))
                    .setLongLived()
                    .setCategories(contactCategories)
                    .setPerson(
                        Person.Builder()
                            .setName(contact.name)
                            .setKey(id.toString())
                            .build()
                    )
                    .build()
            )
        }

        ShortcutManagerCompat.addDynamicShortcuts(context, shortcuts)
    }

    companion object {

        private const val MAX_SHORTCUTS = 4

        private const val CATEGORY_TEXT_SHARE_TARGET =
            "mochico.dev.example.sharingandroidq.category.TEXT_SHARE_TARGET"
    }
}
