package dev.spikeysanju.wiggles.view

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun Preview() {
    AdoptionForm(navController = rememberAnimatedNavController())

}

@Composable
fun CustomTopAppBar(navController: NavController, title: String, showBackIcon: Boolean) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = if (showBackIcon && navController.previousBackStackEntry != null) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            null
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdoptionForm(navController: NavHostController) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val firstname = remember {
        mutableStateOf("")
    }
    val lastname = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val address = remember {
        mutableStateOf("")
    }
    val phone = remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            CustomTopAppBar(
                navController = navController,
                title = "Adoption Form",
                showBackIcon = true
            )
        },
        content = {

            Column {
                LazyVerticalGrid(
                    modifier = Modifier.padding(20.dp),
                    cells = GridCells.Fixed(2),

                    ) {
                    item {
                        CommonTextField(
                            value = firstname.value,
                            onValueChange = { firstname.value = it },
                            focusManager = focusManager,
                            label = "Firstname"
                        )
                    }
                    item {
                        CommonTextField(
                            value = lastname.value,
                            onValueChange = { lastname.value = it },
                            label = "Lastname",
                            focusManager = focusManager
                        )
                    }
                    item {
                        CommonTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            label = "Email",
                            focusManager = focusManager
                        )

                    }
                    item {
                        CommonTextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            label = "Password",
                            focusManager = focusManager,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation()
                        )

                    }
                    item {
                        CommonTextField(
                            value = address.value,
                            onValueChange = { address.value = it },
                            label = "Address",
                            focusManager = focusManager
                        )

                    }
                    item {
                        CommonTextField(
                            value = phone.value,
                            onValueChange = { phone.value = it },
                            label = "Phone",
                            focusManager = focusManager,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                        )
                    }

                }
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            if(firstname.value.isEmpty() and lastname.value.isNotEmpty()){
                                Toast.makeText(context, "Firstname is Empty", Toast.LENGTH_SHORT).show()
                            }
                            if(lastname.value.isEmpty() and firstname.value.isNotEmpty()){
                                Toast.makeText(context, "Lastname is Empty", Toast.LENGTH_SHORT).show()
                            }
                            if(email.value.isEmpty() and password.value.isNotEmpty()){
                                Toast.makeText(context, "Email is Empty", Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Submit")
                    }

                }
            }

        }
    )
}

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,

    focusManager: FocusManager,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext =
        {
            if (!focusManager.moveFocus(FocusDirection.Right)) {
                focusManager.moveFocus(FocusDirection.Left)
                focusManager.moveFocus(FocusDirection.Down)
            }
        }, onDone = { focusManager.clearFocus() }
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier.padding(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Gray,
            textColor = Color.Blue,
            unfocusedLabelColor = Color.Black,
            focusedLabelColor = Color.Black,

            ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        label = { Text(text = label) },
        visualTransformation = visualTransformation,
        maxLines = 2,


    )
}



