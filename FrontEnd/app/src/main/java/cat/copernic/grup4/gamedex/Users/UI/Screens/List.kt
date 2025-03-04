package cat.copernic.grup4.gamedex.Users.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.UserType
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header

@Composable
fun UserListScreen(navController: NavController, userViewModel: UserViewModel) {
    val users by userViewModel.users.collectAsState()
    val currentUser by userViewModel.currentUser.collectAsState()

    val username = remember {
        navController.currentBackStackEntry?.arguments?.getString("username")
    } ?: return // Si no hay ID, salir de la función

    LaunchedEffect(username){
        if(username.isNotBlank() && username.isNotEmpty()) {
            userViewModel.getAllUsersByUserId(username)
        }else{
            userViewModel.listUsers()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.background),
            )
            .navigationBarsPadding()
            .windowInsetsPadding(WindowInsets.systemBars),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        header(navController, userViewModel)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            stringResource(R.string.users),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Search Bar
        var searchQuery by remember { mutableStateOf("") }
        Row {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(stringResource(R.string.search) + "...") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            IconButton(
                onClick = { navController.navigate("userList/$searchQuery") },
                modifier = Modifier
                .background(Color.LightGray, shape = CircleShape)
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_user)
                )
            }
        }


        if (currentUser?.userType == UserType.USER) {
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (currentUser?.userType == UserType.ADMIN) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Button(
                    onClick = { navController.navigate("validate") },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(end = 5.dp)
                ) {
                    Text(stringResource(R.string.verify), color = Color.White, fontSize = 18.sp)
                }
                Button(
                    onClick = { navController.navigate("add_admin") },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    Text(stringResource(R.string.addAdmin), color = Color.White, fontSize = 18.sp)
                }
            }
        }
        // User List
        LazyColumn(
            modifier = Modifier.weight(1f), // ✅ Para que se ajuste al tamaño disponible
            contentPadding = PaddingValues(bottom = 80.dp) // ✅ Espacio extra para evitar que se oculte el último usuario
        ) {
            items(users) { user ->
                UserCard(user, navController)
            }
        }
    }
    BottomSection(navController, userViewModel, 2)
}

@Composable
fun UserCard(user: User, navController: NavController) {
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageBitmap = user.profilePicture?.let {
                userViewModel.base64ToBitmap(it)
            }

            Column {
                imageBitmap?.let {
                    Image(
                        it, contentDescription = stringResource(R.string.profile_picture),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(72.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.width(24.dp))
            Text(user.username, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navController.navigate("profile/${user.username}") }) {
                Icon(Icons.Default.Info, contentDescription = stringResource(R.string.view_profile))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    val fakeNavController = rememberNavController() // ✅ Crear un NavController fals per la preview
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    UserListScreen(navController = fakeNavController, userViewModel = userViewModel)
}
