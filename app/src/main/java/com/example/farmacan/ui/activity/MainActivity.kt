package com.example.farmacan.ui.activity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.farmacan.R
import com.example.farmacan.data.db.AppDatabase
import com.example.farmacan.data.entity.Drug

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainActivity(navController: NavController, appDatabase: AppDatabase, userName: String?) {
    var listOfDrugs = remember {
        var drugsFromDatabase = appDatabase.getDrugDao().getAll()
        mutableListOf<Drug>().apply {
            addAll(drugsFromDatabase)
        }
    }
    println(listOfDrugs)
    var checkedList = remember {
        mutableStateListOf<Boolean>().apply {
            listOfDrugs.forEach {
                add(false)
            }
        }
    }
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    var buttonActive by remember {
        mutableStateOf(false)
    }
    val rotationState by remember {
        mutableFloatStateOf(0f)
    }
    val rotation = animateFloatAsState(
        targetValue = if (active) 45f else 0f,
        animationSpec = tween(300),
        label = "rotate"
    )
    val rotationButton = animateFloatAsState(
        targetValue = if (buttonActive) 45f else 0f,
        animationSpec = tween(300),
        label = "rotate"
    )
    Column (Modifier.fillMaxSize(0.95f), Arrangement.Top, Alignment.CenterHorizontally){
        SearchBar(query = query,
            onQueryChange = {query = it},
            onSearch = {active = false},
            active = active,
            onActiveChange = {
                active = it
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        active = !active
                        println(rotationState)
                    },
                    Modifier.graphicsLayer(
                        rotationZ = rotation.value
                    ))
                {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "cross",
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {active = false},
                    enabled = query.isNotEmpty())
                {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search")
                }
            }, modifier = Modifier
                .fillMaxWidth(0.95f))
        {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                val filteredDrugs = listOfDrugs.filter {
                    it.getName().contains(query, true)
                }
                items(filteredDrugs.size) { index ->
                    /*AnimatedVisibility(
                        visible = listOfDrugs[index].getName().contains(query, true),
                        enter = slideInVertically(initialOffsetY = { 1000 }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { 1000 }) + fadeOut()
                    ) {*/
                        Text(filteredDrugs[index].getName(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp))
                    //}
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp), // 2 columnas
            modifier = Modifier
                .fillMaxHeight(0.93f)
                .padding(16.dp)
        ) {
            if (query.isNotEmpty()) {
                val filteredDrugs = listOfDrugs.filter {
                    it.getName().contains(query, true)
                }
                if (filteredDrugs.size % 2 != 0) {
                    items(filteredDrugs.dropLast(1).size) { index ->
                        DrugCard(filteredDrugs[index],
                            {checked -> checkedList[index] = checked})
                    }
                    item (span = { GridItemSpan(maxLineSpan) }){
                        DrugCard(
                            filteredDrugs.last(),
                            { checked -> checkedList[checkedList.size - 1] = checked})
                    }
                }
                else {
                    items(filteredDrugs.size) { index ->
                        DrugCard(
                            filteredDrugs[index],
                            { checked -> checkedList[index] = checked})
                    }
                }
            }
            else {
                if (listOfDrugs.size % 2 != 0) {
                    items(listOfDrugs.dropLast(1).size) { index ->
                        DrugCard(
                            listOfDrugs[index],
                            { checked -> checkedList[index] = checked})
                    }
                    item (span = { GridItemSpan(maxLineSpan) }){
                        DrugCard(
                            listOfDrugs.last(),
                            { checked -> checkedList[checkedList.size - 1] = checked})
                    }
                }
                else {
                    items(listOfDrugs.size) { index ->
                        DrugCard(
                            listOfDrugs[index],
                            { checked -> checkedList[index] = checked })
                    }
                }
            }
        }
        Row (Modifier.fillMaxWidth(), Arrangement.End, Alignment.CenterVertically) {
            AnimatedButton(
                translatable = buttonActive,
                isDelete = false,
                checkedList,
                {newListOfDrugs ->
                    listOfDrugs.clear()
                    listOfDrugs = newListOfDrugs},
                {newCheckedList ->
                    checkedList = newCheckedList},
                appDatabase)
            AnimatedButton(
                translatable = buttonActive,
                isDelete = true,
                checkedList,
                {newListOfDrugs ->
                    listOfDrugs.clear()
                    listOfDrugs.addAll(newListOfDrugs)},
                {newCheckedList ->
                    checkedList = newCheckedList},
                appDatabase
            )
            Button(onClick = {
                buttonActive = !buttonActive
            },
                Modifier
                    .fillMaxWidth(0.25f)
                    .align(Alignment.Bottom)
                    .offset {
                        IntOffset(-25.dp.roundToPx(), 0)
                    })
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "search",
                    Modifier.graphicsLayer(
                        rotationZ = rotationButton.value
                    ))
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun DrugCard(drug: Drug, onCheck: (Boolean) -> Unit) {
    var selected by remember {
        mutableStateOf(false)
    }
    var checked by remember {
        mutableStateOf(false)
    }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            selected = !selected
        },
        modifier = Modifier
            .height(180.dp)
            .padding(10.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(painter = painterResource(
                id = when (drug.getName()) {
                    "Amplodipine" -> R.drawable.amlodipine
                    "Amoxicillin" -> R.drawable.amoxicillin
                    "Azithromycin" -> R.drawable.azithromycin
                    "Citalopram" -> R.drawable.citalopram
                    "Ibuprofen" -> R.drawable.ibuprofen
                    "Loratadine" -> R.drawable.loratadine
                    "Omeprazole" -> R.drawable.omeprazole
                    "Paracetamol" -> R.drawable.paracetamol
                    "Simvastatin" -> R.drawable.simvastatin
                    "Warfarin" -> R.drawable.warfarin
                    else -> R.drawable.amlodipine
                }

            ),
                contentDescription = "img",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterStart)
                    .height(90.dp))
            Checkbox(checked = checked,
                onCheckedChange = {
                    checked = !checked
                    onCheck(checked)
                },
                Modifier
                    .align(alignment = Alignment.TopEnd))
        }
    }
}

@Composable
fun AnimatedButton(
    translatable: Boolean,
    isDelete: Boolean,
    checkedList: SnapshotStateList<Boolean>,
    updateDrugList: (ArrayList<Drug>) -> Unit,
    updateCheckedList: (SnapshotStateList<Boolean>) -> Unit,
    appDatabase: AppDatabase
) {
    val translation = animateFloatAsState(
        targetValue = if (translatable) -(LocalConfiguration.current.screenWidthDp.toFloat() + 50f) else LocalConfiguration.current.screenWidthDp.toFloat() + 150f,
        animationSpec = tween(300),
        label = "rotate"
    )
    Button(onClick = {
        if (isDelete) {
            for (i in 0 until checkedList.size) {
                if (checkedList[i]) {
                    println(appDatabase.getDrugDao().getAll()[i].getName())
                    appDatabase.getDrugDao().deleteById(appDatabase.getDrugDao().getAll()[i].getId())
                }
            }
        }
        updateDrugList(appDatabase.getDrugDao().getAll() as ArrayList<Drug>)
        updateCheckedList(mutableStateListOf<Boolean>().apply {
            appDatabase.getDrugDao().getAll().forEach {
                add(false)
            }
        })
    },
        Modifier.graphicsLayer(
            translationX = translation.value
        )) {
        Icon(imageVector = if (isDelete) Icons.Default.Clear else Icons.Default.Add,
            contentDescription = "icon")
    }
}