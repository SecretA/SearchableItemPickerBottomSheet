
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.real.realgpt.R


/**
 * Created by Salam El-Banna | Aug 2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SearchableItemPickerBottomSheet(
    title: String,
    items: List<SearchableItem<T>>,
    onItemSelected: (T) -> Unit,
    onDismissRequest: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        sheetState = state,
        containerColor = Color.White,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
            )

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                singleLine = true,
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                val filteredItems = if (searchQuery.isEmpty()) items else items.filter {
                    it.getSearchableTitle().contains(searchQuery, ignoreCase = true) ||
                            it.getSearchableSubtitle().contains(searchQuery, ignoreCase = true)
                }
                items(filteredItems) { searchableItem ->
                    SearchableItemRow(searchableItem, onItemSelected)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun <T> SearchableItemRow(
    searchableItem: SearchableItem<T>,
    onItemSelected: (T) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected(searchableItem.getSearchableData()) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = searchableItem.getSearchableTitle(),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        )
        Text(
            text = searchableItem.getSearchableSubtitle(),
            modifier = Modifier.widthIn(max = 100.dp),
            textAlign = TextAlign.End
        )
    }
}

abstract class SearchableItem<T> {
    abstract fun getSearchableTitle(): String
    abstract fun getSearchableSubtitle(): String

    @Suppress("UNCHECKED_CAST")
    fun getSearchableData() = this as T
}
