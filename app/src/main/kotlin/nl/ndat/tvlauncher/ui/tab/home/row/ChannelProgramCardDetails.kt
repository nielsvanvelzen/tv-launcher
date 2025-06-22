package nl.ndat.tvlauncher.ui.tab.home.row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.data.sqldelight.ChannelProgram

@Composable
fun ChannelProgramCardDetails(program: ChannelProgram, app: App?) {
	Column(
		modifier = Modifier
			.padding(horizontal = 48.dp)
			.height(100.dp)
			.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(6.dp),
	) {
		program.title?.let { title ->
			Text(
				text = title,
				style = MaterialTheme.typography.labelLarge,
				maxLines = 1,
			)
		}

		program.description?.let { description ->
			Text(
				text = description,
				style = MaterialTheme.typography.labelSmall,
				maxLines = 3,
				modifier = Modifier.width(600.dp)
			)
		}
	}
}
