package nl.ndat.tvlauncher.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.ndat.tvlauncher.data.repository.AppRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AutoStartService : KoinComponent {
	companion object {
		private const val TAG = "AutoStartService"
		private const val STARTUP_DELAY_MS = 3000L // 3秒延迟启动
		private const val APP_START_INTERVAL_MS = 1000L // 应用间启动间隔1秒
	}

	private val appRepository: AppRepository by inject()

	@OptIn(DelicateCoroutinesApi::class)
	fun startAutoStartApps(context: Context) {
		Log.d(TAG, "开始启动自启动应用")
		
		GlobalScope.launch {
			try {
				// 等待系统完全启动
				delay(STARTUP_DELAY_MS)
				
				val autoStartApps = appRepository.getAutoStartApps().collect { apps ->
					Log.d(TAG, "找到 ${apps.size} 个自启动应用")
					
					apps.forEachIndexed { index, app ->
						try {
							Log.d(TAG, "启动应用: ${app.displayName} (${app.packageName})")
							
							val intent = when {
								app.launchIntentUriLeanback != null -> {
									Intent.parseUri(app.launchIntentUriLeanback, 0)
								}
								app.launchIntentUriDefault != null -> {
									Intent.parseUri(app.launchIntentUriDefault, 0)
								}
								else -> {
									context.packageManager.getLaunchIntentForPackage(app.packageName)
								}
							}
							
							if (intent != null) {
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
								context.startActivity(intent)
								Log.d(TAG, "成功启动应用: ${app.displayName}")
							} else {
								Log.w(TAG, "无法启动应用: ${app.displayName} - 没有找到启动Intent")
							}
							
							// 应用间启动间隔
							if (index < apps.size - 1) {
								delay(APP_START_INTERVAL_MS)
							}
						} catch (e: Exception) {
							Log.e(TAG, "启动应用失败: ${app.displayName}", e)
						}
					}
				}
			} catch (e: Exception) {
				Log.e(TAG, "自启动服务执行失败", e)
			}
		}
	}
}


