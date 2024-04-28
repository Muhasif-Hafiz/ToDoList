import android.content.ClipData.Item
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


data class NoteData(
    val id: Int,
    var Note: String,
    var isEditing: Boolean = false
)

@Composable
fun ToDoList() {
    var isAlert by remember { mutableStateOf(false) }
    var NoteText by remember { mutableStateOf("") }
    var sItems by remember { mutableStateOf(listOf<NoteData>()) }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center

    ) {

        Button(
            onClick = { isAlert = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = "Add Note ")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(sItems) {

                    item ->
                if (item.isEditing) {

                    ListEditor(item = item, onEditComplete = { editedNote ->
                        sItems = sItems.map { it.copy(isEditing = false) }
                        val editedItem = sItems.find { it.id == item.id }
                        editedItem?.let {
                            it.Note = editedNote
                        }


                    })
                } else {

                    NoteItems(item = item, onEditClick = {
                        sItems = sItems.map { it.copy(isEditing = it.id == item.id) }
                    }, onDeleteClick = {
                        sItems = sItems - item
                    })
                }


            }

        }


    }

    if (isAlert) {

        AlertDialog(
            onDismissRequest = { isAlert = false },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(19.dp),


                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(onClick = {

                        if (NoteText.isNotBlank()) {
                            val newText = NoteData(
                                id = sItems.size + 1,
                                Note = NoteText,
                            )
                            sItems = sItems + newText
                            isAlert = false
                            NoteText = ""
                        }


                    }) {
                        Text(text = "Add")
                    }

                    Button(onClick = { isAlert = false }) {
                        Text(text = "Cancel")
                    }

                }


            },
            title = { Text(text = "Add Note Here") },

            text = {

                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = NoteText,
                        onValueChange = { NoteText = it }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)

                    )


                }


            }


        )


    }


}

@Composable
fun NoteItems(
    item: NoteData,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()

            .padding(8.dp)
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(36)
            ), horizontalArrangement = Arrangement.SpaceBetween
    )

    {


        Text(
            text = item.Note,
            modifier = Modifier
                .padding(8.dp)

        )

        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "This is Used To Delete Our Note ",
                modifier = Modifier.size(30.dp)
            )
        }


    }
    Column(
        modifier = Modifier.padding(8.dp),


        ) {
        IconButton(
            onClick = onEditClick


        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "This is Used To Edit Our Note ",
                modifier = Modifier.size(30.dp)
            )
        }


    }


}

@Composable
fun ListEditor(item: NoteData, onEditComplete: (String) -> Unit) {
    var editedNote by remember { mutableStateOf(item.Note) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Column {


            BasicTextField(
                value = editedNote,
                onValueChange = { editedNote = it },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )

        }

        Button(
            onClick = {
                isEditing = false
                onEditComplete(editedNote)
            }) {


            Text(text = "Done")
        }

    }

}


@Preview(showBackground = true)
@Composable
fun ToDoListPreview() {

    ToDoList()
}


