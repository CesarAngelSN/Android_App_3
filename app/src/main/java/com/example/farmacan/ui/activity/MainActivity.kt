package com.example.farmacan.ui.activity

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.farmacan.R
import com.example.farmacan.data.db.AppDatabase
import com.example.farmacan.data.entity.Drug

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainActivity(navController: NavController, appDatabase: AppDatabase) {
    var listOfDrugs = remember {
        val drugsFromDatabase = appDatabase.getDrugDao().getAll()
        mutableListOf<Drug>().apply {
            addAll(drugsFromDatabase)
        }
    }
    var checkedList = remember {
        mutableListOf(false).apply {
            for (i in 0 until listOfDrugs.size - 1) {
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
        targetValue = if (buttonActive) -45f else 0f,
        animationSpec = tween(300),
        label = "rotate"
    )
    var showCheckBox by remember {
        mutableStateOf(false)
    }
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
                        Text(filteredDrugs[index].getName(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp))
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(
                if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        (LocalConfiguration.current.screenWidthDp / 2).dp - 20.dp
                    }
                else {
                    (LocalConfiguration.current.screenWidthDp / 4).dp - 20.dp
                }),
            modifier = Modifier
                .fillMaxHeight(0.93f)
                .padding(16.dp)
        ) {
            if (query.isNotEmpty()) {
                val filteredDrugs = listOfDrugs.filter {
                    it.getName().contains(query, true)
                }
                if (filteredDrugs.size % 2 != 0) {
                    filteredDrugs.forEach {
                        item(span = { GridItemSpan(
                            if (it == filteredDrugs.last()) {
                                maxLineSpan
                            }
                            else {
                                1
                            })}
                        ) {
                            DrugCard(drug = it,
                                showCheckBox = showCheckBox,
                                onCheck = { checked -> checkedList[filteredDrugs.indexOf(it)] = checked},
                                onNavigate = {navController.navigate("druginfo/${it.getId()}")})
                        }
                    }
                }
                else {
                    filteredDrugs.forEach {
                        item() {
                            DrugCard(drug = it,
                                showCheckBox = showCheckBox,
                                onCheck = { checked -> checkedList[filteredDrugs.indexOf(it)] = checked},
                                onNavigate = {navController.navigate("druginfo/${it.getId()}")})
                        }
                    }
                }
            }
            else {
                if (listOfDrugs.size % 2 != 0) {
                    listOfDrugs.forEach {
                        item(span = { GridItemSpan(
                            if (it == listOfDrugs.last()) {
                                maxLineSpan
                            }
                            else {
                                1
                            }
                        )}
                        ) {
                            DrugCard(drug = it,
                                showCheckBox = showCheckBox,
                                onCheck = { checked -> checkedList[listOfDrugs.indexOf(it)] = checked},
                                onNavigate = {navController.navigate("druginfo/${it.getId()}")})
                        }
                    }

                }
                else {
                    listOfDrugs.forEach {
                        item() {
                            DrugCard(drug = it,
                                showCheckBox = showCheckBox,
                                onCheck = { checked -> checkedList[listOfDrugs.indexOf(it)] = checked},
                                onNavigate = {navController.navigate("druginfo/${it.getId()}")})
                        }
                    }

                }
            }
        }
        Row (Modifier.fillMaxWidth(), Arrangement.End, Alignment.CenterVertically) {
            AnimatedButton(
                translatable = buttonActive,
                isDelete = false,
                checkedList,
                {},
                {},
                appDatabase,
                {},
                navController
            )
            AnimatedButton(
                translatable = buttonActive,
                isDelete = true,
                checkedList,
                {newListOfDrugs ->
                    listOfDrugs.clear()
                    listOfDrugs.addAll(newListOfDrugs)},
                {newCheckedList ->
                    newCheckedList.clear()
                    checkedList.addAll(newCheckedList)},
                appDatabase,
                {pressed ->
                    showCheckBox = pressed},
                navController
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
                    Modifier
                        .scale(1.8f, 1.8f)
                        .graphicsLayer(
                        rotationZ = rotationButton.value
                    ))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun DrugCard(drug: Drug, showCheckBox: Boolean, onCheck: (Boolean) -> Unit, onNavigate: () -> Unit) {
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
            if (selected) {
                onNavigate()
            }
        },
        modifier = Modifier
            .height(220.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(
                id = when(drug.getPackaging()) {
                    "Box-10" -> R.color.bluedrug
                    "Box-20" -> R.color.greendrug
                    else -> R.color.graydrug
            })
        )
    ) {
        Box(Modifier.fillMaxSize()) {
            if (showCheckBox) {
                Checkbox(checked = checked,
                    onCheckedChange = {
                        checked = !checked
                        onCheck(checked)
                    },
                    Modifier
                        .align(alignment = Alignment.TopEnd)
                        .zIndex(2f),
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Black,
                        checkedColor = colorResource(id = R.color.darkgreen)
                    )
                )
            }
            Column (Modifier.fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
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
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(12.dp))
                Column (
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .fillMaxHeight(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
                    Text(text = drug.getName(), textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(text = "Box size: " + drug.getPackaging(), textAlign = TextAlign.Center, color = Color.Black)
                    Text(text = if (drug.getPrice().isEmpty()) {
                        "Price: Free"
                    }else {
                        "Price: " + drug.getPrice() + "$"
                    }, textAlign = TextAlign.Center, color = Color.Black)
                }


            }
        }
    }
}

