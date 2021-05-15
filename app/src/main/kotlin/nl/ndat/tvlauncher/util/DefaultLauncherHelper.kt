package nl.ndat.tvlauncher.util

import android.annotation.SuppressLint
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.getSystemService
import androidx.core.role.RoleManagerCompat

class DefaultLauncherHelper(
	context: Context
) {
	private val roleManager by lazy { context.getSystemService<RoleManager>() }
	private val isCompatible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && roleManager != null

	@SuppressLint("NewApi")
	fun isDefaultLauncher(): Boolean = isCompatible && roleManager!!.isRoleHeld(RoleManagerCompat.ROLE_HOME)

	@SuppressLint("NewApi")
	fun canRequestDefaultLauncher(): Boolean =
		isCompatible && roleManager!!.isRoleAvailable(RoleManagerCompat.ROLE_HOME)

	@SuppressLint("NewApi")
	fun requestDefaultLauncherIntent(): Intent? =
		if (isCompatible) roleManager!!.createRequestRoleIntent(RoleManagerCompat.ROLE_HOME)
		else null
}
