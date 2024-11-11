package com.sonder.codechallenge.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sonder.domain.models.SearchContentType
import com.sonder.domain.models.SearchItem
import com.sonder.domain.models.SearchSectionResult

@Composable
fun SearchResults(
    sections: List<SearchSectionResult>,
    onItemClick: (SearchItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        sections.forEach { section ->
            if (section.items.isNotEmpty()) {
                item {
                    SearchSection(
                        section = section,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchSection(
    section: SearchSectionResult,
    onItemClick: (SearchItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = section.sectionTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
//        when (section.items.firstOrNull()?.contentType?.viewType) {
//            ViewType.HORIZONTAL_COMPACT -> HorizontalCompactSection(section.items, onItemClick)
//            ViewType.HORIZONTAL_DETAILED -> HorizontalDetailedSection(section.items, onItemClick)
//            ViewType.VERTICAL_COMPACT -> VerticalCompactSection(section.items, onItemClick)
//            ViewType.VERTICAL_DETAILED -> VerticalDetailedSection(section.items, onItemClick)
//            null -> Unit
//        }
    }
}

@Composable
private fun HorizontalCompactSection(
    items: List<SearchItem>,
    onItemClick: (SearchItem) -> Unit
) {
    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            item {
                HorizontalCompactItem(
                    title = item.title.orEmpty(),
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
private fun HorizontalDetailedSection(
    items: List<SearchItem>,
    onItemClick: (SearchItem) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { item ->
            item {
                HorizontalDetailedItem(
                    title = item.title.orEmpty(),
                    description = item.description.orEmpty(),
                    imageUrl = item.thumbnail.orEmpty(),
                    contentType = item.contentType?.name.orEmpty(),
                    ctaText = item.action?.title.orEmpty(),
                    isVideo = item.contentType == SearchContentType.ACTION,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
private fun VerticalCompactSection(
    items: List<SearchItem>,
    onItemClick: (SearchItem) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEach { item ->
            VerticalCompactItem(
                title = item.title.orEmpty(),
                onClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun VerticalDetailedSection(
    items: List<SearchItem>,
    onItemClick: (SearchItem) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.forEach { item ->
            VerticalDetailedItem(
                title = item.title.orEmpty(),
                description = item.description.orEmpty(),
                imageUrl = item.thumbnail.orEmpty(),
                contentType = item.contentType?.name.orEmpty(),
                isVideo = item.contentType == SearchContentType.ACTION,
                onClick = { onItemClick(item) }
            )
        }
    }
} 