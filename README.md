# Searchable Item Picker Bottom Sheet

A Jetpack Compose component for displaying a searchable list of items in a bottom sheet, allowing users to pick an item from the list.

This component can be used for any entity that extends `SearchableItem`

## Features

- Search functionality within the list of items.
- Customizable titles and subtitles for each item.
- Supports any object type by extending the `SearchableItem<T>` abstract class.

## Usage

### 1. Define a Searchable Item

Extend the `SearchableItem<T>` abstract class to define how the title and subtitle are represented for your data type.

```kotlin
data class Country(
    val name: String,
    val phoneCode: String,
    val isoCode: String
) : SearchableItem<Country>() {
    override fun getSearchableTitle() = name
    override fun getSearchableSubtitle() = phoneCode
}
```

### 2. Use the SearchableItemPickerBottomSheet

Use the `SearchableItemPickerBottomSheet` composable to display the bottom sheet.

```kotlin
@Composable
fun YourCustomComponent() {
    val showBottomSheet = remember { mutableStateOf(false) }
    val countries = listOf(
        // List of Country objects extending SearchableItem
    )

    Button(onClick = { showBottomSheet.value = true }) {
        Text("Show Bottom Sheet Picker")
    }

    if (showBottomSheet.value) {
        SearchableItemPickerBottomSheet(
            title = "Pick A Country",
            items = countries,
            onItemSelected = { selectedCountry ->
                // Handle item selection
                showBottomSheet.value = false
            },
            onDismissRequest = { showBottomSheet.value = false }
        )
    }
}
```

### 3. Customization
- Title: Customize the title of the bottom sheet by passing the title parameter.
- Item Selection: Handle the item selection with the onItemSelected callback.
- Dismiss: Dismiss the bottom sheet with the onDismissRequest callback.


## Installation

Simply add the `SearchableItemPickerBottomSheet.kt` to your project.

## License
This project is licensed under the MIT License.
Feel free to copy, edit and adjust any part of it based on your preferences or specific needs for your repository.
