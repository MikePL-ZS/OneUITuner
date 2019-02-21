package tk.zwander.oneuituner.util

import android.app.Activity
import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceFragmentCompat
import eu.chainfire.libsuperuser.Shell
import tk.zwander.oneuituner.App
import tk.zwander.oneuituner.R
import java.io.File


val Context.aapt: String?
    get() {
        val aapt = File(cacheDir, "aapt")
        if (aapt.exists()) return aapt.absolutePath

        if (!assets.extractAsset("aapt", aapt.absolutePath))
            return null

        Shell.SH.run("chmod 755 ${aapt.absolutePath}")
        return aapt.absolutePath
    }

val Context.zipalign: String?
    get() {
        val zipalign = File(cacheDir, "zipalign")
        if (zipalign.exists()) {
            Shell.SH.run("chmod 755 ${zipalign.absolutePath}")
            return zipalign.absolutePath
        }

        if (!assets.extractAsset("zipalign", zipalign.absolutePath))
            return null

        Shell.SH.run("chmod 755 ${zipalign.absolutePath}")
        return zipalign.absolutePath
    }

val Context.app: App
    get() = applicationContext as App

val Context.prefs: PrefManager
    get() = PrefManager.getInstance(this)

fun Context.isInstalled(packageName: String) =
        try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: Exception) {
            false
        }

val PreferenceFragmentCompat.navController: NavController
    get() = NavHostFragment.findNavController(this)

val navOptions: NavOptions
    get() = NavOptions.Builder()
        .setEnterAnim(android.R.anim.fade_in)
        .setExitAnim(android.R.anim.fade_out)
        .setPopEnterAnim(android.R.anim.fade_in)
        .setPopExitAnim(android.R.anim.fade_out)
        .build()

val Activity.navController: NavController
    get() = findNavController(R.id.nav_host)