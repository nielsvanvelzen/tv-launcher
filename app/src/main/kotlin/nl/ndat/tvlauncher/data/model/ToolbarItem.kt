package nl.ndat.tvlauncher.data.model

sealed interface ToolbarItem {
	object Clock : ToolbarItem
	object Settings : ToolbarItem
	object Network : ToolbarItem
}
