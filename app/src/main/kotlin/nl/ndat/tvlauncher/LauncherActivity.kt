package nl.ndat.tvlauncher

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import nl.ndat.tvlauncher.databinding.ActivityLauncherBinding

class LauncherActivity : FragmentActivity() {
	private lateinit var binding: ActivityLauncherBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityLauncherBinding.inflate(layoutInflater)
		setContentView(binding.root)

		supportFragmentManager.commit {
			replace<LauncherFragment>(R.id.content)
		}

//		validateLauncher()
	}

	private fun validateLauncher() {
		val launcherController = LauncherController(this)
		if (!launcherController.isLauncher() && launcherController.canRequestLauncher()) {
			startActivityForResult(launcherController.requestLauncherIntent(), 0)
		}
	}
}

