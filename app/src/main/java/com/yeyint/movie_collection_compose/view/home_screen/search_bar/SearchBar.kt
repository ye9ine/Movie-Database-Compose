package com.yeyint.movie_collection_compose.view.home_screen.search_bar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yeyint.movie_collection.model.SearchHistoryModel
import com.yeyint.movie_collection_compose.R
import com.yeyint.movie_collection_compose.helper.MyColor
import com.yeyint.movie_collection_compose.helper.MyFontSize.textSizeExtraSmall
import com.yeyint.movie_collection_compose.viewModel.MovieViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarView(
    onSearchKeyChange:(String)->Unit,
    isMovie: Boolean,
    onGetMovie:()->Unit,
    onGetTv:()->Unit,
    onInsertSearchHistory:(String)->Unit,
    onGetSearchHistory:()->Unit,
    searchList: List<SearchHistoryModel>

) {

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    var isActive by remember {
        mutableStateOf(false)
    }

    SearchBar(
        modifier = Modifier
            .background(color = MyColor.colorPrimary)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        query = searchText,
        placeholder = {
            Text(text = "Search Movie or Tv Series", fontSize = textSizeExtraSmall.sp)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.mipmap.search),
                contentDescription = "search icon",
                modifier = Modifier.size(25.dp)
            )
        },
        shape = SearchBarDefaults.dockedShape,
        trailingIcon = {
            if (isActive) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear icon",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            if (searchText.isNotEmpty()) {
                                searchText = ""
                                onSearchKeyChange(searchText)
                            } else {
                                if (isMovie) {
                                    onGetMovie()
                                } else {
                                    onGetTv()
                                }
                                isActive = false
                            }
                        }
                )
            }

        },
        onQueryChange = {
            searchText = it
        },
        onSearch = {
            isActive = false
            onSearchKeyChange(searchText)
            onInsertSearchHistory(searchText)

            if (isMovie) {
                onGetMovie()
            } else {
                onGetTv()
            }

        },
        active = isActive,
        onActiveChange = {
            isActive = it
            if (isActive) {
                onGetSearchHistory()
            }
        }
    ) {

        LazyColumn {
            items(
                count = searchList.size,
                itemContent = {
                    val searchKey = searchList[it].searchKey
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()
                            .clickable {
                                isActive = false
                                searchText = searchKey
                                onInsertSearchHistory(searchText)

                                if (isMovie) {
                                    onGetMovie()
                                } else {
                                    onGetTv()
                                }
                            }
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Default.History,
                            contentDescription = "search history"
                        )

                        Text(text = searchKey)
                    }
                }

            )
        }

    }

}


@Preview
@Composable
fun SearchBarPreview() {
    SearchBarView(
        isMovie = true,
        onGetMovie = {

        },
        onGetTv = {

        },
        onInsertSearchHistory = { s->

        },
        onGetSearchHistory = {

        },
        onSearchKeyChange = {s->

        },
        searchList = emptyList()
    )
}