@Composable
fun AnimatedButton(
    translatable: Boolean,
    isDelete: Boolean,
    checkedList: MutableList<Boolean>,
    updateDrugList: (MutableList<Drug>) -> Unit,
    updateCheckedList: (MutableList<Boolean>) -> Unit,
    appDatabase: AppDatabase,
    onPressed: (Boolean) -> Unit,
    navController: NavController
) {
    val translation = animateFloatAsState(
        targetValue = if (translatable) -(LocalConfiguration.current.screenWidthDp.toFloat() + 50f)
        else LocalConfiguration.current.screenWidthDp.toFloat() * 2,
        animationSpec = tween(300),
        label = "rotate"
    )
    var clickCounter by remember {
        mutableIntStateOf(0)
    }
    var active by remember {
        mutableStateOf(false)
    }
    if (active) {
        AlertDialog(
            text = {
                Text(text = "Are you sure you want to delete the selected items?")
            },
            title = {
                Text(text = "Deletion")
            },
            onDismissRequest = {active = false},
            confirmButton = {
                TextButton(onClick = {
                    checkedList.forEach {
                        if (it) {
                            appDatabase.getDrugDao().deleteById(
                                appDatabase.getDrugDao().getAll()[checkedList.indexOf(it)].getId())
                        }
                    }
                    updateDrugList(appDatabase.getDrugDao().getAll() as MutableList<Drug>)
                    updateCheckedList(mutableListOf(false).apply {
                        for (i in 0 until appDatabase.getDrugDao().getAll().size - 1) {
                            add(false)
                        }
                    })
                    clickCounter = 0
                    active = false
                }) {
                    Text(text = "Confirm")
                }

            },
            dismissButton = {
                TextButton(onClick = {
                    active = false
                }) {
                    Text(text = "Cancel")
                }
            })
    }
    Button(onClick = {
        if (isDelete) {
            clickCounter++
            if (clickCounter > 1 && hasClickedItems(checkedList)) {
                active = true
            }
            else if (clickCounter > 1 && !hasClickedItems(checkedList)) {
                clickCounter = 0
            }
            onPressed(clickCounter == 1)
        }
        else {
            navController.navigate("newdrugactivity")
        }
    },
        Modifier
            .padding(10.dp)
            .graphicsLayer(
            translationX = translation.value
        )) {
        Icon(imageVector = if (isDelete) Icons.Default.Clear else Icons.Default.Add,
            contentDescription = "icon")
    }
}

fun hasClickedItems(list: MutableList<Boolean>): Boolean{
    var hasClickedItems = false
    for (i in 0 until list.size) {
        if (list[i]) {
            hasClickedItems = true
        }
    }
    return hasClickedItems
}